package com.naukri.apply.bot.handler;

import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplyHandler {

    public void apply(WebDriver driver) {

        try {
            System.out.println(" Searching for Apply button...");

            Thread.sleep(3000);

            WebElement applyBtn = null;

            try {
                applyBtn = driver.findElement(By.id("apply-button"));
            } catch (Exception ignored) {}

            if (applyBtn == null) {
                List<WebElement> buttons = driver.findElements(By.xpath("//button"));
                for (WebElement btn : buttons) {
                    if (btn.getText().toLowerCase().contains("apply")) {
                        applyBtn = btn;
                        break;
                    }
                }
            }

            if (applyBtn == null) {
                System.out.println("⏭ No Apply button found");
                return;
            }

            System.out.println(" Apply button detected");

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", applyBtn
            );

            Thread.sleep(4000);

            for (int i = 0; i < 10; i++) {

                try {

                    List<WebElement> questions = driver.findElements(
                            By.xpath("//div[contains(@class,'botMsg')]")
                    );

                    if (questions.isEmpty()) break;

                    WebElement lastQ = questions.get(questions.size() - 1);
                    String qText = lastQ.getText().toLowerCase();

                    System.out.println("Question: " + qText);

                    WebElement inputBox = driver.findElement(
                            By.xpath("//div[@contenteditable='true']")
                    );

                    String answer = "1";

                    if (qText.contains("experience")) {
                        answer = "1";
                    } else if (qText.contains("c++")) {
                        answer = "1";
                    } else if (qText.contains("java")) {
                        answer = "1";
                    } else if (qText.contains("relocate")) {
                        answer = "Yes";
                    }

                    inputBox.click();
                    inputBox.sendKeys(answer);
                    inputBox.sendKeys(Keys.ENTER);

                    System.out.println("Answer sent: " + answer);

                    Thread.sleep(3000);

                } catch (Exception e) {
                    System.out.println(" Chat ended");
                    break;
                }
            }

            System.out.println(" Chatbot flow completed");

        } catch (Exception e) {
            System.out.println(" Apply failed: " + e.getMessage());
        }
    }
}