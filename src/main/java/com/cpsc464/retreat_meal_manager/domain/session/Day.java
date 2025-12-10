package com.cpsc464.retreat_meal_manager.domain.session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cpsc464.retreat_meal_manager.domain.mealperiod.MealPeriod;
import com.cpsc464.retreat_meal_manager.domain.preplist.PrepList;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonIgnore
    private Session session;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MealPeriod> mealPeriods = new ArrayList<>();

    @OneToOne(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private PrepList prepList;
}
