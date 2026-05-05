package com.naukri.apply.bot.handler;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class RedirectHandler {

    public Boolean isExternal(WebDriver driver){
        return driver.getWindowHandles().size() > 1;
    }

    public void closeExternal(WebDriver driver){

        for(String tab: driver.getWindowHandles()){

            driver.switchTo().window(tab);
        }

        driver.close();

        for(String tab: driver.getWindowHandles()){

            driver.switchTo().window(tab);
        }
    }
}
