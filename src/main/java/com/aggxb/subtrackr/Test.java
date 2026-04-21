package com.aggxb.subtrackr;

import com.aggxb.subtrackr.service.SubscriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {
    private final SubscriptionService service;

    public Test(SubscriptionService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            //
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
