package com.angel.scraper.job_scraper_api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Jobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {

  @Id
  @Column
  private Integer id;
  @Column
  private String title;
  @Column
  private String description;
  @Column
  private Boolean approved;
  @Column
  private Boolean proccessed;
  @Column
  private Boolean downloadedError;
  @Column
  private Boolean downloaded;

}
