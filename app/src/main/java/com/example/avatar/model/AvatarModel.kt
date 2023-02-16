package com.example.avatar.model

import android.app.Application
import android.net.Uri
import com.google.android.filament.gltfio.FilamentAsset
import com.google.android.filament.gltfio.FilamentAssetLoader
import java.io.IOException

class AvatarModel(application: Application) {
    private val assetLoader = FilamentAssetLoader(application.assets, FilamentAssetLoader.ASSET_EXTRACTOR)
    private var renderable: FilamentAsset? = null

    fun loadAvatarModel() {
        try {
            val glbAsset = assetLoader.createAsset(Uri.parse("file:///android_asset/avatar.glb"))
            renderable = glbAsset
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getRenderable(): FilamentAsset? {
        return renderable
    }
}