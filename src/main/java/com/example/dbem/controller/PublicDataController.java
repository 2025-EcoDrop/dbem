package com.example.dbem.controller;

import com.example.dbem.dto.open.request.ItemName;
import com.example.dbem.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/medicine")
@RequiredArgsConstructor
@RestController
public class PublicDataController {
    private final PublicDataService publicDataService;

    @PostMapping("/name")
    public ResponseEntity<?> getNames(@RequestBody ItemName keyword) {
        List<String> names = this.publicDataService.getNames(keyword.getItemName());
        return ResponseEntity.ok(names);
    }
}
