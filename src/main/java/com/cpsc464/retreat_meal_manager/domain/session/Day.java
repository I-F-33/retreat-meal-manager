package com.cpsc464.retreat_meal_manager.domain.session;

import com.cpsc464.retreat_meal_manager.domain.mealperiod.MealPeriod;
import com.cpsc464.retreat_meal_manager.domain.preplist.PrepList;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "days")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer adultCount;

    @Column(nullable = false)
    private Integer youthCount;

    @Column(nullable = false)
    private Integer kidCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MealPeriod> mealPeriods = new ArrayList<>();

    @OneToOne(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    private PrepList prepList;
}
