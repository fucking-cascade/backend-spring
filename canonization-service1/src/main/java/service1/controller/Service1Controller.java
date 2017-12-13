package service1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service1.domain.Entity;
import service1.service.Service1Service;

@RestController
public class Service1Controller {
    @Autowired
    private Service1Service service1Service;

    @GetMapping("/testType/{testContent}")
    public String testType(
            @PathVariable String testContent
    ) {
        return service1Service.tryInterfaceType(testContent).toString();
        //return service1Service.fuck();
    }

    @GetMapping("/testEntity/{testContent}")
    @ResponseBody
    public Entity testEntity(
            @PathVariable String testContent
    ) {
        return service1Service.tryInterfaceEntity(testContent);
        //return service1Service.fuck();
    }

    @GetMapping("/test")
    public String test() {
        service1Service.testMongoDB();
        return "Testing MongoDB";
    }
}
