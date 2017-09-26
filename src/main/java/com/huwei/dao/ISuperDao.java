package com.huwei.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:14 2017/9/26
 * @Modified By
 */
public interface ISuperDao {

    List<LinkedHashMap<String,Object>> superSelect(String sql);
}
