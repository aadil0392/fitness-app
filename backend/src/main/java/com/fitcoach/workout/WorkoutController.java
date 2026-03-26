package com.fitcoach.workout;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @PostMapping
    public ResponseEntity<WorkoutPlanResponse> suggest(@Valid @RequestBody WorkoutRequest request) {
        return ResponseEntity.ok(workoutPlanService.suggest(request));
    }
}
