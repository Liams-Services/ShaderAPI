package com.liamxsage.shaderapi;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;


public record ShaderRequestPayload(boolean alwaysTrue) implements CustomPayload {
    public static final CustomPayload.Id<ShaderRequestPayload> ID = new CustomPayload.Id<>(Constants.getREQUEST_SHADER_PACKET_ID());
    public static final PacketCodec<RegistryByteBuf, ShaderRequestPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, ShaderRequestPayload::alwaysTrue, ShaderRequestPayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
