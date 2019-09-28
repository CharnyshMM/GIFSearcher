package com.example.mikita.gifsearcher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mikita.gifsearcher.Model.GifObjectModel
import com.example.mikita.gifsearcher.Model.ImageObjectModel
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import kotlinx.android.synthetic.main.gif_list_item.view.*

class GifsAdapter(
//    val items: ArrayList<GifObjectModel>,
    val context: Context
) : PagedListAdapter<GifObjectModel, GifsAdapter.ViewHolder> (diffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.gif_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gif = getItem(position)!!
        val c = Fresco.newDraweeControllerBuilder()
            .setUri(gif.images.original.webp)
            .setAutoPlayAnimations(true)
            .build()

        holder.gifImageView.controller = c
        holder.titleTextView.text = gif.title
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val gifImageView = view.gif_list_item__image_view
        val titleTextView = view.gif_list_item__title_text_view
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<GifObjectModel>() {
            override fun areItemsTheSame(oldItem: GifObjectModel, newItem: GifObjectModel): Boolean {
                return oldItem.id == newItem.id && oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: GifObjectModel, newItem: GifObjectModel): Boolean {
                return oldItem.source == newItem.source && oldItem.title == newItem.title
            }

        }
    }
}