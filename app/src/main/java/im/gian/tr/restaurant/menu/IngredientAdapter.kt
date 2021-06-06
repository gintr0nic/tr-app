package im.gian.tr.restaurant.menu

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.model.Item
import im.gian.tr.producer.ProducerActivity
import im.gian.tr.restaurant.RestaurantActivity
import im.gian.tr.restaurant.RestaurantViewModel

class IngredientAdapter(private val context: Context?, private val ingredients: List<Map<String,String>>) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    class IngredientViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val textViewIngredient: TextView = row.findViewById(R.id.textViewIngredient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_view,
            parent, false)

        return IngredientViewHolder(layout)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.textViewIngredient.text = ingredients[position]["name"]
        holder.textViewIngredient.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        holder.textViewIngredient.setOnClickListener {
            val intent = Intent(context, ProducerActivity::class.java)
            intent.putExtra("producer", ingredients[position]["producer"].toString())
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = ingredients.size
}