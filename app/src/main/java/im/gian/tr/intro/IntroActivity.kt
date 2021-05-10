package im.gian.tr.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import im.gian.tr.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter = IntroPagerAdapter(this)
    }
}