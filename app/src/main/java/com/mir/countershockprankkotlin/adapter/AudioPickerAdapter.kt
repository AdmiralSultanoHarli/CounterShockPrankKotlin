package com.mir.countershockprankkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mir.countershockprankkotlin.R
import com.mir.countershockprankkotlin.model.AudioModel

class AudioPickerAdapter(var items:List<AudioModel>, var callback: Callback) :
    RecyclerView.Adapter<AudioPickerAdapter.ViewHoler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.audio_item, parent, false)

        return ViewHoler(view)
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = items.get(position)
        holder.itemView.setOnClickListener{
            callback.itemSelected(item)
        }

        holder.textView.setText(item.descriptionMessage)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView:TextView

        init {
            textView = itemView.findViewById(R.id.txt_view)
        }

    }

    interface Callback{
        fun itemSelected(item: AudioModel)
    }



}