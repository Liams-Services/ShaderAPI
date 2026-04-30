package com.liamxsage.shaderapi;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;


public record ShaderRequestPayload(boolean alwaysTrue) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ShaderRequestPayload> ID = new CustomPacketPayload.Type<>(Constants.getREQUEST_SHADER_PACKET_ID());
    public static final StreamCodec<RegistryFriendlyByteBuf, ShaderRequestPayload> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, ShaderRequestPayload::alwaysTrue, ShaderRequestPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
