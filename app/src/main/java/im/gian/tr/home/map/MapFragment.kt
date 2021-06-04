package im.gian.tr.home.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import im.gian.tr.R
import im.gian.tr.home.HomeViewModel
import im.gian.tr.model.Restaurant

class MapFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        //Add restaurant markers
        homeViewModel.restaurants.value?.forEach {
            googleMap.addMarker(MarkerOptions().position(LatLng(it.location.latitude,it.location.longitude)))
        }

        //Move camera to first restaurant in list
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
        homeViewModel = ViewModelProvider(context as FragmentActivity).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        //Update map when restaurant list changes
        val restaurantsObserver = Observer<List<Restaurant>> {
            mapFragment?.getMapAsync(callback)
        }
        homeViewModel.restaurants.observe(viewLifecycleOwner, restaurantsObserver)
    }
}