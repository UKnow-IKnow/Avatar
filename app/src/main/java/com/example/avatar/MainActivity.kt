package com.example.avatar


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.avatar.view.AvatarView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AvatarViewModel
    private lateinit var avatarView: AvatarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        avatarView = AvatarView(this)
        setContentView(avatarView)

        viewModel = ViewModelProvider(this).get(AvatarViewModel::class.java)
        viewModel.loadAvatarModel()
        viewModel.getRenderable()?.let { avatarView.setRenderable(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        avatarView.cleanup()
    }
}