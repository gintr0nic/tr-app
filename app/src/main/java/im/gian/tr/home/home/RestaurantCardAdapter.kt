package im.gian.tr.home.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import im.gian.tr.R

class RestaurantCardAdapter(private val restaurantList: List<Restaurant>) : RecyclerView.Adapter<RestaurantCardAdapter.RestaurantCardViewHolder>() {
    class RestaurantCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val textViewRestaurantName: TextView = row.findViewById(R.id.textViewRestaurantName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_card_view,
            parent, false)
        return RestaurantCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RestaurantCardViewHolder, position: Int) {
        holder.textViewRestaurantName.text = restaurantList[position].name
    }

    override fun getItemCount(): Int = restaurantList.size
}