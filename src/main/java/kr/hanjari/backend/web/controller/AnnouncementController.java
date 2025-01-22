package kr.hanjari.backend.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    @PostMapping("/")
    public void createAnnouncement() {
        return;
    }

    @GetMapping("/")
    public void getAllAnnouncement() {
        return;
    }

    @PatchMapping("/{announcementId}")
    public void updateAnnouncement(@PathVariable Long announcementId) {
        return;
    }

    @DeleteMapping("/{announcementId}")
    public void deleteAnnouncement(@PathVariable Long announcementId) {

    }


}
