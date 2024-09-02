package com.liamxsage.shaderapi

import com.liamxsage.shaderapi.Constants.logger
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry


class Shaderapi : ModInitializer {

    override fun onInitialize() {
        logger.info("Initializing ShaderAPI")

        PayloadTypeRegistry.playC2S().register(ShaderRequestPayload.ID, ShaderRequestPayload.CODEC)
        PayloadTypeRegistry.playS2C().register(ShaderReceivePayload.ID, ShaderReceivePayload.CODEC)

        logger.info("ShaderAPI initialized")
    }
}
