package im.gian.tr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import im.gian.tr.intro.IntroActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = Firebase.auth.currentUser

        if(currentUser == null) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            //TODO: Start home activity
        }
    }
}