package im.gian.tr.pages.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import im.gian.tr.R
import im.gian.tr.databinding.ActivityRestaurantBinding

class RestaurantActivity : AppCompatActivity() {
    val restaurantViewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityRestaurantBinding>(this,R.layout.activity_restaurant)
        val navController = findNavController(R.id.navHostFragment)

        //setSupportActionBar(findViewById(R.id.toolbar))
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.restaurantViewModel = restaurantViewModel
        binding.lifecycleOwner = this

        restaurantViewModel.getRestaurant(intent.getStringExtra("id")!!)

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_bar_menu_restaurant)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

    }
}