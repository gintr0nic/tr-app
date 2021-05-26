package im.gian.tr.home.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),0)
        }

        val homeViewModel: HomeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        binding.home = this
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        val adapter = RestaurantCardAdapter(homeViewModel.restaurants.value, homeViewModel.saved.value, homeViewModel.userLocation.value, false)
        val sortedAdapter = RestaurantCardAdapter(homeViewModel.restaurants.value, homeViewModel.saved.value, homeViewModel.userLocation.value, true)

        //TODO: Refactor this shit!!!!
        binding.recyclerViewNew.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNew.adapter = adapter

        binding.recyclerViewNear.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNear.adapter = sortedAdapter

        val restaurantsObserver = Observer<List<Restaurant>> {
            binding.recyclerViewNew.adapter = RestaurantCardAdapter(
                homeViewModel.restaurants.value,
                homeViewModel.saved.value,
                homeViewModel.userLocation.value,
                false)

            binding.recyclerViewNear.adapter = RestaurantCardAdapter(
                homeViewModel.restaurants.value,
                homeViewModel.saved.value,
                homeViewModel.userLocation.value,
                true)
        }
        homeViewModel.restaurants.observe(viewLifecycleOwner, restaurantsObserver)

        val savedObserver = Observer<List<Restaurant>> {
            binding.recyclerViewNew.adapter = RestaurantCardAdapter(
                homeViewModel.restaurants.value,
                homeViewModel.saved.value,
                homeViewModel.userLocation.value,
                false)

            binding.recyclerViewNear.adapter = RestaurantCardAdapter(
                homeViewModel.restaurants.value,
                homeViewModel.saved.value,
                homeViewModel.userLocation.value,
                true)
        }
        homeViewModel.saved.observe(viewLifecycleOwner, savedObserver)

        homeViewModel.fetchSaved()
        homeViewModel.fetchRestaurants()

        return binding.root
    }



}