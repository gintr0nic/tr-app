package im.gian.tr.home

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.transition.Visibility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import im.gian.tr.R
import im.gian.tr.databinding.ActivityHomeBinding
import im.gian.tr.model.Restaurant
import im.gian.tr.model.UserType
import me.ibrahimsn.lib.OnItemSelectedListener

class HomeActivity : AppCompatActivity() {
    val homeViewModel: HomeViewModel by viewModels()
    private val PERMISSION_REQUEST_LOCATION = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Check and/or request location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            homeViewModel.fetchLocation(this)
        else
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),PERMISSION_REQUEST_LOCATION)

        //Fetch data
        homeViewModel.fetchUserType()
        homeViewModel.fetchSaved()
        homeViewModel.fetchRestaurants()

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

        //Show edit profile button if user is restaurant or producer
        val userTypeObserver = Observer<UserType> {
            if(homeViewModel.userType.value == UserType.RESTAURANT || homeViewModel.userType.value == UserType.PRODUCER)
                binding.imageViewProfile.visibility = View.VISIBLE
        }
        homeViewModel.userType.observe(this, userTypeObserver)
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

    override fun onBackPressed() {
        //super.onBackPressed()

        AlertDialog.Builder(this)
            .setTitle(R.string.exit)
            .setMessage(R.string.sure_exit)
            .setPositiveButton(R.string.yes) { _, _ ->
                finish()
            }
            .setNegativeButton(R.string.no, null).show()
    }
}