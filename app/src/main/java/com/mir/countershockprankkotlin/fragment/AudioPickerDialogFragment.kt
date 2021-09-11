package com.mir.countershockprankkotlin.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mir.countershockprankkotlin.model.AudioModel
import com.mir.countershockprankkotlin.adapter.AudioPickerAdapter
import com.mir.countershockprankkotlin.storer.AudioStorer
import com.mir.countershockprankkotlin.R
import com.mir.countershockprankkotlin.helper.ShocklUtils

class AudioPickerDialogFragment: DialogFragment(), AudioPickerAdapter.Callback {

    lateinit var preferences:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    lateinit var adapter: AudioPickerAdapter
    lateinit var rvContent: RecyclerView
    lateinit var llContent: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        preferences = context!!.getSharedPreferences(ShocklUtils.SHOCK_SHARED_PREFS, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_media_picker, container, false)

        val items = AudioStorer(context!!).getAllAudios()

        adapter = AudioPickerAdapter(items, this)

        rvContent = view.findViewById(R.id.rv_content)
        rvContent.adapter = adapter
        llContent = LinearLayoutManager(context)
        rvContent.layoutManager = llContent

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }


    override fun itemSelected(item: AudioModel) {
        editor.putInt(getString(R.string.key_audio_id), item.id)
        editor.commit()
        dismiss()
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(Intent(ShocklUtils.MEDIA_UPDATED_ACTION))
    }


}