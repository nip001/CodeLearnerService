package com.example.demo.controller;

import com.example.demo.model.CompilerModel;
import com.example.demo.service.CompilerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompilerController {
    
    @Autowired
    CompilerService service;

    /*
        Controller menembak api compiler yang dibuat dengan python.
        mengirimkan request data data single line string 
        contoh : "LET nums = \"Data\" \n PRINT nums \n"
    */
    @PostMapping("/compile")
    public String getOutput(@RequestBody CompilerModel code){
        return service.getOutput(code).getOutput();
    }
}
