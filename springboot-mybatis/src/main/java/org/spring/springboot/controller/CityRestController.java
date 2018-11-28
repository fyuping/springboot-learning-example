package org.spring.springboot.controller;

import com.sun.rowset.CachedRowSetImpl;
import org.mybatis.spring.SqlSessionTemplate;
import org.spring.springboot.domain.City;
import org.spring.springboot.resultset.CachedRowSetHandler;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return cityService.findCityByName(cityName);
    }

    @RequestMapping(value = "/api/city/set", method = RequestMethod.GET)
    public Object findOneCity2(@RequestParam(value = "cityName", required = true) String cityName) {
        Object re = cityService.findCityByName(cityName, new CachedRowSetHandler());
//        CachedRowSetImpl cachedRowSet = (CachedRowSetImpl) re;
        return re.toString();
    }

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @GetMapping("/get")
    public Object find(@RequestParam(value = "cityName", required = true) String cityName) {
        CachedRowSetHandler cachedRowSetHandler = new CachedRowSetHandler();
        Map<String, Object> param = new HashMap<>();
        param.put("cityName", cityName);
        sqlSessionTemplate.select("org.spring.springboot.dao.CityDao.findByName",param, cachedRowSetHandler);
        try {
            CachedRowSetImpl result = cachedRowSetHandler.getResult();
            if(result.size()>0 && !result.isAfterLast() &&result.next()){
                return result.getString(1)+result.getString(2)+result.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return e;
        }
        return cachedRowSetHandler.getResult().toString();
    }

}
