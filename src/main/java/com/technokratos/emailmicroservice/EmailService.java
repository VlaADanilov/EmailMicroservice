package com.technokratos.emailmicroservice;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;


@Service
@RequiredArgsConstructor
public class EmailService {
    private final StringRedisTemplate redisTemplate;
    private static final long CODE_TTL = 15; // 15 минут
    OkHttpClient client = new OkHttpClient();
    @Value("${api.key}")
    private String API_KEY;
    @Value("${api.sender_name}")
    private String SENDER_NAME;
    @Value("${api.sender_email}")
    private String SENDER_EMAIL;
    private static final String baseUrl = "https://api.unisender.com/ru/api/sendEmail";

    public void sendEmail(String email) {
        String code = generateCode(7);

        Request request = new Request.Builder()
                .url(fullUrl(email, code))
                .build();

        System.out.println(fullUrl(email, code));
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response.code());
            }
            String body = response.body().string();
            if(body.contains("email_id")){
                System.out.println("Успешно отправлено сообщение");
                System.out.println(body);
            } else {
                System.out.println("Ответ не такой, какой ожидался");
                System.out.println(body);
                throw new IOException("Unexpected answer " + body);
            }
            saveCode(email, code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String fullUrl(String email, String code) {
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?format=json");
        url.append("&api_key=").append(URLEncoder.encode(API_KEY, StandardCharsets.UTF_8));
        url.append("&email=").append(URLEncoder.encode("VCS2 Client <%s>".formatted(email), StandardCharsets.UTF_8));
        url.append("&sender_name=").append(URLEncoder.encode(SENDER_NAME, StandardCharsets.UTF_8));
        url.append("&sender_email=").append(URLEncoder.encode(SENDER_EMAIL, StandardCharsets.UTF_8));
        url.append("&subject=").append(URLEncoder.encode("Code", StandardCharsets.UTF_8));
        url.append("&body=").append(URLEncoder.encode(code, StandardCharsets.UTF_8));
        url.append("&list_id=").append(1);

        return url.toString();
    }

    private static String generateCode(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Длина кода должна быть больше 0");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 0-9
            codeBuilder.append(digit);
        }

        return codeBuilder.toString();
    }

    public void saveCode(String email, String code) {
        redisTemplate.opsForValue()
                .set(email, code, Duration.ofMinutes(CODE_TTL));
    }

    public boolean verifyCode(String email, String inputCode) {
        String storedCode = redisTemplate.opsForValue().get(email);
        if (storedCode == null || !storedCode.equals(inputCode)) {
            return false;
        }
        // После проверки можно удалить ключ (опционально)
        redisTemplate.delete(email);
        return true;
    }

    public boolean checkCode(String email, String code) {
        return verifyCode(email, code);
    }
}
