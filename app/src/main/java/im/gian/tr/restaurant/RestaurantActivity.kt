package im.gian.tr.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.databinding.ActivityRestaurantBinding
import im.gian.tr.model.Restaurant

class RestaurantActivity : AppCompatActivity() {
    val restaurantViewModel: RestaurantViewModel by viewModels()

    private val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set current viewing restaurant and get data
        restaurantViewModel.setRestaurant(Gson().fromJson(intent.getStringExtra("restaurant"), Restaurant::class.java))
        restaurantViewModel.fetchCertifications()
        restaurantViewModel.fetchMenu()
        restaurantViewModel.setSaved(intent.getBooleanExtra("saved", false))

        val binding = DataBindingUtil.setContentView<ActivityRestaurantBinding>(this,R.layout.activity_restaurant)
        val navController = findNavController(R.id.navHostFragment)

        binding.restaurantViewModel = restaurantViewModel
        binding.lifecycleOwner = this

        //Image load
        storage.reference.child("images/${restaurantViewModel.restaurant.value?.id}").list(1).addOnSuccessListener { list->
            list.items.first().downloadUrl.addOnSuccessListener {
                Glide.with(binding.restaurantImageView).load(it).placeholder(R.drawable.restaurant_placeholder).into(binding.restaurantImageView)
            }
        }

        //Setup bottom bar
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_bar_menu_restaurant)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

    }
}