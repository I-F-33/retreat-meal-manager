package com.cpsc464.retreat_meal_manager.application.preplist;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpsc464.retreat_meal_manager.domain.mealperiod.MealPeriod;
import com.cpsc464.retreat_meal_manager.domain.menu.Ingredient;
import com.cpsc464.retreat_meal_manager.domain.preplist.PrepList;
import com.cpsc464.retreat_meal_manager.domain.services.PortionCalculator;
import com.cpsc464.retreat_meal_manager.domain.session.Day;
import com.cpsc464.retreat_meal_manager.domain.session.Group;
import com.cpsc464.retreat_meal_manager.infrastructure.persistence.preplist.PrepListRepository;
import com.cpsc464.retreat_meal_manager.infrastructure.persistence.session.DayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PrepListService {

    private final PrepListRepository prepListRepository;
    private final DayRepository dayRepository;
    private final PortionCalculator portionCalculator;

    public PrepList generatePrepListForDay(Long dayId) {
        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + dayId));

        // Recalculate headcounts based on groups present on this day
        updateDayHeadcounts(day);

        StringBuilder notes = new StringBuilder();
        notes.append("Prep List for ").append(day.getDate()).append("\n\n");
        notes.append("Headcount - Adults: ").append(day.getAdultCount())
                .append(", Youth: ").append(day.getYouthCount())
                .append(", Kids: ").append(day.getKidCount()).append("\n\n");

        for (MealPeriod mealPeriod : day.getMealPeriods()) {
            notes.append(mealPeriod.getMealPeriodType()).append(":\n");

            if (mealPeriod.getMenu() != null) {
                notes.append("Menu: ").append(mealPeriod.getMenu().getMenuName()).append("\n");

                Map<Ingredient, Float> ingredients =
                        portionCalculator.calculateIngredientsForDay(day, mealPeriod.getMenu());

                notes.append("Ingredients:\n");
                ingredients.forEach((ingredient, quantity) ->
                        notes.append("  - ").append(ingredient.getIngredientName())
                                .append(": ").append(quantity)
                                .append(" ").append(ingredient.getUnitOfMeasure())
                                .append("\n"));
            } else {
                notes.append("No menu assigned\n");
            }
            notes.append("\n");
        }

        PrepList prepList = day.getPrepList();
        if (prepList == null) {
            prepList = PrepList.builder()
                    .day(day)
                    .notes(notes.toString())
                    .build();
        } else {
            prepList.setNotes(notes.toString());
        }

        return prepListRepository.save(prepList);
    }

    private void updateDayHeadcounts(Day day) {
        int adults = 0;
        int youth = 0;
        int kids = 0;

        // Get all groups for the session
        for (Group group : day.getSession().getGroups()) {
            // Check if this group is present on this day
            if (!day.getDate().isBefore(group.getArrivalDate()) && 
                !day.getDate().isAfter(group.getDepartureDate())) {
                adults += group.getAdultCount();
                youth += group.getYouthCount();
                kids += group.getKidCount();
            }
        }

        day.setAdultCount(adults);
        day.setYouthCount(youth);
        day.setKidCount(kids);
        dayRepository.save(day);
    }

    public PrepList getPrepListById(Long id) {
        return prepListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PrepList not found with id: " + id));
    }

    public PrepList getPrepListByDay(Long dayId) {
        return prepListRepository.findByDayDayId(dayId)
                .orElseThrow(() -> new RuntimeException("PrepList not found for day: " + dayId));
    }
}
