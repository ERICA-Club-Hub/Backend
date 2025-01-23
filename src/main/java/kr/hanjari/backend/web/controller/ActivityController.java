package kr.hanjari.backend.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/activity")
public class ActivityController {

    @PostMapping("/{clubId}")
    public void postNewActivity() {
        return;
    }

    @PatchMapping("/{activityId}")
    public void updateActivity() {
        return;
    }

    @GetMapping("/{clubId}")
    public void getAllActivity() {
        return;
    }

    @GetMapping("/{activityId}")
    public void getActivityDetail() {
        return;
    }

    @DeleteMapping("/{activityId}")
    public void deleteActivity() {
        return;
    }

}
