package com.angel.scraper.job_scraper_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

  private Integer id;
  private String title;
  private Boolean approved;
  private Boolean proccessed;
  private Boolean downloadedError;
  private Boolean downloaded;

}