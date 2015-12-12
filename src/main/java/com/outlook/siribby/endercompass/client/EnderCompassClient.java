package com.outlook.siribby.endercompass.client;

import com.outlook.siribby.endercompass.EnderCompassMod;
import com.outlook.siribby.endercompass.network.EnderCompassProxy;
import com.outlook.siribby.endercompass.network.MessageGetStrongholdPos;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.client.event.TextureStitchEvent;

public class EnderCompassClient extends EnderCompassProxy {
    public static final TextureEnderCompass ender_compass_icon = new TextureEnderCompass("endercompass:ender_compass");

    public static ChunkPosition strongholdPos;
    private static World strongholdWorld;

    private static Minecraft minecraft = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void onPreTextureStitch(TextureStitchEvent.Pre event) {
        TextureMap textureMap = event.map;

        if (textureMap.getTextureType() == 1) {
            textureMap.setTextureEntry(ender_compass_icon.getIconName(), ender_compass_icon);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.theWorld != strongholdWorld && minecraft.thePlayer != null) {
            strongholdPos = null;
            strongholdWorld = minecraft.theWorld;
            EnderCompassMod.network.sendToServer(new MessageGetStrongholdPos());
        }
    }

    @SubscribeEvent
    public void onRender(RenderItemInFrameEvent event) {
        ItemStack stack = event.item;
        EntityItemFrame itemFrame = event.entityItemFrame;

        if (event.item.getItem() == EnderCompassMod.ender_compass) {
            TextureManager textureManager = minecraft.getTextureManager();
            textureManager.bindTexture(TextureMap.locationItemsTexture);

            TextureMap textureMap = (TextureMap) textureManager.getTexture(TextureMap.locationItemsTexture);
            TextureAtlasSprite texture = textureMap.getAtlasSprite(EnderCompassMod.ender_compass.getIconIndex(stack).getIconName());

            if (texture instanceof TextureCompass) {
                TextureCompass compassTexture = (TextureCompass) texture;
                double angle = compassTexture.currentAngle;
                double delta = compassTexture.angleDelta;
                compassTexture.currentAngle = 0.0D;
                compassTexture.angleDelta = 0.0D;
                compassTexture.updateCompass(itemFrame.worldObj, itemFrame.posX, itemFrame.posZ, (double) MathHelper.wrapAngleTo180_float((float) (180 + itemFrame.hangingDirection * 90)), false, true);
                compassTexture.currentAngle = angle;
                compassTexture.angleDelta = delta;
            }

            EntityItem itemEntity = new EntityItem(itemFrame.worldObj, 0.0D, 0.0D, 0.0D, stack);
            itemEntity.getEntityItem().stackSize = 1;
            itemEntity.hoverStart = 0.0F;

            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;

            if (texture.getFrameCount() > 0) {
                texture.updateAnimation();
            }

            event.setCanceled(true);
        }
    }
}
