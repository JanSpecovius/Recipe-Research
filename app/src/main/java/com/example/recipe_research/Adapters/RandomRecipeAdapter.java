package com.example.recipe_research.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_research.Listeners.RecipeClickListener;
import com.example.recipe_research.Models.Recipe;
import com.example.recipe_research.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {
    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.textViewTitle.setText(list.get(position).title);
        holder.textViewTitle.setSelected(true);

        holder.textViewServings.setText(list.get(position).servings +" " + context.getString(R.string.servings));
        holder.textViewTime.setText(list.get(position).readyInMinutes + " " + context.getString(R.string.minutes));

        if (list.get(position).vegan || list.get(position).vegetarian) {

            if (list.get(position).vegan) {
                holder.textViewAllergy.setText(context.getString(R.string.vegan) +"           "+ context.getString(R.string.check));
            } else if (list.get(position).vegetarian) {
                holder.textViewAllergy.setText(context.getString(R.string.vegetarian) +"   "+ context.getString(R.string.check));
            }


        }else {
            holder.textViewAllergy.setText(context.getString(R.string.vegetarian) +"   "+ context.getString(R.string.cross));
            }

        if(list.get(position).glutenFree){
            holder.textViewGluten.setText(context.getString(R.string.glutenFree) +"   "+ context.getString(R.string.check));
        }else{
            holder.textViewGluten.setText(context.getString(R.string.glutenFree) +"   "+ context.getString(R.string.cross));
        }
        if(list.get(position).dairyFree){
            holder.textViewLactose.setText(context.getString(R.string.dairyFree) +"     "+ context.getString(R.string.check));
        }else{
            holder.textViewLactose.setText(context.getString(R.string.dairyFree) +"     "+ context.getString(R.string.cross));
        }


        Picasso.get().load(list.get(position).image).into(holder.imageViewFood);

        holder.randomListContainer.setOnClickListener(view -> listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView randomListContainer;
    TextView textViewTitle;
    TextView textViewServings;
    TextView textViewTime;
    TextView textViewAllergy;
    TextView textViewLactose;
    TextView textViewGluten;
    ImageView imageViewFood;

    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        randomListContainer = itemView.findViewById(R.id.random_list_container);
        textViewTitle = itemView.findViewById(R.id.textView_title);
        textViewServings = itemView.findViewById(R.id.textView_servings);

        textViewTime = itemView.findViewById(R.id.textView_time);
        imageViewFood = itemView.findViewById(R.id.imageView_food);

        textViewAllergy = itemView.findViewById(R.id.textView_alergys);
        textViewLactose = itemView.findViewById(R.id.textView_lactoseCheck);
        textViewGluten = itemView.findViewById(R.id.textView_glutenCheck);
    }
}