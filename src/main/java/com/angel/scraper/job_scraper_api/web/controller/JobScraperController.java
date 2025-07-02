package com.angel.scraper.job_scraper_api.web.controller;

import com.angel.scraper.job_scraper_api.domain.entity.Job;
import com.angel.scraper.job_scraper_api.service.JobScraperService;
import com.angel.scraper.job_scraper_api.web.dto.JobDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scraper")
public class JobScraperController {

    private final JobScraperService scraperService;

    public JobScraperController(JobScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/scraper")
    public ResponseEntity<String> scraper() {
        return ResponseEntity.ok(scraperService.scraper());
    }

    @PostMapping("/saveJob")
    public ResponseEntity<JobDto> saveJob(@RequestBody Job job) {
         return ResponseEntity.ok(scraperService.saveJob(job));
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDto>> getAllJobs() {
        return ResponseEntity.ok(scraperService.getJobs());
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<JobDto> deleteJob(@PathVariable Integer id) {
        scraperService.deleteJob(id);
        return ResponseEntity.ok().build();
    }

}
