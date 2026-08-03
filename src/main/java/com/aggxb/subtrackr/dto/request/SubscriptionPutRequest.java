package com.aggxb.subtrackr.dto.request;

import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.OwnershipType;

import java.math.BigDecimal;
import java.util.UUID;

public record SubscriptionPutRequest(String name, BigDecimal price, BillingCycle cycle, Integer dueDate,
                                     OwnershipType ownershipType) {
}
