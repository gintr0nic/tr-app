package im.gian.tr.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import im.gian.tr.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textView: TextView = findViewById(R.id.textView)
        textView.text = "Utente loggato: ${Firebase.auth.currentUser.email}"
    }
}