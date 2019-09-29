package com.example.mikita.gifsearcher

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mikita.gifsearcher.Model.GifObjectModel
import com.example.mikita.gifsearcher.Model.ImageObjectModel
import com.example.mikita.gifsearcher.Model.ImagesObjectModel
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.gif_list_item.view.*
import kotlin.math.roundToInt

class GifsAdapter(
//    val items: ArrayList<GifObjectModel>,
    val context: Context,
    val orientation: Int
) : PagedListAdapter<GifObjectModel, GifsAdapter.ViewHolder>(diffCallback) {

    var recyclerViewWidth: Int = 0
    var recyclerViewHeight: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        recyclerViewWidth = parent.width
        recyclerViewHeight = parent.height

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.gif_list_item, parent, false))
    }

    fun selectGifUriToLoad(images: ImagesObjectModel): ImageObjectModel {
        val image = images.downsized
            ?: if (images.fixedHeight != null) {
                images.fixedHeight
            } else {
                images.original
            }
        return image
    }

    fun getHeightForTile(realH: Int, realW: Int, holderWidth: Int): Int {
        //context.resources.displayMetrics.widthPixels)
        return (realH / (realW.toFloat() / holderWidth)).roundToInt()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gif = getItem(position)!!

        val image = selectGifUriToLoad(gif.images)
        val c = Fresco.newDraweeControllerBuilder()
            .setUri(if (image.webp != null) image.webp else image.url)
            .setAutoPlayAnimations(true)
            .build()

        val height = image.height.toInt()
        val width = image.width.toInt()
        val cardWidth = if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            (recyclerViewWidth / 2) else recyclerViewWidth
        holder.gifImageView.layoutParams.height = getHeightForTile(height, width, cardWidth)
        holder.gifImageView.controller = c
        holder.titleTextView.text = if (gif.title != "") gif.title else gif.slug
        holder.authorTextView.text = if (gif.username != "") gif.username else context.getString(R.string.unknown_user)
        holder.sourceTextView.text = if (gif.source != "") gif.source else gif.url
        holder.view.setOnClickListener {
            if (it.additional_info__layout.visibility == View.GONE) {
                it.additional_info__layout.visibility = View.VISIBLE
            } else {
                it.additional_info__layout.visibility = View.GONE
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val gifImageView = view.gif_list_item__image_view
        val titleTextView = view.gif_list_item__title_text_view
        val authorTextView = view.gif_list_item__author_text_view
        val sourceTextView = view.gif_list_item__source_text_view
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