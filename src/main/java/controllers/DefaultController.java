package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping("/hi")
    public String sayHi() {
        return "hi!";
    }

    @RequestMapping("/")
    public String rootController(){
        return "base level";
    }
}
