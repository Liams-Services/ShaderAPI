package com.liamxsage.shaderapi;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;


public record ShaderStatusResponsePayload(String statusResponse) implements CustomPacketPayload {

    public static final Type<ShaderStatusResponsePayload> ID = new Type<>(Constants.getSTATUS_RESPONSE_PACKET_ID());
    public static final StreamCodec<RegistryFriendlyByteBuf, ShaderStatusResponsePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ShaderStatusResponsePayload::statusResponse,
            ShaderStatusResponsePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
