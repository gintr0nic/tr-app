package im.gian.tr.pages.restaurant.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import im.gian.tr.R
import im.gian.tr.databinding.FragmentDetailsBinding
import im.gian.tr.databinding.FragmentMenuBinding
import im.gian.tr.pages.restaurant.RestaurantViewModel

class MenuFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(
            inflater, R.layout.fragment_menu, container, false)

        val restaurantViewModel: RestaurantViewModel = ViewModelProvider(context as FragmentActivity).get(RestaurantViewModel::class.java)

        binding.menu = this
        binding.lifecycleOwner = this
        binding.restaurantViewModel = restaurantViewModel


        return binding.root
    }
}