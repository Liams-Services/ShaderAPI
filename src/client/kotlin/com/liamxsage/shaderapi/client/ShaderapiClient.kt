package com.liamxsage.shaderapi.client

import com.liamxsage.klassicx.extensions.getLogger
import com.liamxsage.shaderapi.ShaderReceivePayload
import com.liamxsage.shaderapi.ShaderRequestPayload
import com.liamxsage.shaderapi.client.config.ConfigManager
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler


class ShaderapiClient : ClientModInitializer {

    override fun onInitializeClient() {
        getLogger().info("Initializing ShaderAPI Client")

        // Check for a disabling condition, such as a missing dependency or a config flag
        if (shouldDisableMod()) {
            getLogger().warn("ShaderAPI Client is disabled.")
            return  // Exit the initialization early
        }

        ClientPlayNetworking.registerGlobalReceiver(ShaderReceivePayload.ID, ShaderReceivePayloadHandler())

        getLogger().info("Registering ShaderRequestPayload Receiver")
        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { handler: ClientPlayNetworkHandler, sender: PacketSender, client: MinecraftClient ->
            ClientPlayNetworking.send(ShaderRequestPayload(true)).also { getLogger().info("ShaderRequestPayload sent") }
        })

        getLogger().info("Loading config")
        ConfigManager.readConfig()

        getLogger().info("ShaderAPI Client initialized")
    }

    private fun shouldDisableMod(): Boolean {
        // Example condition: Disable if Iris is not present
        try {
            Class.forName("net.irisshaders.iris.Iris")
        } catch (e: ClassNotFoundException) {
            getLogger().warn("ShaderAPI Client could not find Iris, disabling")
            return true
        }

        // Additional conditions can be checked here, like a config flag or environment variable
        return false
    }
}
