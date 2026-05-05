package com.naukri.apply.bot.handler;

import com.naukri.apply.bot.engine.AnswerEngine;
import com.naukri.apply.entity.QuestionEntity;
import com.naukri.apply.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuestionHandler {

    private final AnswerEngine answerEngine;
    private QuestionRepository questionRepository;

    public Boolean handleQuestion(WebDriver driver) {

        List<WebElement> questions = driver.findElements(By.className("question"));

        for (WebElement q : questions) {

            String text = q.getText();
            String normalized = normalize(text);

            String answer = answerEngine.getAnswer(normalized);

            if (answer == null) {

                saveIfNoExists(normalized);

                log.info("Unknown question → skipping job");

                return false;
            }
            try {

                q.findElement(By.tagName("input")).sendKeys(answer);

            } catch (Exception e) {

                log.info("Failed to fill answer");
            }

        }

        return null;
    }

    private void saveIfNoExists(String question) {

        if (questionRepository.findByQuestion(question).isEmpty()) {

            QuestionEntity newQuestion = QuestionEntity.builder()
                    .question(question)
                    .answered(false)
                    .build();

            questionRepository.save(newQuestion);
        }
    }

    private String normalize(String q) {
        return q.replaceAll("[^a-zA-Z ]", "").toLowerCase().trim();
    }
}
