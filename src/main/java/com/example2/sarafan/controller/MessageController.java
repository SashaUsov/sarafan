package com.example2.sarafan.controller;

import com.example2.sarafan.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {


    private int counter = 4;

    private List<Map<String, String>> messages = new ArrayList<>();

    {
        HashMap<String, String> items = new HashMap<>();
        items.put("id", "1");
        items.put("text", "First message");
        messages.add(items);

        items = new HashMap<>();
        items.put("id", "2");
        items.put("text", "Second message");
        messages.add(items);

        items = new HashMap<>();
        items.put("id", "3");
        items.put("text", "Third message");
        messages.add(items);
    }

    @GetMapping
    public List<Map<String, String>> list() {

        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);

        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);

        messages.remove(message);
    }
}
