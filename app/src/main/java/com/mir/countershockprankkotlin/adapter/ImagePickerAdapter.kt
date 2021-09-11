package com.mir.countershockprankkotlin.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mir.countershockprankkotlin.R
import com.mir.countershockprankkotlin.helper.ShocklUtils
import com.mir.countershockprankkotlin.model.ImageModel
import java.io.File

class ImagePickerAdapter(var items:List<ImageModel>, var callback: Callback) :
    RecyclerView.Adapter<ImagePickerAdapter.ViewHoler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)

        return ViewHoler(view)
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = items.get(position)
        holder.itemView.setOnClickListener{
            callback.itemSelected(item)
        }

        val imgUri:Uri
        if (item.isAsset){
            imgUri = ShocklUtils.getDrawableUri(holder.itemView.context, item.imgFileName)
        } else {
            imgUri = Uri.fromFile(File(item.imgFileName))
        }

        Glide.with(holder.itemView.context)
            .load(imgUri)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.img_grid)
        }

    }

    interface Callback{
        fun itemSelected(item: ImageModel)
    }

}