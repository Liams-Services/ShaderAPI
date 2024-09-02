package com.liamxsage.shaderapi;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;


public record ShaderReceivePayload(String shaderUrl, String hash, String serverGroup) implements CustomPayload {

    public static final Id<ShaderReceivePayload> ID = new Id<>(Constants.getRECEIVE_SHADER_PACKET_ID());
    public static final PacketCodec<RegistryByteBuf, ShaderReceivePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, ShaderReceivePayload::shaderUrl,
            PacketCodecs.STRING, ShaderReceivePayload::hash,
            PacketCodecs.STRING, ShaderReceivePayload::serverGroup,
            ShaderReceivePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
