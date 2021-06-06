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
import im.gian.tr.restaurant.RestaurantViewModel

class MenuFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = DataBindingUtil.inflate<FragmentRestaurantMenuBinding>(
            inflater, R.layout.fragment_restaurant_menu, container, false)

        val restaurantViewModel: RestaurantViewModel = ViewModelProvider(context as FragmentActivity).get(
            RestaurantViewModel::class.java)

        binding.menu = this
        binding.lifecycleOwner = this
        binding.restaurantViewModel = restaurantViewModel

        //Menu recyclerview
        binding.recyclerViewMenu.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMenu.adapter = MenuAdapter(context)


        return binding.root
    }
}