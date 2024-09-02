package com.liamxsage.shaderapi

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Constants {
    @JvmStatic
    val REQUEST_SHADER_PACKET_ID: Identifier = Identifier.of("shaderapi", "request_shader_url")

    @JvmStatic
    val RECEIVE_SHADER_PACKET_ID: Identifier = Identifier.of("shaderapi", "receive_shader_url")

    @JvmStatic
    val logger: Logger = LoggerFactory.getLogger(Shaderapi::class.java)
}
