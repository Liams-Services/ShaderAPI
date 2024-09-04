package com.liamxsage.shaderapi.client.functions

import com.liamxsage.shaderapi.ShaderStatusResponsePayload
import com.liamxsage.shaderapi.client.ShaderStatusResponse
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

fun sendShaderStatusResponse(statusResponse: ShaderStatusResponse) {
    ClientPlayNetworking.send(ShaderStatusResponsePayload(statusResponse.name))
}

fun sendShaderStatusResponse(statusResponse: String) {
    ClientPlayNetworking.send(ShaderStatusResponsePayload(statusResponse))
}