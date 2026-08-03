package com.aggxb.subtrackr.service;

import com.aggxb.subtrackr.domain.Subscription;
import com.aggxb.subtrackr.dto.request.SubscriptionPostRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionPutRequest;
import com.aggxb.subtrackr.dto.response.SubscriptionResponse;
import com.aggxb.subtrackr.dto.response.SummaryResponse;
import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.OwnershipType;
import com.aggxb.subtrackr.mapper.SubscriptionMapper;
import com.aggxb.subtrackr.repository.ISubscriptionRepository;
import com.aggxb.subtrackr.specs.SubscriptionSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final ISubscriptionRepository repository;
    private final SubscriptionMapper mapper;

    public Page<SubscriptionResponse> findWithFilters(String term,
                                                      OwnershipType ownershipType,
                                                      Pageable pageable) {
        Specification<Subscription> spec = Specification
                .where(SubscriptionSpecs.nameContains(term))
                .and(SubscriptionSpecs.ownershipTypeEquals(ownershipType));

        Page<Subscription> subscriptionList = repository.findAll(spec, pageable);

        return subscriptionList.map(mapper::toSubscriptionResponse);
    }

    public Subscription findEntityByIdOrThrowNotFound(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription with id '" + id + "' not found"));
    }

    public SubscriptionResponse save(SubscriptionPostRequest subscriptionPostRequest) {
        Subscription subscriptionToSave = mapper.toSubscription(subscriptionPostRequest);

        Subscription savedSubscription = repository.save(subscriptionToSave);

        return mapper.toSubscriptionResponse(savedSubscription);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) {
        Subscription subscription = findEntityByIdOrThrowNotFound(id);

        repository.delete(subscription);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(UUID id, SubscriptionPutRequest subscriptionPutRequest) {
        Subscription subscription = findEntityByIdOrThrowNotFound(id);

        mapper.updateSubscription(subscriptionPutRequest, subscription);

        repository.save(subscription);
    }

    @Transactional(rollbackFor = Exception.class)
    public void toggleStatus(UUID id) {
        Subscription subscription = findEntityByIdOrThrowNotFound(id);
        Boolean status = subscription.getStatus();

        subscription.setStatus(!status);

        mapper.updateSubscription(id, subscription);

        repository.save(subscription);
    }

    public SummaryResponse getSummary(OwnershipType ownershipType) {
        BigDecimal MONTH_COUNT = new BigDecimal("12");

        Integer totalActiveSubscriptions = repository.countByOwnershipTypeAndStatus(ownershipType, true);

        BigDecimal totalMonthlySpend = repository.calculateTotalSpendByStatusAndCycleAndOwnershipType(true, BillingCycle.MONTHLY, ownershipType);
        BigDecimal totalYearlySpend = repository.calculateTotalSpendByStatusAndCycleAndOwnershipType(true, BillingCycle.YEARLY, ownershipType);

        if (totalMonthlySpend == null) totalMonthlySpend = BigDecimal.ZERO;
        if (totalYearlySpend == null) totalYearlySpend = BigDecimal.ZERO;

        BigDecimal finalMonthlySpend = totalYearlySpend.divide(MONTH_COUNT, RoundingMode.HALF_UP).add(totalMonthlySpend);
        BigDecimal finalYearlySpend = totalMonthlySpend.multiply(MONTH_COUNT).add(totalYearlySpend);

        return mapper.toSummaryResponse(finalMonthlySpend, finalYearlySpend, totalActiveSubscriptions);
    }
}
