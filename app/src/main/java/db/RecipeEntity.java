package db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "query")
    public String query;

    @ColumnInfo(name = "cuisine")
    public String cuisine;

    @ColumnInfo(name = "diet")
    public String diet;

    @ColumnInfo(name = "intolerances")
    public String intolerances;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "responseId")
    public int responseId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "date")
    public Date date;

}
