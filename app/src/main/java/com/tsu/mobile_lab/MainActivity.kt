package com.tsu.mobile_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.tsu.mobile_lab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var viewPager2: ViewPager2? = null

    private val pager2CallBack = object: ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)



            if(position == PageLists.introSlides.size - 1){
                binding?.buttonNavigation?.text = "Let's Start"
            }
            else{
                binding?.buttonNavigation?.text = "Next"
                binding?.buttonNavigation?.setOnClickListener{
                    viewPager2?.currentItem = position + 1
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupViewPager(binding!!)


    }

    private fun setupViewPager(binding: ActivityMainBinding){
        val adapter = IntroAdapter(PageLists.introSlides)
        viewPager2 = binding.viewPager
        viewPager2?.adapter = adapter
        viewPager2?.registerOnPageChangeCallback(pager2CallBack)
        binding.dots.setViewPager2(viewPager2!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2?.unregisterOnPageChangeCallback(pager2CallBack)
    }
}
