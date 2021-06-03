package im.gian.tr.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.databinding.ActivityRestaurantBinding
import im.gian.tr.model.Restaurant

class RestaurantActivity : AppCompatActivity() {
    val restaurantViewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityRestaurantBinding>(this,R.layout.activity_restaurant)
        val navController = findNavController(R.id.navHostFragment)

        binding.restaurantViewModel = restaurantViewModel
        binding.lifecycleOwner = this

        restaurantViewModel.setRestaurant(Gson().fromJson(intent.getStringExtra("restaurant"), Restaurant::class.java))

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_bar_menu_restaurant)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

    }
}