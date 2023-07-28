package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeftAdapter(private val mList :List<LeftData>) : RecyclerView.Adapter<LeftAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cloud_img_view_of_left_list)
        val timeText: TextView = itemView.findViewById(R.id.time_tv_left_list)
        val degreeText: TextView = itemView.findViewById(R.id.degree_tv_left_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.houre_wise_item_list, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]


        holder.imageView.setImageResource(itemsViewModel.cloudImage)

        holder.timeText.text = itemsViewModel.time
        holder.degreeText.text = itemsViewModel.temperature
    }
}