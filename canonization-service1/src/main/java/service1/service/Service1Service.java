package service1.service;

import service1.domain.Entity;
import service1.domain.Type;

public interface Service1Service {
    Type tryInterfaceType(String test);
    Entity tryInterfaceEntity(String test);
    String fuck();
    void testMongoDB();
}
