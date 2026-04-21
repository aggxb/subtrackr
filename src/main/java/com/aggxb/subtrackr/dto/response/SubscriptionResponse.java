package com.aggxb.subtrackr.dto.response;

import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.SubscriptionStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record SubscriptionResponse(UUID id, String name, BigDecimal price, BillingCycle cycle, Integer dueDate,
                                   SubscriptionStatus status) {
}
