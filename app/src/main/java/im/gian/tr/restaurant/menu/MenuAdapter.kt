package im.gian.tr.restaurant.menu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import im.gian.tr.R
import im.gian.tr.model.Item
import im.gian.tr.restaurant.RestaurantActivity
import im.gian.tr.restaurant.RestaurantViewModel

class MenuAdapter(private val context: Context?) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val restaurantViewModel: RestaurantViewModel =
        ViewModelProvider(context as RestaurantActivity).get(RestaurantViewModel::class.java)

    private var menu: List<Item>? = restaurantViewModel.menu.value

    var storage = Firebase.storage

    class MenuViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val textViewName: TextView = row.findViewById(R.id.textViewName)
        val recyclerViewIngredients: RecyclerView = row.findViewById(R.id.recyclerViewIngredients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.menu_card_view,
            parent, false)

        return MenuViewHolder(layout)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        //Observe for changes in viewmodel
        restaurantViewModel.menu.observe(context as RestaurantActivity, menuObserver)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        //Name
        holder.textViewName.text = menu!![position].name
        Log.d("MENU", menu!![position].name)

        //Ingredients
        holder.recyclerViewIngredients.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerViewIngredients.adapter = IngredientAdapter(context, menu!![position].ingredients)

    }

    //Update certification list when viewmodel data changes
    private val menuObserver = Observer<List<Item>> {
        menu = restaurantViewModel.menu.value

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = menu!!.size
}