package com.naukri.apply.service;

import com.naukri.apply.bot.executer.BotExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobApplyService {

    private final BotExecutor executor;

    public void start() {

        new Thread(() -> {
            try {
                executor.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
