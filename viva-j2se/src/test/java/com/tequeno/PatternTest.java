package com.tequeno;

import com.tequeno.pattern.builder.FemaleBuilder;
import com.tequeno.pattern.builder.MaleBuilder;
import com.tequeno.pattern.builder.Person;
import com.tequeno.pattern.builder.PersonDirector;
import com.tequeno.pattern.factory.*;
import com.tequeno.pattern.observer.*;
import com.tequeno.pattern.singleton.Singleton;
import com.tequeno.pattern.strategy.Duck;
import com.tequeno.pattern.strategy.MullardDuck;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PatternTest {

    @Test
    public void testFactory() {
        simpleFactory();
        factoryMethod();
    }

    /**
     * 通过统一的工厂方法创建
     */
    public void simpleFactory() {
        System.out.println("简单工厂模式");
        BMW320 bmw320 = new BMW320();
        BMW523 bmw523 = new BMW523();
        SimpleFactory simpleFactory = new SimpleFactory();
        AbstractBMW abstractBmw1 = simpleFactory.createBMW(320);
        AbstractBMW abstractBmw2 = simpleFactory.createBMW(523);
    }

    /**
     * 为每一种产品单独创建单一的工厂
     */
    public void factoryMethod() {
        System.out.println("工厂方法模式");
        BMW320Factory bmw320Factory = new BMW320Factory();
        BMW320 bmw1 = bmw320Factory.createBMW();
        BMW523Factory bmw523Factory = new BMW523Factory();
        BMW523 bmw2 = bmw523Factory.createBMW();
    }


    @Test
    public void testBuilder() {
        PersonDirector pd = new PersonDirector();
        Person male = pd.buildPerson(new MaleBuilder());
        Person female = pd.buildPerson(new FemaleBuilder());
        System.out.println(male.toString());
        System.out.println(female.toString());
    }

    @Test
    public void testObserver() {

        Customer c = new Customer();
        WDCPoster poster = new WDCPoster();
        poster.registerObserver(c);
        NYCTime time = new NYCTime();
        time.registerObserver(c);
        LDThemes themes = new LDThemes();
        themes.registerObserver(c);

        MessageDataInfo message1 = new MessageDataInfo(LocalDateTime.now(), "Welcome to subscribe Poster");
        poster.messageChanged(message1);
        MessageDataInfo message2 = new MessageDataInfo(LocalDateTime.now(), "Welcome to subscribe Time");
        time.messageChanged(message2);
        MessageDataInfo message3 = new MessageDataInfo(LocalDateTime.now(), "Welcome to subscribe Theme");
        themes.messageChanged(message3);
        c.display();
    }

    @Test
    public void testSingleton() {
        ExecutorService pool = Executors.newCachedThreadPool();

        int size = 1000;
        CopyOnWriteArraySet<Singleton> set = new CopyOnWriteArraySet<>();

        CountDownLatch cd = new CountDownLatch(size);

        IntStream.range(0, size).forEach(i -> pool.execute(() -> {
            set.add(Singleton.getInstance5());
            cd.countDown();
        }));

        try {
            boolean await = cd.await(3L, TimeUnit.SECONDS);
            if (await) {
                System.out.println("set size ---" + set.size());
            } else {
                System.out.println("等到超时");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    @Test
    public void testStrategy() {
        Duck duck = new MullardDuck();
        duck.display();
    }
}