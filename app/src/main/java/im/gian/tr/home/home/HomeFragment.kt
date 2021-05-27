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
import androidx.fragment.app.FragmentActivity
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

        val homeViewModel: HomeViewModel = ViewModelProvider(context as FragmentActivity).get(HomeViewModel::class.java)

        binding.home = this
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        val adapter = RestaurantCardAdapter(context, false)
        val sortedAdapter = RestaurantCardAdapter(context, true)

        binding.recyclerViewNew.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNew.adapter = adapter

        binding.recyclerViewNear.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNear.adapter = sortedAdapter

        homeViewModel.fetchSaved()
        homeViewModel.fetchRestaurants()

        return binding.root
    }



}