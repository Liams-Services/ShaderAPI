package com.liamxsage.shaderapi;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;


public record ShaderStatusResponsePayload(String statusResponse) implements CustomPayload {

    public static final Id<ShaderStatusResponsePayload> ID = new Id<>(Constants.getSTATUS_RESPONSE_PACKET_ID());
    public static final PacketCodec<RegistryByteBuf, ShaderStatusResponsePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, ShaderStatusResponsePayload::statusResponse,
            ShaderStatusResponsePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
