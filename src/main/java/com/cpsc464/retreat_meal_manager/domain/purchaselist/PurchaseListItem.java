package com.cpsc464.retreat_meal_manager.domain.purchaselist;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_list_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float quantity;

    @Column(nullable = false)
    private String uom;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_list_id", nullable = false)
    private PurchaseList purchaseList;
}
