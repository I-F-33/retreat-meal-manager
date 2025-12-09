package com.cpsc464.retreat_meal_manager.domain.purchaselist;

import com.cpsc464.retreat_meal_manager.domain.session.Session;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_lists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseListId;

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseListStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @OneToMany(mappedBy = "purchaseList", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchaseListItem> items = new ArrayList<>();
}
