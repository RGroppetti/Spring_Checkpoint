package com.example.spring_checkpoint;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class PagesController {
    @GetMapping("/camelize")
    public String camelize(@RequestParam("original") String queryString,@RequestParam(value = "initialCap", defaultValue = "false") Boolean initialCap){
        String input[] = queryString.split("_");
        String output = "";
        for(String part: input){
            output += part.substring(0,1).toUpperCase()+part.substring(1).toLowerCase();
        }
        if(!initialCap){
            output = output.substring(0,1).toLowerCase() + output.substring(1);
        }
        return output;
    }
    public String fixBadWords(int length){
        String output = "";
        for(int i =0;i<length;i++){
            output += "*";
        }
        return output;
    }

    @GetMapping("/redact")
    public String redactString(@RequestParam("original") String original,@RequestParam(value = "badWord",required = false,defaultValue = "") List<String> badWords){
        String[] splitString = original.split(" ");
        for(int i = 0; i < splitString.length; i++){
            if(badWords.contains(splitString[i])){
                splitString[i] = fixBadWords(splitString[i].length());
            }
        }
        return String.join(" ",splitString);
    }
    @GetMapping("/encode")
    public String encode(@RequestParam("message") String message,@RequestParam(value = "key") String newAlphabet){
        String[] originalAlphabet = "abcdefghijklmnopqrsstuvwxyz".split("");
        String[] newAlphabetArray = newAlphabet.split("");
        String output = "";
        for(var i = 0; i < message.length();i++){
            char cur = message.charAt(i);
            int ind = Arrays.asList(originalAlphabet).indexOf(cur);
            output += newAlphabetArray[ind];
        }
        return output;
    }
}
