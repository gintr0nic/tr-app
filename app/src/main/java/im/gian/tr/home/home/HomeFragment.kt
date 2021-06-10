package im.gian.tr.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentHomeBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.model.Restaurant


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false)

        val homeViewModel: HomeViewModel = ViewModelProvider(context as FragmentActivity).get(HomeViewModel::class.java)

        binding.home = this
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        //Search textview
        binding.textViewSearch.threshold = 1
        val restaurantsObserver = Observer<List<Restaurant>> {
            if(context != null)
                binding.textViewSearch.setAdapter(SuggestionAdapter(context as FragmentActivity, R.layout.suggestion_view, homeViewModel.restaurants.value!!, homeViewModel.saved.value!!))
        }
        homeViewModel.restaurants.observe(context as FragmentActivity, restaurantsObserver)

        //Restaurants recyclerview
        val adapter = RestaurantCardAdapter(context, false)
        binding.recyclerViewNew.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNew.adapter = adapter

        //Restaurants recyclerview (sorted by distance)
        val sortedAdapter = RestaurantCardAdapter(context, true)
        binding.recyclerViewNear.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNear.adapter = sortedAdapter

        return binding.root
    }



}