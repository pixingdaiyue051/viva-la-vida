package com.tequeno.vivaboot.service;

import com.tequeno.vivaboot.entity.Emp;

import java.time.LocalDateTime;
import java.util.Random;

public class EmpGenerator {

    private final static String NO = "emp";
    private final static String NAME = "员工";

    private long ser;

    private final Random r;

    public EmpGenerator(long ser) {
        this.ser = ser;
        this.r = new Random();
    }

    public Emp next() {
        ser++;
        Emp e = new Emp();
        e.setId(ser);
        e.setNo(NO + ser);
        e.setName(NAME + ser);
        e.setDeptId((long) r.nextInt(4) + 1);
        e.setStatus(r.nextInt(2) + 1);

        LocalDateTime time = LocalDateTime.of(2024, r.nextInt(12) + 1, r.nextInt(28) + 1, r.nextInt(24), r.nextInt(60), r.nextInt(60));
        e.setCreateTime(time);
        return e;
    }
}
