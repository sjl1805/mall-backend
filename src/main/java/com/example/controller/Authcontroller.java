package com.example.controller;

import com.example.common.Result;
import com.example.model.dto.auth.UserLoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    //private final UsersService usersService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserLoginDTO loginDTO) {
        // usersService.getUserByLoginId(loginDTO.getLoginId());


        return Result.success(loginDTO);
    }
}


