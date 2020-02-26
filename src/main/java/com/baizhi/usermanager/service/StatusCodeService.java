package com.baizhi.usermanager.service;

import java.util.List;
import java.util.Map;

public interface StatusCodeService {

    List<Map<String, Object>> queryStatusInfoByDate(String date);
}
