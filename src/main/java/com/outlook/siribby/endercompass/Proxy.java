package com.outlook.siribby.endercompass;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class Proxy {
    private static World currentWorld;

    public void registerRender() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof EntityPlayerMP && (currentWorld != event.player.worldObj || EnderCompass.strongholdPos == null)) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            int x = (int) player.posX;
            int y = (int) player.posY;
            int z = (int) player.posZ;
            ChunkPosition position = player.worldObj.findClosestStructure("Stronghold", x, y, z);

            if (position != null) {
                currentWorld = player.worldObj;
                EnderCompass.strongholdPos = position;
                EnderCompass.networkWrapper.sendTo(new MessageStrongholdPos(position), player);
            }
        }
    }
}
