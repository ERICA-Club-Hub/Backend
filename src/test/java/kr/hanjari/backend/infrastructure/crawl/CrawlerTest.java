package kr.hanjari.backend.infrastructure.crawl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CrawlerTest {

    @Test
    @DisplayName("Crawler Test")
    public void testCrawl() {

        WebDriver driver = null;

        try {
            driver = getDriver();

            driver.get("https://hanjari.site");
            System.out.println(driver.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }

    private WebDriver getDriver() {

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

        WebDriver driver = new ChromeDriver(options);

        return driver;
    }
}
