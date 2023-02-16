package com.example.avatar.view

import android.content.Context
import android.hardware.lights.Light
import android.view.SurfaceView
import com.google.android.filament.*
import com.google.android.filament.android.UiHelper
import com.google.android.filament.utils.*
import java.util.concurrent.Executors

class AvatarView(context: Context) : SurfaceView(context) {
    private val renderer: Renderer
    private val engine: Engine
    private val scene: Scene
    private val light: Light
    private val helper: UiHelper

    init {
        val executor = Executors.newSingleThreadExecutor()
        engine = Engine.create(executor)
        renderer = engine.createRenderer()
        scene = engine.createScene()
        helper = UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK)
        helper.attachTo(this)

        val directLight = LightManager.Builder(LightManager.Type.SUN)
            .color(1.0f, 1.0f, 1.0f)
            .intensity(400_000.0f)
            .direction(0.0f, -1.0f, -1.0f)
            .build(engine)
        light = directLight

        renderer.scene = scene
        renderer.view = helper.view
        renderer.skybox = Skybox.Builder().color(0.1f, 0.1f, 0.1f).build(engine)
        helper.renderCallback = RenderCallback { renderer.render(it) }
    }

    fun setRenderable(renderable: FilamentAsset) {
        val entityManager = engine.entityManager
        val materialInstance = MaterialInstance.Builder(renderable.defaultMaterialInstance)
            .setParameter("baseColor", MaterialInstance.FloatElement(Float4(1.0f, 1.0f, 1.0f, 1.0f)))
            .build(engine)

        val entity = entityManager.create()
        entityManager.addComponent(entity, TransformManager().create())
        entityManager.addComponent(entity, RenderableManager().create())
        entityManager.addComponent(entity, MaterialInstanceManager().create())
        entityManager.setTransform(entity, Matrix4x4())

        val renderableManager = engine.renderableManager
        renderableManager.create(entity)
        renderableManager.setFilamentAsset(entity, renderable)
        renderableManager.setMaterialInstanceAt(entity, 0, materialInstance)

        scene.addEntity(entity)
    }

    fun cleanup() {
        helper.detach()
        renderer.destroy()
        engine.destroy()
    }
}