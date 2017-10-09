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
        List<LinkedHashMap<String, Object>> result = getOriginalData(sqlModel);
        String tableName = sqlModel.getTableName();
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
