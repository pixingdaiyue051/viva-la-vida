package test;

import com.tequeno.service.AreaService;
import ocean.application.MyApplicationContext;

public class Test {
    public static void main(String[] args) {
        MyApplicationContext m = new MyApplicationContext();
        AreaService areaService = (AreaService) m.getBean("AreaService");
        areaService.info();
    }
}
