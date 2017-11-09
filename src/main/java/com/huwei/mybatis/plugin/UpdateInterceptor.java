package com.huwei.mybatis.plugin;

import com.alibaba.fastjson.JSONObject;
import com.huwei.ApplicationUtil;
import com.huwei.dao.ISuperDao;
import com.huwei.mybatis.sql.analysis.BaseAnalysis;
import com.huwei.mybatis.sql.analysis.DeleteSqlAnalysis;
import com.huwei.mybatis.sql.analysis.InsertSqlAnalysis;
import com.huwei.mybatis.sql.analysis.UpdateSqlAnalysis;
import com.huwei.threadLocal.SqlThreadLocal;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:19 2017/8/31
 * @Modified By
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        }
)
@Component
public class UpdateInterceptor implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        ISuperDao superDao = ApplicationUtil.getBean(ISuperDao.class);
        superDao.superSelect("select * from user");
        System.out.println("intercept========================");
        SqlModel sqlModel = new SqlModel();
        List<String> paramNameList = new ArrayList<>();
        sqlModel.setParamNameList(paramNameList);

        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object param = args[1];
        BoundSql boundSql = statement.getBoundSql(param);
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            System.out.println(mapping.getProperty());
            paramNameList.add(mapping.getProperty());
        }
        sqlModel.setSqlCommandType(statement.getSqlCommandType());
        sqlModel.setSqlText(boundSql.getSql());
        sqlModel.setParam(param);
        System.out.println(boundSql.getSql());
        System.out.println(statement.getSqlCommandType());
        System.out.println(JSONObject.toJSONString(param));

        List<String> sqlList = null;
        /**
         * 更新需要获取 执行更新操作之前的数据
         * 因此要在目标sql执行之前 获取rollback sql
         */
        if (statement.getSqlCommandType() == SqlCommandType.UPDATE) {
            BaseAnalysis analysis = new UpdateSqlAnalysis();
            sqlList = analysis.getSqlByOriginalSql(sqlModel);
        } else if (statement.getSqlCommandType() == SqlCommandType.DELETE) {
            BaseAnalysis analysis = new DeleteSqlAnalysis();
            sqlList = analysis.getSqlByOriginalSql(sqlModel);
        }

        /**
         * 执行目标真是 sql
         */
        Object result = invocation.proceed();

        /**
         * 插入操作需要获取，在插入操作之后，获取的自增Id
         * 因此需要在目标sql执行之后 获取rollback sql
         */
        if (statement.getSqlCommandType() == SqlCommandType.INSERT) {
            BaseAnalysis analysis = new InsertSqlAnalysis();
            sqlList = analysis.getSqlByOriginalSql(sqlModel);
        }

        if (sqlList != null) {
            List<String> sqlTL = SqlThreadLocal.get();
            sqlTL.addAll(sqlList);
        }

        System.out.println(JSONObject.toJSONString(sqlModel));
        return result;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
