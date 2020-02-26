package com.baizhi.usermanager.controller;

import com.baizhi.usermanager.service.StatusCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class StatusInfoController {

    @Autowired
    private StatusCodeService statusCodeService;

    @RequestMapping("/statusinfo")
    @ResponseBody
    public List<Map<String, Object>> statusInfo(String datetime) {
        List<Map<String, Object>> maps = statusCodeService.queryStatusInfoByDate(datetime);
        return maps;
    }
}
