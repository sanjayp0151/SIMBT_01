package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DownAdapter(private val mList :List<DownData>) : RecyclerView.Adapter<DownAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cloud_img_view_of_down_list)
        val dateText: TextView = itemView.findViewById(R.id.date_tv_down_list)
        val degreeText: TextView = itemView.findViewById(R.id.degree_tv_down_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.day_wise_item_list, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]


        holder.imageView.setImageResource(itemsViewModel.cloudImage)

        holder.dateText.text = itemsViewModel.date
        holder.degreeText.text = itemsViewModel.temperature
    }

}