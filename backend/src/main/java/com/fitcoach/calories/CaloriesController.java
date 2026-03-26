package com.fitcoach.calories;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calories")
public class CaloriesController {

    private final CaloriesService caloriesService;

    public CaloriesController(CaloriesService caloriesService) {
        this.caloriesService = caloriesService;
    }

    @PostMapping
    public ResponseEntity<CaloriesResponse> calculate(@Valid @RequestBody CaloriesRequest request) {
        return ResponseEntity.ok(caloriesService.calculate(request));
    }
}
