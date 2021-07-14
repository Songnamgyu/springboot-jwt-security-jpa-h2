package com.example.api.model.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.exception.CAuthenticationEntryPointException;
import com.example.api.model.response.CommonResult;

import lombok.RequiredArgsConstructor;

// import 생략

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() throws CAuthenticationEntryPointException {
        throw new CAuthenticationEntryPointException();
    }
}