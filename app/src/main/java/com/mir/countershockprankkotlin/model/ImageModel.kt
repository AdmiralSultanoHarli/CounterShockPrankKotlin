package com.mir.countershockprankkotlin.model

import java.io.Serializable

class ImageModel: Serializable {

    val id:Int
    val imgFileName:String
    val isAsset:Boolean

    constructor(id: Int, imgFileName: String, isAsset: Boolean) {
        this.id = id
        this.imgFileName = imgFileName
        this.isAsset = isAsset
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageModel

        if (id != other.id) return false
        if (imgFileName != other.imgFileName) return false
        if (isAsset != other.isAsset) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + imgFileName.hashCode()
        result = 31 * result + isAsset.hashCode()
        return result
    }


}