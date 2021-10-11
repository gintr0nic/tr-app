package im.gian.tr.restaurant.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentRestaurantMenuBinding
import im.gian.tr.restaurant.RestaurantActivity
import im.gian.tr.restaurant.RestaurantViewModel
import im.gian.tr.restaurant.menu.NoScrollExpandableListView
import java.util.ArrayList

class MenuFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentRestaurantMenuBinding>(
            inflater, R.layout.fragment_restaurant_menu, container, false)

        val restaurantViewModel: RestaurantViewModel = ViewModelProvider(context as FragmentActivity).get(
            RestaurantViewModel::class.java)

        binding.menu = this
        binding.lifecycleOwner = this
        binding.restaurantViewModel = restaurantViewModel

        val listData = HashMap<String, List<String>>()
        for(item in restaurantViewModel.menu.value!!) {
            val ingredients: MutableList<String> = mutableListOf()

            for(ingredient in item.ingredients)
                ingredients.add(ingredient["name"]!!)

            listData[item.name] = ingredients
        }

        //Menu expandable list
        binding.expandableListMenu.setAdapter(ExpandableListAdapter(context as FragmentActivity,
            restaurantViewModel.menu.value!!
        ))


        return binding.root
    }
}