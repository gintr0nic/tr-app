package im.gian.tr.restaurant.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import im.gian.tr.R
import im.gian.tr.databinding.FragmentRestaurantContactsBinding
import im.gian.tr.restaurant.RestaurantViewModel

class ContactsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRestaurantContactsBinding>(
            inflater, R.layout.fragment_restaurant_contacts, container, false)

        val restaurantViewModel: RestaurantViewModel = ViewModelProvider(context as FragmentActivity).get(
            RestaurantViewModel::class.java)

        binding.contacts = this
        binding.lifecycleOwner = this
        binding.restaurantViewModel = restaurantViewModel

        return binding.root
    }
}