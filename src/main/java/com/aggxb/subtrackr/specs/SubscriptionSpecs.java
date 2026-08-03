package com.aggxb.subtrackr.specs;

import com.aggxb.subtrackr.domain.Subscription;
import com.aggxb.subtrackr.enums.OwnershipType;
import org.springframework.data.jpa.domain.Specification;

public final class SubscriptionSpecs {

    public static Specification<Subscription> nameContains(String term) {
        return ((root, query, criteriaBuilder) -> {
            if (term == null || term.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + term.toLowerCase() + "%"
            );
        });
    }

    public static Specification<Subscription> ownershipTypeEquals(OwnershipType ownershipType) {
        return ((root, query, criteriaBuilder) -> {
            if (ownershipType == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("ownershipType"),
                    ownershipType
            );
        });
    }

}
