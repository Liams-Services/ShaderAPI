package com.liamxsage.shaderapi.client

import com.liamxsage.shaderapi.Constants.logger
import com.liamxsage.shaderapi.ShaderReceivePayload
import com.liamxsage.shaderapi.client.config.ConfigManager
import com.liamxsage.shaderapi.client.config.ShaderPackAcceptState
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.irisshaders.iris.Iris
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ConfirmScreen
import net.minecraft.text.Text
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.util.*

class ShaderReceivePayloadHandler : ClientPlayNetworking.PlayPayloadHandler<ShaderReceivePayload> {
    override fun receive(payload: ShaderReceivePayload, context: ClientPlayNetworking.Context) {
        logger.info("ShaderReceivePayload received")
        val shaderUrl: String = payload.shaderUrl ?: return
        val shaderHash: String = payload.hash ?: return
        val serverGroup: String = payload.serverGroup.ifEmpty { context.player().server?.serverIp ?: "global" }

        if (testForServerGroupAlwaysAccept(serverGroup)) {
            downloadAndApplyShaderPack(shaderUrl, shaderHash)
            return
        }

        val client = context.client()
        client.execute {
            client.setScreen(ConfirmScreen({ accept: Boolean ->
                if (accept) {
                    downloadAndApplyShaderPack(shaderUrl, shaderHash)
                    context.client().setScreen(null)
                    ConfigManager.addServerGroup(serverGroup, ShaderPackAcceptState.ACCEPT)
                    return@ConfirmScreen
                }

               context.client().setScreen(null)
                ConfigManager.addServerGroup(serverGroup, ShaderPackAcceptState.DENY)

            }, Text.of("Shader Pack Available"), Text.of("Do you want to download and apply the shader pack?")))
        }
    }

    private fun testForServerGroupAlwaysAccept(serverGroup: String): Boolean {
        return ConfigManager.getShaderPackAcceptState(serverGroup) == ShaderPackAcceptState.ACCEPT
    }

    private fun testForShaderPack(hash: String): Boolean {
        try {
            val file = File(MinecraftClient.getInstance().runDirectory, "downloads/$hash.zip")
            return file.exists()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun downloadAndApplyShaderPack(url: String, shaderHash: String) {

        if (testForShaderPack(shaderHash)) {
            applyShaderPack(File(MinecraftClient.getInstance().runDirectory, "downloads/$shaderHash.zip"))
            return
        }

        // Download the shader pack
        val shaderPackFile: File = downloadShaderPack(url, shaderHash) ?: run {
            logger.warn("Shader Pack Download failed")
            return
        }


        // Apply the shader pack
        applyShaderPack(shaderPackFile)
    }

    private fun applyShaderPack(shaderPackFile: File) {
        if (!Iris.getIrisConfig().areShadersEnabled() || !Iris.getCurrentPackName().equals(shaderPackFile.getName(), true)) {
            try {
                Iris.getIrisConfig().setShaderPackName("../downloads/${shaderPackFile.getName()}")
                Iris.getIrisConfig().setShadersEnabled(true)
                Iris.getIrisConfig().save()
                Iris.reload()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    private fun downloadShaderPack(url: String, hash: String): File? {
        // Implementiere den Download des Shaderpacks von der URL und speichere es lokal ab
        // Gib die Datei zurück, wenn der Download erfolgreich war, andernfalls null
        try {
            val minecraftClient = MinecraftClient.getInstance()
            val tempUUID = UUID.randomUUID()
            val tmpFile = File(minecraftClient.runDirectory, "downloads/$tempUUID")
            val urlConnection = URI(url).toURL().openConnection() as HttpURLConnection
            urlConnection.connect()
            urlConnection.inputStream.use { inputStream ->
                tmpFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            val renamed = tmpFile.renameTo(File(minecraftClient.runDirectory, "downloads/$hash.zip"))
            return if (renamed) {
                File(minecraftClient.runDirectory, "downloads/$hash.zip")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}