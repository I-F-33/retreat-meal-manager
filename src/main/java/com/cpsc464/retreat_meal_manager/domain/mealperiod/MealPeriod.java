package com.cpsc464.retreat_meal_manager.domain.mealperiod;

import com.cpsc464.retreat_meal_manager.domain.menu.Menu;
import com.cpsc464.retreat_meal_manager.domain.session.Day;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_periods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealPeriodType mealPeriodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", nullable = false)
    private Day day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
