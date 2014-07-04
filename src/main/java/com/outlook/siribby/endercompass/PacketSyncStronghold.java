package com.outlook.siribby.endercompass;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.ChunkPosition;

public class PacketSyncStronghold implements IMessage {
    public ChunkPosition strongholdPosition;

    public PacketSyncStronghold() {}

    public PacketSyncStronghold(ChunkPosition par1ChunkPosition) {
        strongholdPosition = par1ChunkPosition;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(strongholdPosition.chunkPosX);
        buffer.writeInt(strongholdPosition.chunkPosY);
        buffer.writeInt(strongholdPosition.chunkPosZ);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        strongholdPosition = new ChunkPosition(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public static class Handler implements IMessageHandler<PacketSyncStronghold, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncStronghold message, MessageContext ctx) {
            EnderCompass.strongholdPos = message.strongholdPosition;
            return null;
        }
    }
}
