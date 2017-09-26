package com.huwei.mybatis.sql.analysis;

import com.huwei.mybatis.plugin.SqlModel;

import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:36 2017/9/26
 * @Modified By
 */
public class UpdateSqlAnalysis extends BaseAnalysis {

    @Override
    public List<String> getSqlByOriginalSql(SqlModel sqlModel) {
        String originalSql = sqlModel.getSqlText();
        Object param = sqlModel.getParam();
        String[] arrSql = originalSql.split(" where ");
        String whereSql = "";
        if(arrSql.length>1){
            whereSql=" where "+arrSql[1];
        }
        return null;
    }
}
