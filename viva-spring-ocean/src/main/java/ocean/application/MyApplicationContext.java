package ocean.application;

import ocean.util.ScanUtil;

public class MyApplicationContext {

    public MyApplicationContext() {
        ScanUtil.doScan();
        ScanUtil.map.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v);
        });
    }

    public Object getBean(String className) {
        return ScanUtil.map.get(className);
    }
}
