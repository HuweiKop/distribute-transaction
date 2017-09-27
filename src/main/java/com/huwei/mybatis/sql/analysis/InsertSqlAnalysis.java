package com.huwei.mybatis.sql.analysis;

import com.huwei.mybatis.plugin.SqlModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 14:00 2017/9/26
 * @Modified By
 */
public class InsertSqlAnalysis extends BaseAnalysis {

    @Override
    public List<String> getSqlByOriginalSql(SqlModel sqlModel) throws IllegalAccessException {
        String insSql = sqlModel.getSqlText();
        Object param = sqlModel.getParam();
        String tableName = getTableName(insSql);
        for (Field field : param.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getName().equals("id")){
                field.setAccessible(true);
                String delSql = String.format("delete from %s where id=%s",tableName,field.get(param));
                System.out.println(delSql);
                List<String> list = new ArrayList<>(1);
                list.add(delSql);
                sqlModel.setRollbackSqlList(list);
                return list;
            }
        }
        throw new RuntimeException("sql 错误");
    }

    @Override
    protected String getTableName(String sql) {
        String[] tt = sql.split("\\(");
        if(tt.length<1){
            throw new RuntimeException("sql 错误");
        }
        String tableName = tt[0].replaceAll("insert","").replaceAll("into","").trim();
        return tableName;
    }
}
