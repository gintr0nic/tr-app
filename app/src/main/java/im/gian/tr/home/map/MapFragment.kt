package im.gian.tr.home.map

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.home.HomeViewModel
import im.gian.tr.model.Restaurant
import im.gian.tr.restaurant.RestaurantActivity

class MapFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        val map: HashMap<Marker, String> = HashMap()

        //Add restaurant markers
        homeViewModel.restaurants.value?.forEach {
            val markerOptions = MarkerOptions().position(LatLng(it.location.latitude,it.location.longitude))
                .icon(bitmapDescriptorFromVector(context as FragmentActivity, R.drawable.ic_map_marker))
                .title(it.name)

            val marker = googleMap.addMarker(markerOptions)
            marker!!.tag = it
        }

        googleMap.setOnInfoWindowClickListener {
            val intent = Intent(context, RestaurantActivity::class.java)
            intent.putExtra("restaurant", Gson().toJson(it.tag))

            if (homeViewModel.saved.value?.contains(it.tag) == true)
                intent.putExtra("saved", true)

            context?.startActivity(intent)
        }

        //Move camera to first restaurant in list
        val newPos = homeViewModel.userLocation.value
        if (newPos != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(newPos.latitude,newPos.longitude), 12.0f))
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

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, 100, 157)
            val bitmap = Bitmap.createBitmap(100, 157, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}