package com.tsu.mobile_lab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobile_lab.databinding.ActivityIntroBinding
import com.tsu.mobile_lab.databinding.Viewpager2IntroBinding

class IntroAdapter(private val introList: List<Intro>): RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {
    inner class IntroViewHolder(private val binding: Viewpager2IntroBinding):RecyclerView.ViewHolder(binding.root){
        fun bindItem(intro: Intro){
            binding.textView1.text=intro.title
            binding.textView2.text=intro.desc
            binding.imageView.setImageResource(intro.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        return IntroViewHolder(
            Viewpager2IntroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return introList.size
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bindItem(introList[position])
    }
}