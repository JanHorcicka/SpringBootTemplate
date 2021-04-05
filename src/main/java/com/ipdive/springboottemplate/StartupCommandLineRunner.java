package com.ipdive.springboottemplate;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
class StartupCommandLineRunner {

    @PostConstruct
    public void init() {
        createInMemoryItems();
    }

    private void createInMemoryItems() {
        System.out.println("Loading in-memory DAOs");
        // TODO: Put in-memory loading logic here
        System.out.println("In-memory DAOs loaded");
    }

}