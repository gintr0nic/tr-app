package im.gian.tr.home.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import im.gian.tr.R
import im.gian.tr.databinding.FragmentHomeBinding
import im.gian.tr.databinding.FragmentMapBinding
import im.gian.tr.databinding.FragmentSigninBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.home.home.RestaurantCardAdapter
import im.gian.tr.home.model.Restaurant
import im.gian.tr.intro.IntroViewModel

class MapFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        homeViewModel.restaurants.value?.forEach {
            googleMap.addMarker(MarkerOptions().position(LatLng(it.location.latitude,it.location.longitude)))
        }

        val newPos = homeViewModel.restaurants.value?.get(0)?.location
        if (newPos != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(newPos.latitude,newPos.longitude)))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        val restaurantsObserver = Observer<List<Restaurant>> {
            mapFragment?.getMapAsync(callback)
        }
        homeViewModel.restaurants.observe(viewLifecycleOwner, restaurantsObserver)
    }
}