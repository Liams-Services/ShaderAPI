package com.liamxsage.shaderapi

import com.liamxsage.klassicx.extensions.getLogger
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry


class Shaderapi : ModInitializer {

    override fun onInitialize() {
        getLogger().info("Initializing ShaderAPI")

        PayloadTypeRegistry.playC2S().register(ShaderRequestPayload.ID, ShaderRequestPayload.CODEC)
        PayloadTypeRegistry.playS2C().register(ShaderReceivePayload.ID, ShaderReceivePayload.CODEC)

        getLogger().info("ShaderAPI initialized")
    }
}
