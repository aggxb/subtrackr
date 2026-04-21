package com.aggxb.subtrackr.domain;

import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.SubscriptionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subscription {
    @NonNull
    @EqualsAndHashCode.Include
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private BigDecimal price;

    @NonNull
    private BillingCycle cycle;

    @NonNull
    private Integer dueDate;

    @NonNull
    private SubscriptionStatus status;

    @NonNull
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
