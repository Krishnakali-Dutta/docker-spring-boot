package com.krishnakali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class StartApplication {

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "I have successfully built a Spring Boot application using Maven!!!! ");
        model.addAttribute("msg", "This application is deployed onto EKS cluster using Helm and Jenkins Pipeline!!!!");
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StartApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.servlet.context-path", "/myapp"));
        app.run(args);
    }

}
