package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) throws JsonProcessingException {
        String finalResult = "";
        RestTemplate template = new RestTemplate();
        String task = "http://94.198.50.185:7081/api/users";


        // #1 Получаем всех пользователей
        ResponseEntity<String> response = template.getForEntity(task, String.class);


        // #2 достаём куки
        String sessionID = response.getHeaders().get("Set-cookie").get(0);


        // #3 Сохроняем пользователя
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionID);
        // Сохранить пользователя с id = 3, name = James, lastName = Brown, age = на ваш выбор.
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 10);

        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> result = template.exchange(task, HttpMethod.POST, entity, String.class);
        finalResult += result.getBody();


        // #4 Изменить пользователя с id = 3. Необходимо поменять name на Thomas, а lastName на Shelby.
        user.setName("Thomas");
        user.setLastName("Shelby");
        entity = new HttpEntity<>(user, httpHeaders);
        result = template.exchange(task, HttpMethod.PUT, entity, String.class);
        finalResult += result.getBody();

        // # Удалить пользователя с id = 3. В случае успеха вы получите последнюю часть кода.
        task = "http://94.198.50.185:7081/api/users/3";
//        Map<String, Long> params = new HashMap<>();
//        params.put("id", 3L);
        result = template.exchange(task, HttpMethod.DELETE, entity, String.class);
        finalResult += result.getBody();




        // My work
        System.out.println(finalResult);
    }
}