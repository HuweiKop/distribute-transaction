package com.huwei.mybatis.sql.analysis;

import com.huwei.ApplicationUtil;
import com.huwei.dao.ISuperDao;
import com.huwei.mybatis.plugin.SqlModel;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:36 2017/9/26
 * @Modified By
 */
public class UpdateSqlAnalysis extends BaseAnalysis {

    @Override
    public List<String> getSqlByOriginalSql(SqlModel sqlModel) throws IllegalAccessException {
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
        String[] arrSql = originalSql.split(" where ");
        String whereSql = "";
        if (arrSql.length > 1) {
            whereSql = " where " + arrSql[1];
        }
        String tableName = getTableName(originalSql);
        String sqlSelect = "select * from " + tableName + whereSql;
        ISuperDao superDao = ApplicationUtil.getBean(ISuperDao.class);
        List<LinkedHashMap<String, Object>> result = superDao.superSelect(sqlSelect);
        List<String> rollbackSqlList = new ArrayList<>(result.size());
        sqlModel.setRollbackSqlList(rollbackSqlList);
        for (LinkedHashMap<String, Object> map : result) {
            StringBuilder whereIdSql = new StringBuilder(" where id =");
            StringBuilder setSql = new StringBuilder(" set ");
            for (Map.Entry entry : map.entrySet()) {
                if (entry.getKey().equals("id")) {
                    whereIdSql.append(entry.getValue());
                } else {
                    setSql.append(entry.getKey()).append(" = '").append(entry.getValue()).append("',");
                }
            }
            String setS = setSql.toString();
            if (setS.endsWith(",")) {
                setS = setS.substring(0, setS.length() - 1);
            }
            String sql = "update " + tableName + setS + " " + whereIdSql.toString();
            rollbackSqlList.add(sql);
        }
        return rollbackSqlList;
    }

    @Override
    protected String getTableName(String sql) {
        String[] tt = sql.split(" set ");
        if (tt.length < 1) {
            throw new RuntimeException("sql 错误");
        }
        String tableName = tt[0].replaceAll("update", "").trim();
        return tableName;
    }
}
