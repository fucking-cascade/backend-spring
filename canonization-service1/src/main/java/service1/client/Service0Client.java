package service1.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "canonization-service0")
public interface Service0Client {
    @RequestMapping(value = "/test/{testText}", method = RequestMethod.GET)
    public Boolean guess(
            @PathVariable("testText") String testText
    );
}
