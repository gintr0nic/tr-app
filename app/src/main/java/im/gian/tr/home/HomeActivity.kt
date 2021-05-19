package im.gian.tr.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import im.gian.tr.R
import im.gian.tr.databinding.ActivityHomeBinding
import me.ibrahimsn.lib.OnItemSelectedListener

class HomeActivity : AppCompatActivity() {
    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this,R.layout.activity_home)
        val navController = findNavController(R.id.navHostFragment)

        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        homeViewModel.setTest("oleeeeeeee")

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_bar_menu)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.homeFragment -> homeViewModel.setTitleTextRes(R.string.home)
                R.id.mapFragment -> homeViewModel.setTitleTextRes(R.string.map)
                R.id.savedFragment -> homeViewModel.setTitleTextRes(R.string.saved)
            }
        }
    }
}