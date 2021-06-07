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
    val PERMISSION_REQUEST_LOCATION = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Fetch data
        homeViewModel.fetchSaved()
        homeViewModel.fetchRestaurants()

        //Check and/or request location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            homeViewModel.fetchLocation(this)
        else
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),PERMISSION_REQUEST_LOCATION)

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
    }

    override fun onResume() {
        super.onResume()

        //Fetch data
        homeViewModel.fetchSaved()
        homeViewModel.fetchRestaurants()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_REQUEST_LOCATION) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                homeViewModel.fetchLocation(this)
        }
    }
}