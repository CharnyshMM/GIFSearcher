package com.example.mikita.gifsearcher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mikita.gifsearcher.Model.GifObjectModel
import com.example.mikita.gifsearcher.Model.ImageObjectModel
import kotlinx.android.synthetic.main.gif_list_item.view.*

class GifsAdapter(
    val items: ArrayList<GifObjectModel>,
    val context: Context
) : RecyclerView.Adapter<GifsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.gif_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).asGif().load(items[position].images.original.url).into(holder.gifImageView)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val gifImageView = view.gif_list_item__image_view
    }

}