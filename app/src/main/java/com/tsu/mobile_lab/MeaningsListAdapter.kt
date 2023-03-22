package com.tsu.mobile_lab

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsu.mobile_lab.databinding.ItemMeaningsListBinding

class MeaningsListAdapter(private val meanings: MutableList<SpannableString>): RecyclerView.Adapter<MeaningsListAdapter.MeaningsViewHolder>() {

    class MeaningsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val viewBinding = ItemMeaningsListBinding.bind(view)

        fun bind(text: SpannableString) {
            viewBinding.itemTextView.text = text
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningsViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meanings_list, parent, false)

        return MeaningsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return meanings.size
    }

    override fun onBindViewHolder(holder: MeaningsViewHolder, position: Int) {
        holder.bind(meanings[position])
    }


}