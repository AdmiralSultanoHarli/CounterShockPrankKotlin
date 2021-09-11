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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mir.countershockprankkotlin.model.ImageModel
import com.mir.countershockprankkotlin.adapter.ImagePickerAdapter
import com.mir.countershockprankkotlin.storer.ImageStorer
import com.mir.countershockprankkotlin.R
import com.mir.countershockprankkotlin.helper.ShocklUtils

class ImagePickerDialogFragment: DialogFragment(), ImagePickerAdapter.Callback {

    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var adapter: ImagePickerAdapter
    lateinit var rvContent: RecyclerView
    lateinit var glContent: GridLayoutManager

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

        val items = ImageStorer(context!!).getAllImages()

        adapter = ImagePickerAdapter(items, this)

        rvContent = view.findViewById(R.id.rv_content)
        rvContent.adapter = adapter
        glContent = GridLayoutManager(context, 3)
        rvContent.layoutManager = glContent

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }

    override fun itemSelected(item: ImageModel) {
        editor.putInt(getString(R.string.key_photo_id), item.id)
        editor.commit()
        dismiss()
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(Intent(ShocklUtils.MEDIA_UPDATED_ACTION))
    }

}