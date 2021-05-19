package im.gian.tr.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import im.gian.tr.R
import im.gian.tr.databinding.FragmentHomeBinding
import im.gian.tr.databinding.FragmentSavedBinding
import im.gian.tr.databinding.FragmentSigninBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.intro.IntroViewModel

class SavedFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSavedBinding>(
            inflater, R.layout.fragment_saved, container, false)

        binding.saved = this
        binding.homeViewModel = homeViewModel


        return binding.root
    }
}