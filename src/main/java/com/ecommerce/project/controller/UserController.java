package com.ecommerce.project.controller;
import com.ecommerce.project.models.User;
import com.ecommerce.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping(value = "user/login")
    public ResponseEntity<Void> loginUser(@RequestBody User user) {
        User emp = userRepository.findByEmail(user.getEmail() );
        if (emp!=null){
                if (encoder.matches(user.getPassword(),emp.getPassword())){

                    return ResponseEntity.accepted().build();
                }
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "user/email/{email}")
    public User getUser(@PathVariable String email ){

        User user = userRepository.findByEmail(email);
        if(user!=null) {
            return user;
        }
        else return  null;
    }

    @PostMapping(value = "user/register")
    public ResponseEntity<Void> registerUser(@RequestBody User user) {

        User emp = new User();

        emp.setFirst_name(user.getFirst_name());
        emp.setLast_name(user.getLast_name());
        emp.setEmail(user.getEmail());
        emp.setPassword(encoder.encode(user.getPassword()));
        emp.setRole("client");

        User E = userRepository.save(emp);
        if (E != null) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
