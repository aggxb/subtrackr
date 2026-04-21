package com.aggxb.subtrackr.dto.request;

import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.SubscriptionStatus;

import java.math.BigDecimal;

public record SubscriptionPostRequest(String name, BigDecimal price, BillingCycle cycle, Integer dueDate,
                                      SubscriptionStatus status) {
}
