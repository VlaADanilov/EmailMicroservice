package com.technokratos.emailmicroservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController implements EmailApi {
    private final EmailService emailService;

    public void email(String email) {
        System.out.println(email);
        emailService.sendEmail(email);
    }

    public String check(String email, String code) {
        return emailService.checkCode(email, code) ? "YES" : "NO";
    }
}
