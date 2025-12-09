package com.cpsc464.retreat_meal_manager.domain.preplist;

import com.cpsc464.retreat_meal_manager.domain.session.Day;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prep_lists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrepList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prepListId;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", nullable = false)
    private Day day;
}
