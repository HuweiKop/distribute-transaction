package com.huwei.mybatis.sql.analysis;

import com.huwei.mybatis.plugin.SqlModel;

import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:35 2017/9/26
 * @Modified By
 */
public abstract class BaseAnalysis {

    public abstract List<String> getSqlByOriginalSql(SqlModel sqlModel) throws IllegalAccessException;

    protected abstract String getTableName(String sql);
}
