package com.example.recipe_research.Adapters;

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

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).title);
        holder.textView_title.setSelected(true);

        holder.textView_servings.setText(list.get(position).servings + " Portionen");
        holder.textView_time.setText(list.get(position).readyInMinutes + " Minuten");

        if(list.get(position).vegan | list.get(position).vegetarian) {

            if (list.get(position).vegan) {
                holder.textView_alergys.setText("Vegan         ✅");
            } else {
                if (list.get(position).vegetarian) {
                    holder.textView_alergys.setText("Vegetarian ✅");
                }
            }
        }
            else{
                holder.textView_alergys.setText("");
            }

        if(list.get(position).glutenFree){
            holder.textView_gluten.setText("Glutenfree ✅");
        }else{
            holder.textView_gluten.setText("");
        }
        if(list.get(position).dairyFree){
            holder.textView_lactose.setText("Dairy Free  ✅");
        }else{
            holder.textView_lactose.setText("");
        }



        Picasso.get().load(list.get(position).image).into(holder.imageView_food);

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView random_list_container;
    TextView textView_title, textView_servings, textView_time, textView_alergys,textView_lactose,textView_gluten;
    ImageView imageView_food;

    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_servings = itemView.findViewById(R.id.textView_servings);

        textView_time = itemView.findViewById(R.id.textView_time);
        imageView_food = itemView.findViewById(R.id.imageView_food);

        textView_alergys = itemView.findViewById(R.id.textView_alergys);
        textView_lactose = itemView.findViewById(R.id.textView_lactoseCheck);
        textView_gluten = itemView.findViewById(R.id.textView_glutenCheck);
    }
}