package com.angel.scraper.job_scraper_api.mapper;

import com.angel.scraper.job_scraper_api.domain.entity.Job;
import com.angel.scraper.job_scraper_api.web.dto.JobDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {

  JobDto toDto(Job job);

  List<JobDto> toDtoList(List<Job> jobs);

}
