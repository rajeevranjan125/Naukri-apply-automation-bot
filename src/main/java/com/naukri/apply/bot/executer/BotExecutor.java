package com.naukri.apply.bot.executer;

import com.naukri.apply.bot.handler.ApplyHandler;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BotExecutor {

    private final ApplyHandler applyHandler;

    public void run() throws Exception {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.naukri.com");

        System.out.println("👉 Login manually, then press ENTER...");
        System.in.read();

        while (true) {

            System.out.println("🔁 New Cycle Started...");

            driver.get("https://www.naukri.com/software-developer-jobs");
            Thread.sleep(5000);

            List<WebElement> jobs = driver.findElements(
                    By.xpath("//div[contains(@class,'cust-job-tuple')]")
            );

            System.out.println("Total jobs: " + jobs.size());

            for (int i = 0; i < jobs.size(); i++) {

                try {
                    jobs = driver.findElements(
                            By.xpath("//div[contains(@class,'cust-job-tuple')]")
                    );

                    WebElement job = jobs.get(i);

                    WebElement title = job.findElement(
                            By.xpath(".//a[contains(@class,'title')]")
                    );

                    String jobTitle = title.getText();
                    System.out.println("👉 Opening: " + jobTitle);

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].removeAttribute('target');", title
                    );

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();", title
                    );

                    Thread.sleep(5000);

                    boolean applied = tryApply(driver);

                    if (!applied) {
                        System.out.println("⏭ Not internal apply job");
                    }

                    driver.navigate().back();
                    Thread.sleep(4000);

                } catch (Exception e) {
                    System.out.println(" Error: " + e.getMessage());
                }
            }

            int delay = 4000 + (int)(Math.random() * 2000); // 4–6 sec
            System.out.println(" Waiting " + delay + " ms before next cycle...");
            Thread.sleep(delay);
        }
    }

    private boolean tryApply(WebDriver driver) {

        try {
            List<WebElement> applyButtons = driver.findElements(
                    By.xpath("//button[contains(text(),'Apply')]")
            );

            if (applyButtons.isEmpty()) {
                return false;
            }

            applyHandler.apply(driver);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}