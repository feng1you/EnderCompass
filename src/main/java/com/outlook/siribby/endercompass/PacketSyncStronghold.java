package com.outlook.siribby.endercompass;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.ChunkPosition;

public class PacketSyncStronghold implements IMessage, IMessageHandler<PacketSyncStronghold, IMessage> {
    public ChunkPosition position;

    public PacketSyncStronghold() {
    }

    public PacketSyncStronghold(ChunkPosition position) {
        this.position = position;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(position.chunkPosX);
        buffer.writeInt(position.chunkPosY);
        buffer.writeInt(position.chunkPosZ);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        position = new ChunkPosition(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    @Override
    public IMessage onMessage(PacketSyncStronghold message, MessageContext ctx) {
        EnderCompass.strongholdPos = message.position;
        return null;
    }
}
