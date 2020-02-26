package com.baizhi.usermanager.controller;


import com.baizhi.usermanager.util.SecurityCode;
import com.baizhi.usermanager.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Controller
@RequestMapping("code")
public class CodeController {


    @RequestMapping("getCode")
    public void getCode(HttpSession session, HttpServletResponse response) throws Exception{
//		获取数字验证码
        String securityCode = SecurityCode.getSecurityCode();
        System.out.println(securityCode);
//		将数字验证码存入session
        session.setAttribute("securityCode", securityCode);
//		获取图片验证码
        BufferedImage image = SecurityImage.createImage(securityCode);
//		将验证码图片响应出去
        ImageIO.write(image, "png", response.getOutputStream());
    }

}
