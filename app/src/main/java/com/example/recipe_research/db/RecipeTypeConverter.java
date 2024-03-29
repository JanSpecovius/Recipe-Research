package com.example.recipe_research.db;

import androidx.room.TypeConverter;


import java.util.Date;

public class RecipeTypeConverter {
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}