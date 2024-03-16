package net.qwew.aliobfuscator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("msg")
public class MsgController {

    @GetMapping
    public ResponseEntity<String> getAsd() {
        try {
            return ResponseEntity.ok().body("ok gud");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("not gud :( \n" +e.toString());
        }
    } 
}
