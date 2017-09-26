package com.huwei.mybatis.plugin;

import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 18:25 2017/9/11
 * @Modified By
 */
public class SqlModel {
    private SqlCommandType sqlCommandType;
    private String sqlText;
    private List<String> paramNameList;
    private Object param;
    private String idName;
    private List<String> rollbackSqlList;

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<String> getParamNameList() {
        return paramNameList;
    }

    public void setParamNameList(List<String> paramNameList) {
        this.paramNameList = paramNameList;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public List<String> getRollbackSqlList() {
        return rollbackSqlList;
    }

    public void setRollbackSqlList(List<String> rollbackSqlList) {
        this.rollbackSqlList = rollbackSqlList;
    }
}
