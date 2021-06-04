package im.gian.tr.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import im.gian.tr.R
import im.gian.tr.databinding.ActivityHomeBinding
import me.ibrahimsn.lib.OnItemSelectedListener

class HomeActivity : AppCompatActivity() {
    val homeViewModel: HomeViewModel by viewModels()
    private lateinit var locationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this,R.layout.activity_home)
        val navController = findNavController(R.id.navHostFragment)

        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        //Setup bottom bar
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_bar_menu)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        //Change title according to selected page
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.homeFragment -> homeViewModel.setTitleTextRes(R.string.app_name)
                R.id.mapFragment -> homeViewModel.setTitleTextRes(R.string.map)
                R.id.savedFragment -> homeViewModel.setTitleTextRes(R.string.saved)
            }
        }

        //Check location permission and get last known coarse location
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    Log.d("location",location.toString())
                    if (location != null) {
                        homeViewModel.setUserLocation(location)
                    }
                }
        }
    }
}