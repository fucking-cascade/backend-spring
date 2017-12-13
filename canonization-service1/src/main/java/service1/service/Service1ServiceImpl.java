package service1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service1.client.Service0Client;
import service1.domain.Entity;
import service1.domain.Type;
import service1.repository.EntityRepository;

@Service
public class Service1ServiceImpl implements Service1Service {
    @Autowired
    private Service0Client service0Client;

    @Autowired
    private EntityRepository entityRepository;

    @Override
    public String fuck() {
        return "FUCK";
    }

    @Override
    public Type tryInterfaceType(String test) {
        if (service0Client.guess(test) == true) {
            return Type.getDefault();
        } else {
            return Type.XML;
        }
    }

    @Override
    public Entity tryInterfaceEntity(String test) {
        Entity guesser = new Entity();
        guesser.setId(0);
        if (service0Client.guess(test) == true) {
            guesser.setName("GuessedRight");
            guesser.setType(Type.getDefault());
        } else {
            guesser.setName("GuessedWrong");
            guesser.setType(Type.XML);
        }
        return guesser;
    }

    @Override
    public void testMongoDB() {
        System.out.println("Testing");
        //entityRepository.deleteAll();
        int temp = 0;
        // System.out.println("Test MongoDB with findAll():");
        for (Entity entity: entityRepository.findAll()) {
            System.out.println(entity);
            temp = entity.getId() + 1;
        }

        entityRepository.save(new Entity(temp++,"Yingchen XUE", Type.JSON));
        entityRepository.save(new Entity(temp++,"Yaxuan LANYU", Type.XML));
        entityRepository.save(new Entity(temp++,"Yingchen XUE", Type.DATA));
        entityRepository.save(new Entity(temp++,"Yaxuan LANYU", Type.JSON));

        System.out.println("Test MongoDB with findAll():");
        for (Entity entity: entityRepository.findAll()) {
            System.out.println(entity);
        }
        System.out.println();
        System.out.println("Test MongoDB with findByName():");
        for (Entity entity: entityRepository.findByName("Yingchen XUE")) {
            System.out.println(entity);
        }
        System.out.println();System.out.println("Test MongoDB with findByType():");
        for (Entity entity: entityRepository.findByType(Type.JSON)) {
            System.out.println(entity);
        }
        System.out.println();

        //entityRepository.deleteAll();
    }
}
