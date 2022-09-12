package com.example.task_mis.controllers;

import com.example.task_mis.entities.JwtRequest;
import com.example.task_mis.entities.JwtResponse;
import com.example.task_mis.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}
