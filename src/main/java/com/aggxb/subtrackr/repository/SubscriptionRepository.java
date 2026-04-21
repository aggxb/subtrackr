package com.aggxb.subtrackr.repository;

import com.aggxb.subtrackr.domain.Subscription;
import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.SubscriptionStatus;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class SubscriptionRepository {
    public static final List<Subscription> SUBSCRIPTIONS = new ArrayList<>();

    static {
        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "netflix", new BigDecimal("55.90"), BillingCycle.MONTHLY, 5, SubscriptionStatus.ACTIVE, LocalDateTime.now()));
        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "spotify", new BigDecimal("21.90"), BillingCycle.MONTHLY, 12, SubscriptionStatus.ACTIVE, LocalDateTime.now()));
        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "chatgpt", new BigDecimal("100.00"), BillingCycle.MONTHLY, 20, SubscriptionStatus.ACTIVE, LocalDateTime.now()));
        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "github", new BigDecimal("20.00"), BillingCycle.MONTHLY, 25, SubscriptionStatus.ACTIVE, LocalDateTime.now()));

        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "amazon", new BigDecimal("119.00"), BillingCycle.YEARLY, 10, SubscriptionStatus.ACTIVE, LocalDateTime.now()));
        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "adobe", new BigDecimal("1200.00"), BillingCycle.YEARLY, 15, SubscriptionStatus.ACTIVE, LocalDateTime.now()));

        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "duolingo", new BigDecimal("14.99"), BillingCycle.MONTHLY, 2, SubscriptionStatus.CANCELED, LocalDateTime.now()));
        SUBSCRIPTIONS.add(new Subscription(UUID.randomUUID(), "notion", new BigDecimal("400.00"), BillingCycle.YEARLY, 30, SubscriptionStatus.CANCELED, LocalDateTime.now()));
    }

    public List<Subscription> findAll() {
        return SUBSCRIPTIONS;
    }

    public List<Subscription> findByName(String name) {
        return SUBSCRIPTIONS.stream()
                .filter(subscription -> subscription.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<Subscription> findAllOrderByPrice() {
        return SUBSCRIPTIONS.stream()
                .sorted(Comparator.comparing(Subscription::getPrice))
                .toList();
    }

    public List<Subscription> findAllOrderByName() {
        return SUBSCRIPTIONS.stream()
                .sorted(Comparator.comparing(Subscription::getName))
                .toList();
    }

    public Optional<Subscription> findById(UUID id) {
        return SUBSCRIPTIONS.stream()
                .filter(subscription -> subscription.getId().equals(id))
                .findFirst();
    }

    public Subscription save(Subscription subscription) {
        SUBSCRIPTIONS.add(subscription);

        return subscription;
    }

    public void delete(Subscription subscription) {
        SUBSCRIPTIONS.remove(subscription);
    }

    public void update(Subscription subscription) {
        delete(subscription);
        save(subscription);
    }
}
