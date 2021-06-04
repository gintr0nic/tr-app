package im.gian.tr.home.saved

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
import im.gian.tr.databinding.FragmentSavedBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.model.Restaurant

class SavedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSavedBinding>(
            inflater, R.layout.fragment_saved, container, false)

        val homeViewModel: HomeViewModel = ViewModelProvider(context as FragmentActivity).get(HomeViewModel::class.java)

        binding.saved = this
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        //Saved recyclerview
        binding.recyclerViewSaved.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSaved.adapter = SavedCardAdapter(context)

        return binding.root
    }
}