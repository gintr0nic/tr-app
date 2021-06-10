package im.gian.tr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import im.gian.tr.home.HomeActivity
import im.gian.tr.intro.IntroActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = Firebase.auth.currentUser

        //If the user is not registered, go to Intro page (and then Login page)
        //Otherwise go directly to Home page
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