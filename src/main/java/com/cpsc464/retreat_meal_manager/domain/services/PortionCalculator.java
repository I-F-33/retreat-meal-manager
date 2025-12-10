package com.cpsc464.retreat_meal_manager.domain.services;

import com.cpsc464.retreat_meal_manager.domain.menu.Ingredient;
import com.cpsc464.retreat_meal_manager.domain.menu.Menu;
import com.cpsc464.retreat_meal_manager.domain.menu.MenuItem;
import com.cpsc464.retreat_meal_manager.domain.session.Day;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PortionCalculator {

    public Map<Ingredient, Float> calculateIngredientsForDay(Day day, Menu menu) {
        if (menu == null) {
            return new HashMap<>();
        }

        Map<Ingredient, Float> ingredientTotals = new HashMap<>();

        for (MenuItem menuItem : menu.getMenuItems()) {
            Ingredient ingredient = menuItem.getIngredient();

            float totalQuantity = calculateIngredientQuantity(
                    ingredient,
                    day.getAdultCount(),
                    day.getYouthCount(),
                    day.getKidCount()
            );

            ingredientTotals.merge(ingredient, totalQuantity, Float::sum);
        }

        return ingredientTotals;
    }

    public float calculateIngredientQuantity(Ingredient ingredient, int adultCount, int youthCount, int kidCount) {
        float adultTotal = ingredient.getAdultPortion() * adultCount;
        float youthTotal = ingredient.getYouthPortion() * youthCount;
        float kidTotal = ingredient.getKidPortion() * kidCount;

        return adultTotal + youthTotal + kidTotal;
    }
}
