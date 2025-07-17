package com.example.dbem.controller;

import com.example.dbem.dto.open.request.ItemName;
import com.example.dbem.service.PublicDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RequestMapping("/api/medicine")
@RequiredArgsConstructor
@RestController
public class PublicDataController {
    private final PublicDataService publicDataService;

    @GetMapping("/name")
    public ResponseEntity<?> getNames(@RequestBody ItemName keyword) {
        List<String> names = this.publicDataService.getNames(keyword.getItemName());
        return ResponseEntity.ok(names);
    }
}
