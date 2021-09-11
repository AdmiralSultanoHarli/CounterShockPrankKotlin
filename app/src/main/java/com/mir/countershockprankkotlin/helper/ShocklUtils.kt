package com.mir.countershockprankkotlin.helper

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.File

class ShocklUtils {

    companion object {
        val SHOCK_SHARED_PREFS = "shock_shared_prefs"

        val STARTING_ID = 1000

        val MEDIA_UPDATED_ACTION = "media_updated_action"

        fun getRawUri(context: Context, assetName:String):Uri{
            return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator +
                File.separator + context.packageName + "/raw/" + assetName)
        }

        fun getDrawableUri(context: Context, assetName: String):Uri{
            return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator +
                    File.separator + context.packageName + "/drawable/" + assetName)
        }

    }

}