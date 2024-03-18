package net.qwew.aliobfuscator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.qwew.aliobfuscator.service.ObfuscatorService;
import net.qwew.aliobfuscator.service.RequestService;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1")
public class MsgController {

    @Autowired
    ObfuscatorService obfuscator;

    @Autowired
    RequestService translator;

    @GetMapping("test")
    public ResponseEntity<String> test() {
        try {
            return ResponseEntity.ok().body("ok gud");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("not gud :( \n" +e.toString());
        }
    } 

    @PostMapping("obfuscate")
    public ResponseEntity<String> obfuscate(@RequestBody HashMap<String, Object> body) {
        String prompt = (String) body.get("prompt");
        String obfuscated = obfuscator.obfuscate(prompt);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"obfuscated\":\"" + obfuscated + "\"}");
    }
}
