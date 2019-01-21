package com.tequeno.service;

import com.tequeno.dao.AreaDao;
import ocean.anno.MyAutowired;
import ocean.anno.MyComponent;

@MyComponent
public class AreaService {

    @MyAutowired
    private AreaDao areaDao;

    public void info() {
        System.out.println("111111111111");
        areaDao.info();
    }
}
