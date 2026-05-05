package com.naukri.apply.bot.engine;

import com.naukri.apply.entity.QuestionEntity;
import com.naukri.apply.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerEngine {

    private final QuestionRepository questionRepository;

    public String getAnswer(String question) {

        String normalized = normalizeQuestion(question);

        Optional<QuestionEntity> questionEntity = questionRepository.findByQuestion(normalized);

        if (questionEntity.isPresent() && questionEntity.get().getAnswered()) {
            return questionEntity.get().getAnswer();
        }

        return null;
    }

    private String normalizeQuestion(String question) {
        return question.replaceAll("\"[^a-zA-Z ]\"", "").toLowerCase().trim();
    }
}
