package org.spring.springboot.resultset;

import com.sun.rowset.CachedRowSetImpl;
import org.apache.ibatis.executor.result.DefaultResultContext;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * 拦截查询结果，并返回成cachedRowSet
 */
@Intercepts({@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class})})
public class RowSetIntercepts implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Class<? extends Object> targetClass = invocation.getTarget().getClass();
        Field resultHandlerField = targetClass.getDeclaredField("resultHandler");
        resultHandlerField.setAccessible(true);
        Object resultHandler = resultHandlerField.get(invocation.getTarget());
        // 如果是需要返回特定的结果，做特定的处理
        if (resultHandler != null && resultHandler instanceof CachedRowSetHandler) {
            Statement arg = (Statement) invocation.getArgs()[0];
            CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(arg.getResultSet());
            DefaultResultContext resultContext = new DefaultResultContext();
            resultContext.nextResultObject(cachedRowSet);
            ((CachedRowSetHandler) resultHandler).handleResult(resultContext);
            return Arrays.asList(cachedRowSet);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println(properties);
    }
}
