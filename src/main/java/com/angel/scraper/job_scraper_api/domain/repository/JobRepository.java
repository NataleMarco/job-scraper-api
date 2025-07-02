package com.angel.scraper.job_scraper_api.domain.repository;

import com.angel.scraper.job_scraper_api.domain.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
}
