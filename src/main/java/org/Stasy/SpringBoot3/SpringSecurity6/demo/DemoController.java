package org.Stasy.SpringBoot3.SpringSecurity6.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//1:57:57 to start test changes
//What the heck, the request mappping passed the Spring Security automatically!
@RestController
@RequestMapping("/collaborator/auth/demo-controller")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
