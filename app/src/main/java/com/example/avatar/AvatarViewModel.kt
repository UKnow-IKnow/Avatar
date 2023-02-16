package com.example.avatar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.avatar.model.AvatarModel
import com.google.android.filament.gltfio.FilamentAsset

class AvatarViewModel(application: Application) : AndroidViewModel(application) {
    private val avatarModel = AvatarModel(application)

    fun loadAvatarModel() {
        avatarModel.loadAvatarModel()
    }

    fun getRenderable(): FilamentAsset? {
        return avatarModel.getRenderable()
    }
