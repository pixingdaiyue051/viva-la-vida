package com.tequeno.file;

import com.tequeno.classload.Employee;
import com.tequeno.classload.Pack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeHandler {

    public void load() {

        try (FileOutputStream fos = new FileOutputStream("/data/doc/pack1.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            Pack p = new Pack();
            p.setParam1("43fd");
            p.setId(44L);
            p.setParam2(556);
            p.setEmployee(new Employee());
            oos.writeObject(p);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void read() {

        try (FileInputStream fis = new FileInputStream("/data/doc/pack1.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Object o = ois.readObject();
            Pack p = (Pack) o;
            System.out.println(p);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
