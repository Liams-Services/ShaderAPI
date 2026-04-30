package com.liamxsage.shaderapi

import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Constants {
    @JvmStatic
    val REQUEST_SHADER_PACKET_ID: Identifier = Identifier.fromNamespaceAndPath("shaderapi", "request_shader_url")

    @JvmStatic
    val RECEIVE_SHADER_PACKET_ID: Identifier = Identifier.fromNamespaceAndPath("shaderapi", "receive_shader_url")

    @JvmStatic
    val STATUS_RESPONSE_PACKET_ID: Identifier = Identifier.fromNamespaceAndPath("shaderapi", "status_response")

    @JvmStatic
    val logger: Logger = LoggerFactory.getLogger(Shaderapi::class.java)
}
