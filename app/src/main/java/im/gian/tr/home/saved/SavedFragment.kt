package im.gian.tr.home.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentHomeBinding
import im.gian.tr.databinding.FragmentSavedBinding
import im.gian.tr.databinding.FragmentSigninBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.home.home.RestaurantCardAdapter
import im.gian.tr.home.model.Restaurant
import im.gian.tr.intro.IntroViewModel

class SavedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSavedBinding>(
            inflater, R.layout.fragment_saved, container, false)

        val homeViewModel: HomeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        binding.saved = this
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        binding.recyclerViewSaved.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSaved.adapter = RestaurantCardAdapter(homeViewModel.saved.value, homeViewModel.userLocation.value, false)

        val savedObserver = Observer<List<Restaurant>> {
            binding.recyclerViewSaved.adapter = RestaurantCardAdapter(homeViewModel.saved.value, homeViewModel.userLocation.value, false)
        }
        homeViewModel.saved.observe(viewLifecycleOwner, savedObserver)

        homeViewModel.fetchSaved()

        return binding.root
    }
}