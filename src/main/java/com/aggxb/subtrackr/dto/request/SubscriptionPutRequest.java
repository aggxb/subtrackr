package com.aggxb.subtrackr.dto.request;

import com.aggxb.subtrackr.enums.BillingCycle;

import java.math.BigDecimal;
import java.util.UUID;

public record SubscriptionPutRequest(UUID id, String name, BigDecimal price, BillingCycle cycle, Integer dueDate) {
}
