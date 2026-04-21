package com.aggxb.subtrackr.dto.response;

import java.math.BigDecimal;

public record SummaryResponse(BigDecimal totalMonthlySpend, BigDecimal totalYearlySpend,
                              Integer activeSubscriptionsCount) {
}
