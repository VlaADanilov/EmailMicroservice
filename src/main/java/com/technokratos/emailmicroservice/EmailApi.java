package com.technokratos.emailmicroservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailApi {
    @GetMapping("/email")
    void email(@RequestParam("email") String email);

    @GetMapping("/check")
    String check(@RequestParam("email") String email, @RequestParam("code") String code);
}
