package com.baizhi.usermanager.controller;

import com.baizhi.usermanager.entity.User;
import com.baizhi.usermanager.service.UserService;
import com.baizhi.usermanager.util.JedisUtils;
import com.baizhi.usermanager.util.MailUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("update")
    @ResponseBody
    public Map update(User user, String token){
        Map map = new HashMap();
        try{
            //1.根据token获取redis中真正修改密码的用户名
            Jedis jedis = JedisUtils.getJedis();
            String username = jedis.get("user:findpwd:" + token);
            if(username == null){
                map.put("msg","修改操作已经过期");
                return map;
            }
            jedis.del("user:findpwd:" + token);  //删除
            //2.修改
            user.setUsername(username);
            userService.update(user);
            map.put("flag",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","系统异常，修改失败");
            map.put("flag",false);
        }
        return map;
    }


    @RequestMapping("findPwd")
    @ResponseBody
    public Map findPwd(String username){
        Map map = new HashMap();
        //先判断用户名是否存在
        User user1 = userService.selectUserByName(username);
        if(user1 == null){
            map.put("flag",false);
            map.put("msg","该用户尚未注册");
            return map;
        }

        //发送邮件比较耗时，放在子线程完成
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = UUID.randomUUID().toString().replaceAll("-","");
                MailUtil.sendSimpleMail(username,"找回密码",
                        "<a href=\"http://localhost:8989/user-manager/inputPwd.html?token="+s+"\">点我重置密码</a>");
                Jedis jedis = JedisUtils.getJedis();
                jedis.set("user:findpwd:"+s,username);
                jedis.expire("user:findpwd:"+s,3600);
            }
        }).start();

        map.put("flag",true);
        map.put("msg","已经发送验证邮件，请注意查收");
        return map;
    }


    @RequestMapping("reg")
    @ResponseBody
    public Map reg(User user,String code,HttpSession session){
        Map map = new HashMap();
        //先判断用户名是否已经被注册
        User user1 = userService.selectUserByName(user.getUsername());
        if(user1 != null){
            map.put("flag",false);
            map.put("msg","用户名已经存在");
            return map;
        }

        String regCode = (String) session.getAttribute("regCode");
        if(regCode.equalsIgnoreCase(code)){
            map.put("flag",true);
            try {
                userService.insert(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            map.put("flag",false);
            map.put("msg","验证码输入错误");
        }
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("t9", user.getUsername(), user.getPassword());

        future.addCallback(
                (stringStringSendResult) -> {
                    System.out.println("发松数据成功！");
                },
                (t) -> {
                    System.out.println("发送数据失败！");
                    t.printStackTrace();
                }
        );
        return map;
    }


    @RequestMapping("sendMail")
    @ResponseBody
    public Map sendMail(String username, HttpSession session, HttpServletRequest request){
        Map map = new HashMap();
        String remoteAddr = request.getRemoteAddr();   //获取客户端请求ip地址

       /* Jedis jedis = JedisUtils.getJedis();
        boolean f1 = jedis.exists("user:sendMail:"+remoteAddr);
        if(f1){
            map.put("msg","不能重复发送");
        }else {*/
            //发送邮件
            String s = "0123456789qwertyuiopasdfghjklzxcvbnm";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i=0;i<4;i++){
                //charAt 根据下标获取字符串中指定位置的字符
                sb.append(s.charAt(random.nextInt(s.length())));
            }
            session.setAttribute("regCode",sb.toString());  //发送邮件时，将邮件注册注册码存储到session中
            MailUtil.sendSimpleMail(username,"我是测试验证吗",sb.toString());
        System.out.println(sb.toString());
            //存储redis中
           /* jedis.set("user:sendMail:"+remoteAddr,"1");
            jedis.expire("user:sendMail:"+remoteAddr,60); // 60自动清除该key-value*/
            map.put("msg","发送成功");
       /* }*/
        return map;
    }



    @RequestMapping("login")
    @ResponseBody
    public Map login(User user, String code, HttpSession session){
        Map map = new HashMap();
        //1.先判断验证码是否正确
        //1.1 验证正确继续向下执行
        //1.2 不正确，直接响应结果告诉浏览器验证码错误
        String code2 = (String) session.getAttribute("securityCode"); //生成
        if(!code2.equalsIgnoreCase(code)){    //不正确执行判断中的代码
            map.put("flag",false);
            map.put("msg","验证码输入错误");
            return map;
        }

        //2.调用service方法判断用户名和密码是否正确
        //2.1 正确，告诉浏览器用户名和密码ok
        //2.2 不正确,告诉浏览器用户名和密码有问题
        User user1 = userService.selectUserByNameAndPwd(user);
        if(user1 != null){
            map.put("flag",true);
        }else {
            map.put("flag",false);
            map.put("msg","用户名或者密码输入错误");
        }
        return map;
    }

}
