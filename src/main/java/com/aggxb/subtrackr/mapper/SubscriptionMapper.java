package com.aggxb.subtrackr.mapper;

import com.aggxb.subtrackr.domain.Subscription;
import com.aggxb.subtrackr.dto.request.SubscriptionPostRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionPutRequest;
import com.aggxb.subtrackr.dto.response.SubscriptionResponse;
import com.aggxb.subtrackr.dto.response.SummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Subscription toSubscription(SubscriptionPostRequest subscriptionPostRequest);

    @Mapping(target = "modifiedAt", expression = "java(java.time.LocalDateTime.now())")
    Subscription toSubscription(SubscriptionPutRequest subscriptionPutRequest);

    void updateSubscription(SubscriptionPutRequest subscriptionPutRequest, @MappingTarget Subscription subscription);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    List<SubscriptionResponse> toSubscriptionResponseList(List<Subscription> subscriptionList);

    SummaryResponse toSummaryResponse(BigDecimal totalMonthlySpend, BigDecimal totalYearlySpend, int activeSubscriptionsCount);
}
