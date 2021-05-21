package im.gian.tr.home.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        homeViewModel.restaurants.value?.forEach {
            googleMap.addMarker(MarkerOptions().position(LatLng(it.position.latitude,it.position.longitude)))
        }

        val newPos = homeViewModel.restaurants.value?.get(0)?.position
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