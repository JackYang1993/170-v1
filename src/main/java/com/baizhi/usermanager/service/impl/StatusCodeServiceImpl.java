package com.baizhi.usermanager.service.impl;

import com.baizhi.usermanager.dao.StatusInfoDAO;
import com.baizhi.usermanager.entity.StatusInfo;

import com.baizhi.usermanager.service.StatusCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatusCodeServiceImpl implements StatusCodeService {

    @Autowired
    private StatusInfoDAO statusInfoDAO;

    @Override
    public List<Map<String, Object>> queryStatusInfoByDate(String date) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        List<StatusInfo> list = statusInfoDAO.findByDateTime(date);
        list.forEach(statusInfo -> {
            HashMap map = new HashMap<String, Object>();
            map.put("name", statusInfo.getStatus());
            map.put("y", statusInfo.getNum());
            if ("200".equals(statusInfo.getStatus())) {
                map.put("sliced", true);
                map.put("selected", true);
            }
            result.add(map);
        });

        return result;
    }
}
