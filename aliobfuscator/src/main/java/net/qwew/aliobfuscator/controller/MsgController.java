package net.qwew.aliobfuscator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.qwew.aliobfuscator.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("msg")
public class MsgController {

    @Autowired
    RequestService requestService;

    @GetMapping
    public ResponseEntity<String> getAsd() {
        try {
            String response = requestService.getTranslation("hello this is a test", "en", "ru");
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("not gud :( \n" +e.toString());
        }
    } 
}
