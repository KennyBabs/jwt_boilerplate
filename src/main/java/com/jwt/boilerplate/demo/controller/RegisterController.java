package com.jwt.boilerplate.demo.controller;

import com.jwt.boilerplate.demo.payload.request.UserDTO;
import com.jwt.boilerplate.demo.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegisterController {

    @Autowired
    private JwtUserDetailService userDetailsService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }
}
