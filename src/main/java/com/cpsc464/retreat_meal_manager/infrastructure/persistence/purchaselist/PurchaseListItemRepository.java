package com.cpsc464.retreat_meal_manager.infrastructure.persistence.purchaselist;

import com.cpsc464.retreat_meal_manager.domain.purchaselist.PurchaseListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseListItemRepository extends JpaRepository<PurchaseListItem, Long> {

    List<PurchaseListItem> findByPurchaseListPurchaseListId(Long purchaseListId);
}
