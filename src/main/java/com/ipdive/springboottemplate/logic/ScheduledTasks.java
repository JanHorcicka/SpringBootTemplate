package com.ipdive.springboottemplate.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    BusinessLogic businessLogic;

    @Scheduled(fixedDelay = 1000*60*60*24, initialDelay= 1000*60*1)
    public void scheduleUpdateTask() {
        System.out.println("This is a scheduled task");
        businessLogic.deleteExpiredUsers();
        businessLogic.deleteExpiredPasswordResetTokens();
    }
}
