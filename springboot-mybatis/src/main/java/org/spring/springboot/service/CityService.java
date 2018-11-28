package org.spring.springboot.service;

import org.spring.springboot.domain.City;
import org.spring.springboot.resultset.CachedRowSetHandler;

/**
 * 城市业务逻辑接口类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
public interface CityService {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName
     */
    City findCityByName(String cityName);

    Object findCityByName(String cityName, CachedRowSetHandler cachedRowSetHandler);

}
