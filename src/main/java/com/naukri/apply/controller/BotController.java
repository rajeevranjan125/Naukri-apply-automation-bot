package com.naukri.apply.controller;

import com.naukri.apply.service.JobApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bot")
public class BotController {

    private final JobApplyService jobApplyService;

    @PostMapping("/start")
    public String start() {
        jobApplyService.start();

        return "Bot Started";
    }
}
