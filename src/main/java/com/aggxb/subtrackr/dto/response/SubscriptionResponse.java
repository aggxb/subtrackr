package com.aggxb.subtrackr.dto.response;

import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.OwnershipType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionResponse(UUID id, String name, BigDecimal price, BillingCycle cycle, Integer dueDate,
                                   OwnershipType ownershipType, Boolean status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
