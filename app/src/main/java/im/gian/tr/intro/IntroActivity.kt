package im.gian.tr.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import im.gian.tr.R
import im.gian.tr.databinding.ActivityIntroBinding
import im.gian.tr.login.LoginActivity

class IntroActivity : AppCompatActivity() {

    val introViewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityIntroBinding>(this,R.layout.activity_intro)

        binding.introViewModel = introViewModel
        binding.lifecycleOwner = this

        binding.viewPager.adapter = IntroPagerAdapter(this)

        //Change button text when page changes
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
                introViewModel.setButtonRes(position)
            }
        })

        //Go to next page
        //If user reaches last page go to Login activity
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

        //Circle indicator to keep track of current page
        binding.circleIndicator.setViewPager(binding.viewPager)
    }
}