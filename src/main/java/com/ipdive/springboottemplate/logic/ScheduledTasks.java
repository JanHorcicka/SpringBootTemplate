package com.ipdive.springboottemplate.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    BusinessLogic businessLogic;

    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduleUpdateTask() {
        System.out.println("This is a scheduled task");
        businessLogic.deleteExpiredUsers();
        businessLogic.deleteExpiredPasswordResetTokens();
    }
}
