package im.gian.tr.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import im.gian.tr.R

class IntroPagerAdapter(private val context: Context) : PagerAdapter() {
    val pages = 2

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var layout = LayoutInflater.from(context).inflate(R.layout.frame_intro_one, container, false)

        when (position){
            0 -> layout = LayoutInflater.from(context).inflate(R.layout.frame_intro_one, container, false)
            1 -> layout = LayoutInflater.from(context).inflate(R.layout.frame_intro_two, container, false)
        }

        container.addView(layout)
        //viewModel.setButtonRes(position)
        return layout
    }

    override fun getCount(): Int {
        return pages
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

}