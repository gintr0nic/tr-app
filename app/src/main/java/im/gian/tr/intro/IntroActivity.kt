package im.gian.tr.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import im.gian.tr.R
import im.gian.tr.databinding.ActivityIntroBinding
import im.gian.tr.login.LoginActivity
import me.relex.circleindicator.CircleIndicator

class IntroActivity : AppCompatActivity() {

    val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val binding: ActivityIntroBinding = DataBindingUtil.setContentView(this,R.layout.activity_intro)

        binding.viewPager.adapter = IntroPagerAdapter(this)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageSelected(position: Int) {
                viewModel.setButtonRes(position)
            }
        })

        binding.button.setOnClickListener{
            val pages = (binding.viewPager.adapter as IntroPagerAdapter).count
            val current = binding.viewPager.currentItem

            when {
                current < pages - 1 -> binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
                else -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.circleIndicator.setViewPager(binding.viewPager)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}