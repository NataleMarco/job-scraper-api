package com.angel.scraper.job_scraper_api.service;

import com.angel.scraper.job_scraper_api.domain.entity.Job;
import com.angel.scraper.job_scraper_api.web.dto.JobDto;
import java.util.List;

public interface JobScraperService {

  String scraper();

  JobDto saveJob(Job job);

  List<JobDto> getJobs();

  void deleteJob(Integer jobId);

}