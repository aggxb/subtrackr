package com.aggxb.subtrackr.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record SubscriptionPutRequest(UUID id, BigDecimal price) {
}
