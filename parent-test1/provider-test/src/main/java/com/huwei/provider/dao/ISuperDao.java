package com.huwei.provider.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:14 2017/9/26
 * @Modified By
 */
@Repository
public interface ISuperDao {

    @Select("${sql}")
    List<LinkedHashMap<String,Object>> superSelect(Map<String,String> map);
}
