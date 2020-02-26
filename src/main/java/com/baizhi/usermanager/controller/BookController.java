package com.baizhi.usermanager.controller;


import com.baizhi.usermanager.entity.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("book")
public class BookController {

    @RequestMapping("select")
    @ResponseBody
    public Book select(){
        return new Book(1,"测试",new Date());
    }
 int a =1;

}
