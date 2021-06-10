package im.gian.tr.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.databinding.ActivityHomeBinding
import im.gian.tr.model.Restaurant
import im.gian.tr.model.UserType
import im.gian.tr.producer.ProducerActivity
import im.gian.tr.restaurant.RestaurantActivity

class HomeActivity : AppCompatActivity() {
    val homeViewModel: HomeViewModel by viewModels()

    private val PERMISSION_REQUEST_LOCATION = 0
    private val user = Firebase.auth

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

        //Profile button
        binding.imageViewProfile.setOnClickListener {

            //If user is restaurant start restaurant acitvity in edit mode
            if(homeViewModel.userType.value == UserType.RESTAURANT){
                homeViewModel.getUserRestaurant {
                    val intent = Intent(this, RestaurantActivity::class.java)
                    val restaurant = it.toObject(Restaurant::class.java)
                    restaurant?.id = user.uid!!
                    intent.putExtra("restaurant", Gson().toJson(restaurant))
                    intent.putExtra("edit", true)
                    startActivity(intent)
                }
            }

            //If user is producer start producer acitvity in edit mode
            if(homeViewModel.userType.value == UserType.PRODUCER){
                val intent = Intent(this, ProducerActivity::class.java)
                intent.putExtra("producer", user.uid)
                intent.putExtra("edit", true)
                startActivity(intent)
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

        //Fetch location if user accepted permission
        if(requestCode == PERMISSION_REQUEST_LOCATION) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                homeViewModel.fetchLocation(this)
        }
    }

    override fun onBackPressed() {
        //Warn user before exit
        AlertDialog.Builder(this)
            .setTitle(R.string.exit)
            .setMessage(R.string.sure_exit)
            .setPositiveButton(R.string.yes) { _, _ ->
                finishAffinity()
            }
            .setNegativeButton(R.string.no, null).show()
    }
}