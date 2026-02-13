package kr.hanjari.backend.infrastructure.crawl;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.detail.ClubInstagramImage;
import kr.hanjari.backend.domain.club.domain.repository.ClubInstagramImageRepository;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InstagramCrawler {

    private final ClubRepository clubRepository;
    private final ClubInstagramImageRepository clubInstagramImageRepository;

    @Value("${slack.webhook-url}")
    private String SELENIUM_URL;

    private static final String INSTAGRAM_URL = "https://www.instagram.com/";


//    @Scheduled(cron = "0 0 4 * * *")
    public void update() {

        WebDriver driver = null;

        try {
            driver = getDriver();

            List<Club> clubList = clubRepository.findAll();
            for (Club club : clubList) {

                Long clubId = club.getId();
                String account = club.getSnsUrl();
                String profileImageUrl = getProfileImageUrl(account, driver);

                ClubInstagramImage clubInstagramImage = clubInstagramImageRepository.findById(clubId)
                        .orElseGet(() -> new ClubInstagramImage(clubId));

                clubInstagramImage.update(profileImageUrl);
                clubInstagramImageRepository.save(clubInstagramImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }



    private String getProfileImageUrl(String account, WebDriver driver) {
        String profileUrl = INSTAGRAM_URL + account;

        driver.get(profileUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String query = "//img[contains(@alt, '" + account + "')]";
        // 대기
        WebElement profileImageElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(query))
        );

        String imageUrl = profileImageElement.getAttribute("src");

        if (imageUrl != null) {
            return imageUrl;
        }
        return null;

    }

    private WebDriver getDriver() throws MalformedURLException {

        // Chrome
        ChromeOptions options = new ChromeOptions();
        // 백그라운드 실행
        options.addArguments("--headless");
        // sandbox 비활성화
        options.addArguments("--no-sandbox");
        // gpu 가속 비활성화
        options.addArguments("--disable-gpu");
        // 공유 메모리 비활성화
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        URI uri = URI.create(SELENIUM_URL);

        return new RemoteWebDriver(
                uri.toURL(),
                options
        );
    }
}
