package com.aggxb.subtrackr.service;

import com.aggxb.subtrackr.domain.Subscription;
import com.aggxb.subtrackr.dto.request.SubscriptionPostRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionPutRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionToggleStatusRequest;
import com.aggxb.subtrackr.dto.response.SubscriptionResponse;
import com.aggxb.subtrackr.dto.response.SummaryResponse;
import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.Order;
import com.aggxb.subtrackr.enums.SubscriptionStatus;
import com.aggxb.subtrackr.mapper.SubscriptionMapper;
import com.aggxb.subtrackr.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final SubscriptionMapper mapper;

    public List<SubscriptionResponse> findWithFilters(String name, Order order) {
        var subscriptionList = repository.findWithFilters(name, order);

        return mapper.toSubscriptionResponseList(subscriptionList);
    }

    public Subscription findEntityByIdOrThrowNotFound(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription with id '" + id + "' not found"));
    }

    public SubscriptionResponse save(SubscriptionPostRequest subscriptionPostRequest) {
        var subscriptionToSave = mapper.toSubscription(subscriptionPostRequest);
        var subscriptionResponse = mapper.toSubscriptionResponse(subscriptionToSave);

        repository.save(subscriptionToSave);

        return subscriptionResponse;
    }

    public void delete(UUID id) {
        var subscription = findEntityByIdOrThrowNotFound(id);

        repository.delete(subscription);
    }

    public void update(SubscriptionPutRequest subscriptionPutRequest) {
        var subscription = findEntityByIdOrThrowNotFound(subscriptionPutRequest.id());

        mapper.updateSubscription(subscriptionPutRequest, subscription);

        repository.update(subscription);
    }

    public void toggleStatus(SubscriptionToggleStatusRequest subscriptionToggleStatusRequest) {
        var subscription = findEntityByIdOrThrowNotFound(subscriptionToggleStatusRequest.id());
        var status = subscription.getStatus();

        subscription.setStatus(status == SubscriptionStatus.ACTIVE ? SubscriptionStatus.CANCELED : SubscriptionStatus.ACTIVE);

        mapper.updateSubscription(subscriptionToggleStatusRequest, subscription);

        repository.update(subscription);
    }

    public SummaryResponse getSummary() {
        List<Subscription> activeSubscriptionList = repository.findAll().stream()
                .filter(subscription -> subscription.getStatus().equals(SubscriptionStatus.ACTIVE))
                .toList();

        BigDecimal MONTH_COUNT = new BigDecimal("12");

        BigDecimal totalMonthlySubscriptionAmount = activeSubscriptionList.stream()
                .filter(subscription -> subscription.getCycle().equals(BillingCycle.MONTHLY))
                .map(Subscription::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalYearlySubscriptionAmount = activeSubscriptionList.stream()
                .filter(subscription -> subscription.getCycle().equals(BillingCycle.YEARLY))
                .map(Subscription::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalMonthlySpend = totalYearlySubscriptionAmount.divide(MONTH_COUNT, RoundingMode.HALF_UP).add(totalMonthlySubscriptionAmount);

        BigDecimal totalYearlySpend = totalMonthlySubscriptionAmount.multiply(MONTH_COUNT).add(totalYearlySubscriptionAmount);

        int activeSubscriptionCount = activeSubscriptionList.size();

        return mapper.toSummaryResponse(totalMonthlySpend, totalYearlySpend, activeSubscriptionCount);
    }
}
