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

@Service
public class RequestService {
        
    //Api URI
    private final String url;

    //RestTemplate for processing requests and responses
    private final RestTemplate restTemplate;

    //common headers for most requests
    private final HttpHeaders headers;


    public RequestService(RestTemplateBuilder restTemplateBuilder) {

        //LibreTranslate instance deployed on a cloud server
        this.url = "http://cubercube.ru:5000/";
        this.restTemplate = restTemplateBuilder.build();

        //common headers
        this.headers = new HttpHeaders();
        //set `content-type` header
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        //set `accept` header
        this.headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    public String getTranslation(String msg, String source, String target) {

        //prompt itself
        Map<String, Object> body = new HashMap<>();
        
        body.put("q", msg);
        body.put("source", source);
        body.put("target", target);
        body.put("format", "text");

        //building a request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, this.headers);
        
        //returning a response body
        String response = this.restTemplate.postForObject(url+"translate", entity, String.class);
        
        return response;
     }

    public String detectSourceLang(String msg) {
        
        //prompt itself
        Map<String, Object> body = new HashMap<>();
        body.put("q", msg);

        //building a request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, this.headers);
        
        //returning a response body
        String response = this.restTemplate.postForObject(url+"detect", entity, String.class);
        
        //returns an array of 1 object for some reason so i need to crop it
        return response.substring(1, response.length()-2);  
    }     
}
