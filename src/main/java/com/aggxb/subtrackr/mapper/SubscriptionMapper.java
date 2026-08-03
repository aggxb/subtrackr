package com.aggxb.subtrackr.mapper;

import com.aggxb.subtrackr.domain.Subscription;
import com.aggxb.subtrackr.dto.request.SubscriptionPostRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionPutRequest;
import com.aggxb.subtrackr.dto.response.SubscriptionResponse;
import com.aggxb.subtrackr.dto.response.SummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubscriptionMapper {
    Subscription toSubscription(SubscriptionPostRequest subscriptionPostRequest);

    void updateSubscription(SubscriptionPutRequest subscriptionPutRequest, @MappingTarget Subscription subscription);

    void updateSubscription(UUID id, @MappingTarget Subscription subscription);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    SummaryResponse toSummaryResponse(BigDecimal totalMonthlySpend, BigDecimal totalYearlySpend, int activeSubscriptionsCount);
}
