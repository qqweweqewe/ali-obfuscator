package net.qwew.aliobfuscator.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
public class RequestService {
        
    private final String url;

    private final RestTemplate restTemplate;

    public RequestService(RestTemplateBuilder restTemplateBuilder) {

        //LibreTranslate instance deployed on a cloud server
        this.url = "http://cubercube.ru:5000/translate";
        restTemplate = restTemplateBuilder.build();
    }

    public String getTranslation(String msg, String source, String target) {
        
        //create headers
        HttpHeaders headers = new HttpHeaders();
        //set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        //set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();

        //prompt itself
        map.put("q", msg);
        map.put("source", source);
        map.put("target", target);
        map.put("format", "text");

        String jsonBody = gson.toJson(map);
        
        //building a request
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        
        //returning a response body
        String response = this.restTemplate.postForObject(url, entity, String.class);
        System.out.println(response);
        return response;
     }

}
