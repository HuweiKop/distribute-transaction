package com.huwei.mybatis.sql.analysis;

import com.huwei.ApplicationUtil;
import com.huwei.dao.ISuperDao;
import com.huwei.mybatis.plugin.SqlModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:36 2017/9/26
 * @Modified By
 */
public class DeleteSqlAnalysis extends BaseAnalysis {

    @Override
    public List<String> getSqlByOriginalSql(SqlModel sqlModel) throws IllegalAccessException {
        List<LinkedHashMap<String, Object>> result = getOriginalData(sqlModel);
        String tableName = sqlModel.getTableName();
        List<String> rollbackSqlList = new ArrayList<>(result.size());
        sqlModel.setRollbackSqlList(rollbackSqlList);
        for (LinkedHashMap<String, Object> map : result) {
            StringBuilder sqlField = new StringBuilder();
            StringBuilder sqlValue = new StringBuilder();
            for (Map.Entry entry : map.entrySet()) {
                sqlField.append(entry.getKey()).append(",");
                sqlValue.append("'").append(entry.getValue()).append("',");
            }
            String strField = sqlField.toString();
            if (strField.endsWith(",")) {
                strField = strField.substring(0, strField.length() - 1);
            }
            String strValue = sqlValue.toString();
            if (strValue.endsWith(",")) {
                strValue = strValue.substring(0, strValue.length() - 1);
            }
            String insertSql = String.format("insert into %s (%s) values (%s)",tableName,strField,strValue);
            rollbackSqlList.add(insertSql);
        }
        return rollbackSqlList;
    }

    @Override
    protected String getTableName(String sql) {
        String[] tt = sql.split(" where ");
        if (tt.length < 1) {
            throw new RuntimeException("sql 错误");
        }
        String tableName = tt[0].replaceAll("delete", "").replaceAll("from", "").trim();
        return tableName;
    }
}
