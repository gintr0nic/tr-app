package im.gian.tr.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentHomeBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.home.model.Restaurant

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false)

        val homeViewModel: HomeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        binding.home = this
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel


        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = RestaurantCardAdapter(homeViewModel.restaurants.value, homeViewModel.userLocation.value)

        binding.recyclerView1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView1.adapter = RestaurantCardAdapter(homeViewModel.restaurants.value, homeViewModel.userLocation.value)

        val restaurantsObserver = Observer<List<Restaurant>> {
            binding.recyclerView.adapter = RestaurantCardAdapter(homeViewModel.restaurants.value, homeViewModel.userLocation.value)
            binding.recyclerView1.adapter = RestaurantCardAdapter(homeViewModel.restaurants.value, homeViewModel.userLocation.value)
        }
        homeViewModel.restaurants.observe(viewLifecycleOwner, restaurantsObserver)

        homeViewModel.fetchRestaurants()

        return binding.root
    }



}