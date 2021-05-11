package im.gian.tr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import im.gian.tr.intro.IntroActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firstRun = true

        if(firstRun) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            //start home activity
        }
    }
}