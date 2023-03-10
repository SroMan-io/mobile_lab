package com.tsu.mobile_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.tsu.mobile_lab.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private var binding: ActivityIntroBinding? = null

    private var viewPager2: ViewPager2? = null

    private var currentPosition: Int = 0

    private var amountOfIntroPages: Int = 0

    private val pager2CallBack = object: ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            currentPosition = position
            amountOfIntroPages = PageLists.introSlides.size

            if(position == amountOfIntroPages - 1){
                binding?.buttonNavigation?.text = "Let's Start"
                binding?.buttonNavigation?.setOnClickListener{
                    val intent = Intent(this@IntroActivity, SignUpActivity::class.java)
                    startActivity(intent)
                }
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
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupViewPager(binding!!)
        supportActionBar?.hide()

        binding?.buttonSkip?.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }



    }

    private fun setupViewPager(binding: ActivityIntroBinding){
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
