package com.outlook.siribby.endercompass.network;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessageGetStrongholdPos implements IMessage, IMessageHandler<MessageGetStrongholdPos, IMessage> {
    public MessageGetStrongholdPos() {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(MessageGetStrongholdPos message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        BlockPos pos = player.worldObj.getStrongholdPos("Stronghold", new BlockPos(player));
        return pos != null ? new MessageSetStrongholdPos(pos) : null;
    }
}
