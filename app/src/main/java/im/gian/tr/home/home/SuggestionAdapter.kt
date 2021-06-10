package im.gian.tr.home.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.model.Restaurant
import im.gian.tr.restaurant.RestaurantActivity


class SuggestionAdapter(context: Context, private val layoutResource: Int, private val restaurants: List<Restaurant>, private val saved: List<Restaurant>):
    ArrayAdapter<Restaurant>(context, layoutResource, restaurants),
    Filterable {
    private var restaurantList: List<Restaurant> = restaurants

    override fun getCount(): Int {
        return restaurantList.size
    }

    override fun getItem(position: Int): Restaurant? {
        return restaurantList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)

        //Start restaurant activity when user taps on search result
        view.setOnClickListener {
            val intent = Intent(context, RestaurantActivity::class.java)
            intent.putExtra("restaurant", Gson().toJson(restaurantList[position]))

            if (restaurantList[position] in saved)
                intent.putExtra("saved", true)

            context.startActivity(intent)
        }

        //Restaurant name textview
        val name = view.findViewById<TextView>(R.id.suggestionName)
        name.text = restaurantList[position].name

        return view
    }

    //Perform restaurant list filtering
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                restaurantList = filterResults.values as List<Restaurant>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    restaurants
                else
                    restaurants.filter {
                        it.name.lowercase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}