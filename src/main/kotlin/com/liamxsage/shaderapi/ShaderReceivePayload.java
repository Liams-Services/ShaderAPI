package com.liamxsage.shaderapi;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;


public record ShaderReceivePayload(String shaderUrl, String hash, String serverGroup) implements CustomPacketPayload {

    public static final Type<ShaderReceivePayload> ID = new Type<>(Constants.getRECEIVE_SHADER_PACKET_ID());
    public static final StreamCodec<RegistryFriendlyByteBuf, ShaderReceivePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ShaderReceivePayload::shaderUrl,
            ByteBufCodecs.STRING_UTF8, ShaderReceivePayload::hash,
            ByteBufCodecs.STRING_UTF8, ShaderReceivePayload::serverGroup,
            ShaderReceivePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
