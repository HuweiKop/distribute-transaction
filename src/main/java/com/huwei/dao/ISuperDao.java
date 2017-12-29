package com.huwei.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:14 2017/9/26
 * @Modified By
 */
@Repository
public interface ISuperDao {

    @Select("${sql}")
    List<LinkedHashMap<String,Object>> superSelect(@Param("sql") String sql);
}
