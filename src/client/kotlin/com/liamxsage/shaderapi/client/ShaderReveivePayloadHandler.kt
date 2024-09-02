package com.liamxsage.shaderapi.client

import com.liamxsage.klassicx.extensions.getLogger
import com.liamxsage.shaderapi.ShaderReceivePayload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.irisshaders.iris.Iris
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ConfirmScreen
import net.minecraft.text.Text
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.util.*

class ShaderReveivePayloadHandler : ClientPlayNetworking.PlayPayloadHandler<ShaderReceivePayload> {
    override fun receive(payload: ShaderReceivePayload, context: ClientPlayNetworking.Context) {
        getLogger().info("ShaderReceivePayload received")
        val shaderUrl: String = payload.shaderUrl ?: return
        val shaderHash: String = payload.hash ?: return
        val serverGroup: String = payload.serverGroup ?: "global"

        val client = context.client()
        client.execute {
            client.setScreen(ConfirmScreen({ accept: Boolean ->
                if (accept) {
                    downloadAndApplyShaderPack(shaderUrl, shaderHash)
                    context.client().setScreen(null)
                    return@ConfirmScreen
                }

               context.client().setScreen(null)

            }, Text.of("Shader Pack Available"), Text.of("Do you want to download and apply the shader pack?")))
        }
    }

    private fun testForServerGroupAlwaysAccept(client: MinecraftClient, serverGroup: String): Boolean {
        client.currentServerEntry?.resourcePackPolicy
        return serverGroup == "global"
    }

    private fun testForShaderPack(hash: String): Boolean {
        try {
            val file = File(MinecraftClient.getInstance().runDirectory, "downloads/$hash")
            return file.exists()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun downloadAndApplyShaderPack(url: String, shaderHash: String) {

        if (testForShaderPack(shaderHash)) {
            applyShaderPack(File(MinecraftClient.getInstance().runDirectory, "downloads/$shaderHash"))
            return
        }

        // Download the shader pack
        val shaderPackFile: File = downloadShaderPack(url) ?: run {
            getLogger().warn("Shader Pack Download failed")
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

    private fun downloadShaderPack(url: String): File? {
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

            val hash = tmpFile.hashCode().toString()
            val renamed = tmpFile.renameTo(File(minecraftClient.runDirectory, "downloads/$hash"))
            return if (renamed) {
                File(minecraftClient.runDirectory, "downloads/$hash")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}