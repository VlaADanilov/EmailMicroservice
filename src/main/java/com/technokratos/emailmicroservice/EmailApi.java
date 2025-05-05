package com.technokratos.emailmicroservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Size;

@Tag(name = "Email controller",
    description = "Контроллер, связанный с email")
public interface EmailApi {
    @Operation(summary = "Отправить код на email",
                description = """
                        Отправляет код восстановления на email,
                        который ему передадут""")
    @ApiResponse(
            responseCode = "200",
            description = """
    Успешно отправил код на указанный email"""
    )
    @GetMapping("/email")
    void email(@RequestParam(value = "email", required = true)
               @Parameter(description = "email, на который нужно отправить код",
                       required = true,
                        example = "example@gmail.com")
               String email);

    @Operation(summary = "Проверить код",
                description = """
                        Проверит отправленный на переданный email
                        код на соответствие
                        тому, что ему передадут в параметрах""")
    @ApiResponse(
            responseCode = "200",
            description = """
    Может вернуть 'NO' или 'YES,
    'NO' - коды не совпали, 'YES' - коды совпали"""
    )
    @GetMapping("/check")
    String check(@RequestParam(value = "email", required = true)
                 @Parameter(description = "email, с кодом которого нужно сравнить",
                         required = true,
                         example = "example@gmail.com")
                 String email,
                 @RequestParam(value = "code", required = true)
                 @Parameter(description = "код, который нужно сравнить",
                            required = true,
                            example = "1234567")
                 String code);
}
