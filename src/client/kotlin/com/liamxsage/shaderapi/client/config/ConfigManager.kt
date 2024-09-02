package com.liamxsage.shaderapi.client.config

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liamxsage.shaderapi.Constants.logger
import net.minecraft.client.MinecraftClient
import java.io.File

object ConfigManager {

    private val configFile = File(MinecraftClient.getInstance().runDirectory, "config/shaderapi.json").also {
        if (!it.exists()) {
            it.createNewFile()
            it.writeText(Gson().toJson(emptyList<ServerGroupShaderState>()))
        }
    }
    private var serverGroups: MutableList<ServerGroupShaderState> = mutableListOf()

    fun readConfig() {
        try {
            serverGroups = Gson().fromJson(configFile.readText(), object : TypeToken<List<ServerGroupShaderState>>() {}.type)
            logger.info("Loaded ${serverGroups.size} server groups")
        } catch (e: Exception) {
            logger.warn("Failed to read config file", e)
        }
    }

    private fun saveConfig() {
        try {
            configFile.writeText(Gson().toJson(serverGroups))
        } catch (e: Exception) {
            logger.warn("Failed to save config file", e)
        }
    }

    fun addServerGroup(serverGroup: String, shaderPackAcceptState: ShaderPackAcceptState) {
        if (serverGroups.any { it.serverGroup == serverGroup }) {
            // Server group already exists, change the state
            serverGroups.find { it.serverGroup == serverGroup }?.shaderPackAcceptState = shaderPackAcceptState
            saveConfig()
            return
        }
        serverGroups.add(ServerGroupShaderState(serverGroup, shaderPackAcceptState))
        saveConfig()
    }

    fun getShaderPackAcceptState(serverGroup: String): ShaderPackAcceptState {
        return serverGroups.find { it.serverGroup == serverGroup }?.shaderPackAcceptState ?: ShaderPackAcceptState.PROMPT
    }

}