package org.spring.springboot.resultset;

import com.sun.rowset.CachedRowSetImpl;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * 标记类：返回CachedRowSetImpl
 */
public class CachedRowSetHandler implements ResultHandler {
    private CachedRowSetImpl cachedRowSet;
    @Override
    public void handleResult(ResultContext resultContext) {
        cachedRowSet = (CachedRowSetImpl) resultContext.getResultObject();
    }

    public CachedRowSetImpl getResult() {
        return this.cachedRowSet;
    }
}
