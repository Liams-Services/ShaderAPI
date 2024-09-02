package com.liamxsage.shaderapi.client.config

data class ServerGroupShaderState(
    val serverGroup: String,
    var shaderPackAcceptState: ShaderPackAcceptState = ShaderPackAcceptState.PROMPT
)
