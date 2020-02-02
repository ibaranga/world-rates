package com.worldrates.app.boundary;

import com.worldrates.app.entity.RateSyncStatus;
import com.worldrates.jobs.boundary.RatesSyncJob;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final RatesSyncJob ratesSyncJob;

    public AdminController(RatesSyncJob ratesSyncJob) {
        this.ratesSyncJob = ratesSyncJob;
    }

    @PostMapping("/jobs/dailyRatesSync")
    public ResponseEntity<RateSyncStatus> dailyRatesSync() {
        LocalDateTime startTime = LocalDateTime.now();
        try {
            ratesSyncJob.syncRates();
            return ResponseEntity
                    .ok(new RateSyncStatus(startTime, LocalDateTime.now(), "OK", null, null, null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RateSyncStatus(startTime, LocalDateTime.now(), "ERROR", null, null, e.getMessage()));
        }
    }
}
