package im.gian.tr.home.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import im.gian.tr.R
import im.gian.tr.databinding.FragmentHomeBinding
import im.gian.tr.databinding.FragmentMapBinding
import im.gian.tr.databinding.FragmentSigninBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.intro.IntroViewModel

class MapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMapBinding>(
            inflater, R.layout.fragment_map, container, false)

        val homeViewModel: HomeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        binding.map = this
        binding.homeViewModel = homeViewModel


        return binding.root
    }
}