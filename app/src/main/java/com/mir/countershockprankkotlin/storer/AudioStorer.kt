package com.mir.countershockprankkotlin.storer

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mir.countershockprankkotlin.R
import com.mir.countershockprankkotlin.helper.ShocklUtils
import com.mir.countershockprankkotlin.model.AudioModel

class AudioStorer (var context: Context) {
    val preferences: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        preferences = context.getSharedPreferences(ShocklUtils.SHOCK_SHARED_PREFS, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun addAudio(audio: AudioModel) {
        val audios = getStoredIAudios() as ArrayList<AudioModel>
        audios.add(audio)
        storeAudios(audios)
    }

    fun storeAudios(audios:List<AudioModel>) {
        val gson = Gson()
        editor.putString(context.getString(R.string.key_stored_audios), gson.toJson(audios))
        editor.commit()
    }

    private fun getStoredIAudios():List<AudioModel>{
        val audiosAsString = preferences.getString(context.getString(R.string.key_stored_audios), null)
        if (audiosAsString == null || audiosAsString.length == 0){
            return ArrayList()
        }
        val gson = Gson()
        val type = object: TypeToken<List<AudioModel>>(){}.type
        return gson.fromJson(audiosAsString, type)
    }

    fun getAllAudios():List<AudioModel>{
        val assetAudios = ArrayList<AudioModel>()
        assetAudios.add(AudioModel(0, "scream2", "Scream 2", true))
        assetAudios.add(AudioModel(1, "scream1", "Scream 1", true))
        assetAudios.add(AudioModel(2, "see_you", "Seeing You", true))
        assetAudios.add(AudioModel(3, "behind_you", "Behind you now", true))

        assetAudios.addAll(getStoredIAudios())
        return assetAudios
    }

    fun getSelectedAudio(): AudioModel {
        val audios = getAllAudios()

        val defaultAudio = audios.get(0)

        val audioId = preferences.getInt(context.getString(R.string.key_audio_id), 0)
        for (audio in audios){
            if (audio.id == audioId){
                return audio
            }
        }

        // Fall back on defaults
        editor.putInt(context.getString(R.string.key_audio_id), 0)
        editor.commit()

        return defaultAudio
    }

}