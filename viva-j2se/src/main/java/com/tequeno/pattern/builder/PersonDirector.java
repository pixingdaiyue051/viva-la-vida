package com.tequeno.pattern.builder;

/**
 * 指导者
 * 按照产品的组装顺序，生产最终产品
 * 也可以根据具体需求只构建某些部分，形成一个不完全产品
 */
public class PersonDirector {
    public Person buildPerson(PersonBuilder pb) {
        pb.buildHead();
        pb.buildBody();
        pb.buildFoot();
        return pb.buildPerson();
    }
}
