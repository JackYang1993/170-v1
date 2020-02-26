package com.baizhi.usermanager.dao;

import com.baizhi.usermanager.entity.StatusInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatusInfoDAO {

    @Select("select * from t_status_result where datetime = #{datetime}")
    public List<StatusInfo> findByDateTime(String dateTime);
}
