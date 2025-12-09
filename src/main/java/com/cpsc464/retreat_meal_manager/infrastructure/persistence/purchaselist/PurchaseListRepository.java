package com.cpsc464.retreat_meal_manager.infrastructure.persistence.purchaselist;

import com.cpsc464.retreat_meal_manager.domain.purchaselist.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseListRepository extends JpaRepository<PurchaseList, Long> {

    Optional<PurchaseList> findBySessionSessionId(Long sessionId);
}
