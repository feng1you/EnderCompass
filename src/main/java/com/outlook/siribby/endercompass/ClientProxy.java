package com.outlook.siribby.endercompass;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void renderItemInFrame(RenderItemInFrameEvent event) {
        if (event.item.getItem() == EnderCompass.ender_compass) {
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            texturemanager.bindTexture(TextureMap.locationItemsTexture);
            TextureAtlasSprite textureatlassprite = ((TextureMap) texturemanager.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(EnderCompass.ender_compass.getIconIndex(event.item).getIconName());

            if (textureatlassprite instanceof TextureEnderCompass) {
                TextureEnderCompass texturecompass = (TextureEnderCompass) textureatlassprite;
                double d0 = texturecompass.currentAngle;
                double d1 = texturecompass.angleDelta;
                texturecompass.currentAngle = 0.0D;
                texturecompass.angleDelta = 0.0D;
                texturecompass.updateCompass(event.entityItemFrame.worldObj, event.entityItemFrame.posX, event.entityItemFrame.posZ, (double) MathHelper.wrapAngleTo180_float((float) (180 + event.entityItemFrame.hangingDirection * 90)), false, true);
                texturecompass.currentAngle = d0;
                texturecompass.angleDelta = d1;
            }

            EntityItem entityitem = new EntityItem(event.entityItemFrame.worldObj, 0.0D, 0.0D, 0.0D, event.item);
            entityitem.getEntityItem().stackSize = 1;
            entityitem.hoverStart = 0.0F;

            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;

            if (textureatlassprite.getFrameCount() > 0) {
                textureatlassprite.updateAnimation();
            }

            event.setCanceled(true);
        }
    }
}
