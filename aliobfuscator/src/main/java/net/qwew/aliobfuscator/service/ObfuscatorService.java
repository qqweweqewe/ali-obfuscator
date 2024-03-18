package net.qwew.aliobfuscator.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class ObfuscatorService {
    
    private final Gson gson;
    
    @Autowired
    RequestService translator;
    
    public ObfuscatorService() {
        this.gson = new Gson();
    }

    public String obfuscate(String msg) {
        
        //pipeline:
        //source -> en -> sq(albanian) -> ko -> zh -> source -> ja -> source
    
        //retrieving the source language
        Map<String, Object> map = gson.fromJson(translator.detectSourceLang(msg), HashMap.class);
        String source = (String) map.get("language");

        //languages
        String[] lang = {source, "en", "sq", "ko", "zh", source, "ja"};
        
        //obfuscation
        String translated = msg;
        Map<String, Object> translatedRaw;

        for(int i = 0; i < lang.length; i++) {
            translatedRaw = gson.fromJson(translator.getTranslation(translated, lang[i], lang[(i+1) % lang.length]), HashMap.class);
            translated = (String) translatedRaw.get("translatedText");
        }

        return translated;
    }
}
