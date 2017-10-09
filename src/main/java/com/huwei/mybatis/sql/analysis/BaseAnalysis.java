package com.huwei.mybatis.sql.analysis;

import com.huwei.ApplicationUtil;
import com.huwei.dao.ISuperDao;
import com.huwei.mybatis.plugin.SqlModel;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
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

    protected String getFullSql(SqlModel sqlModel) throws IllegalAccessException {
        String originalSql = sqlModel.getSqlText();
        Object param = sqlModel.getParam();
        for (String name : sqlModel.getParamNameList()) {
            for (Field field : param.getClass().getDeclaredFields()) {
                if (name.equals(field.getName())) {
                    field.setAccessible(true);
//                    CharSequence value = field.get(param).toString();
                    originalSql = originalSql.replaceFirst("\\?", "'"+field.get(param).toString()+"'");
                }
            }
        }
        return originalSql;
    }

    protected List<LinkedHashMap<String, Object>> getOriginalData(SqlModel sqlModel) throws IllegalAccessException {
        String originalSql = getFullSql(sqlModel);

        String[] arrSql = originalSql.split(" where ");
        String whereSql = "";
        if (arrSql.length > 1) {
            whereSql = " where " + arrSql[1];
        }
        String tableName = getTableName(originalSql);
        sqlModel.setTableName(tableName);
        String sqlSelect = "select * from " + tableName + whereSql;
        ISuperDao superDao = ApplicationUtil.getBean(ISuperDao.class);
        List<LinkedHashMap<String, Object>> result = superDao.superSelect(sqlSelect);

        return result;
    }
}
