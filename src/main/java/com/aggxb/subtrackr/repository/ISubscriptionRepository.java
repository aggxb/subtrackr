package com.aggxb.subtrackr.repository;

import com.aggxb.subtrackr.domain.Subscription;
import com.aggxb.subtrackr.enums.BillingCycle;
import com.aggxb.subtrackr.enums.OwnershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface ISubscriptionRepository extends JpaRepository<Subscription, UUID>, JpaSpecificationExecutor<Subscription> {

    @Query("""
                SELECT SUM(s.price)
                FROM Subscription s
                WHERE s.status = :status 
                            AND s.cycle = :cycle 
                            AND (:ownershipType IS NULL OR s.ownershipType = :ownershipType)
            """)
    BigDecimal calculateTotalSpendByStatusAndCycleAndOwnershipType(@Param("status") Boolean status, @Param("cycle") BillingCycle cycle, @Param("ownershipType") OwnershipType ownershipType);

    @Query(""" 
                SELECT COUNT (s.id)
                FROM Subscription s
                WHERE s.status = :status
                            AND (:ownershipType IS NULL OR s.ownershipType = :ownershipType)
            """)
    Integer countByOwnershipTypeAndStatus(OwnershipType ownershipType, Boolean status);
}
