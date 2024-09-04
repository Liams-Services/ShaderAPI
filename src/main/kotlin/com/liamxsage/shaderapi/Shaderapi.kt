package com.liamxsage.shaderapi

import com.liamxsage.shaderapi.Constants.logger
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry


class Shaderapi : ModInitializer {

    override fun onInitialize() {
        logger.info("Initializing ShaderAPI")

        // Client 2 Server
        PayloadTypeRegistry.playC2S().register(ShaderRequestPayload.ID, ShaderRequestPayload.CODEC)
        PayloadTypeRegistry.playC2S().register(ShaderStatusResponsePayload.ID, ShaderStatusResponsePayload.CODEC)

        // Server 2 Client
        PayloadTypeRegistry.playS2C().register(ShaderReceivePayload.ID, ShaderReceivePayload.CODEC)

        logger.info("ShaderAPI initialized")
    }
}
