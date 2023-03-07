package com.example.recipe_research.Models;

import java.util.List;

public class NutritionByIdResponse {
    private List<NutritionValue> nutrients;

    public List<NutritionValue> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<NutritionValue> nutrients) {
        this.nutrients = nutrients;
    }

    public static class NutritionValue {
        private String name;
        private String amount;
        private String unit;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
