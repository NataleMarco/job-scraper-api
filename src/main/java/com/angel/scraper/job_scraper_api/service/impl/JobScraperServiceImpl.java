package com.angel.scraper.job_scraper_api.service.impl;

import com.angel.scraper.job_scraper_api.domain.entity.Job;
import com.angel.scraper.job_scraper_api.domain.repository.JobRepository;
import com.angel.scraper.job_scraper_api.mapper.JobMapper;
import com.angel.scraper.job_scraper_api.service.JobScraperService;
import com.angel.scraper.job_scraper_api.web.dto.JobDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static java.lang.Thread.sleep;

@Service
public class JobScraperServiceImpl implements JobScraperService {

  final JobRepository jobRepository;
  final JobMapper jobMapper;
  @Value("${firefox.profile}")
  private String firefoxProfile;
  @Value("${div.name}")
  private String divName;

  public JobScraperServiceImpl(JobRepository jobRepository, JobMapper jobMapper) {
    this.jobRepository = jobRepository;
    this.jobMapper = jobMapper;
  }

  private List<String> extractJobIds(String html) {
    List<String> jobIds = new ArrayList<>();

    try {
      Document doc = Jsoup.parse(html);
      Elements jobItems = doc.select("ul li div a[href]");

      for (Element jobItem : jobItems) {
        Element jobLink = jobItem.select("a").first();

        if (jobLink != null) {
          String href = jobLink.attr("href");

          if (href.startsWith("/jobs/view")) {
            String[] parts = href.split("/");

            if (parts.length == 3) {
              jobIds.add(parts[3]);
            }

          }

        }

      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return jobIds;
  }

  private void saveHtmlToFile(String jobId, String html) throws IOException {
    Path path = Paths.get("html_jobs", jobId + ".html");
    Files.createDirectories(path.getParent());
    Files.writeString(path, html, StandardCharsets.UTF_8);
  }

  public String scraper() {
    WebDriver driver = devSeleniumDriver();

    try {

      if (driver == null) {
        throw new RuntimeException("WebDriver is null");
      }

      String url = "https://www.linkedin.com/jobs/search/?currentJobId=4258352254&distance=25&geoId=100446943&keywords=junior%20java%20developer&origin=JOB_SEARCH_PAGE_KEYWORD_AUTOCOMPLETE&refresh=true";
      driver.get(url);
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div." + divName)));
      WebElement scrollableElement = driver.findElement(By.cssSelector("div." + divName));
      Actions actions = new Actions(driver);
      actions.moveToElement(scrollableElement).perform();
      JavascriptExecutor js = (JavascriptExecutor) driver;

      for (int i = 0; i < 3; i++) {
        js.executeScript("arguments[0].scrollTop += 1500;", scrollableElement);
        sleep(2000);
      }

      return "Selenium connection test completed successfully.";
    } catch (Exception e) {
      return "Selenium connection test failed: " + e.getMessage();
    } finally {

      if (driver != null) {
        driver.quit();
      }

    }

  }

  public WebDriver devSeleniumDriver() {
    WebDriverManager.firefoxdriver().setup();
    ProfilesIni allProfiles = new ProfilesIni();
    FirefoxProfile profile = allProfiles.getProfile(firefoxProfile);

    if (profile == null) {
      throw new RuntimeException("The profile " + firefoxProfile + " does not exist");
    }

    FirefoxOptions options = new FirefoxOptions();
    options.setProfile(profile);
    return new FirefoxDriver(options);
  }

  public JobDto saveJob(Job job) {
    Job savedJob = jobRepository.save(job);
    return jobMapper.toDto(savedJob);
  }

  public List<JobDto> getJobs() {
    List<Job> jobList = jobRepository.findAll();
    return jobMapper.toDtoList(jobList);
  }

  public void deleteJob(Integer jobId) {
    jobRepository.deleteById(jobId);
  }

}
