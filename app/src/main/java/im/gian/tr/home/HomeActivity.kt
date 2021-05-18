package im.gian.tr.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import im.gian.tr.R
import im.gian.tr.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this,R.layout.activity_home)

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_bar_menu)
        val menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, findNavController(R.id.navHostFragment))
    }
}