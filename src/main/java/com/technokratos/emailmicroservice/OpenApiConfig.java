package com.technokratos.emailmicroservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Email sender and checker code API",
                description = """
        Система для отправки письма с кодом для\s
        восстановления пароля и проверки истинности кода
        """,
                version = "1.0.0",
                contact = @Contact(
                        name = "Vlad Danilov",
                        email = "helloampro@gmail.com"
                )
        )
)
@Configuration
public class OpenApiConfig {
}
