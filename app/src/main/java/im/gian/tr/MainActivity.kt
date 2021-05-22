package im.gian.tr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import im.gian.tr.home.HomeActivity
import im.gian.tr.intro.IntroActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),0)
        }

        val currentUser = Firebase.auth.currentUser

        if(currentUser == null) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        finish()
    }
}