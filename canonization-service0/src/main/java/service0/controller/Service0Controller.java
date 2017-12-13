package service0.controller;

import org.springframework.web.bind.annotation.*;
        import java.util.concurrent.TimeUnit;

@RestController
public class Service0Controller {
    public static class Account {
        private int _id;
        private String _name;

        public Account() {
        }

        public int getId() {
            return _id;
        }

        public Account setId(int id) {
            this._id = id;
            return this;
        }

        public String getName() {
            return _name;
        }

        public Account setName(String name) {
            this._name = name;
            return this;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "id=" + _id +
                    ", name='" + _name + '\'' +
                    '}';
        }
    }

    @GetMapping("/account/{accountId}/{sleepSec}")
    String user(
            @PathVariable String accountId,
            @PathVariable int sleepSec
    ) {
        try {
            System.out.println("hello:" + accountId);
            return "hello:" + accountId;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("/test/{testText}")
    Boolean guess(
            @PathVariable String testText
    ) {
        System.out.println(testText);
        System.out.println(testText.length());
        if (testText.equals("haha")){
            return true;
        }
        return false;
    }
}
