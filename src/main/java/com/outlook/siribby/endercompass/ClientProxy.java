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
    public void registerRender() {
        super.registerRender();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void renderItemInFrame(RenderItemInFrameEvent event) {
        if (event.item.getItem() == EnderCompass.ENDER_COMPASS) {
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            textureManager.bindTexture(TextureMap.locationItemsTexture);
            TextureAtlasSprite sprite = ((TextureMap) textureManager.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(EnderCompass.ENDER_COMPASS.getIconIndex(event.item).getIconName());

            if (sprite instanceof TextureEnderCompass) {
                TextureEnderCompass texture = (TextureEnderCompass) sprite;
                double angle = texture.currentAngle;
                double delta = texture.angleDelta;
                texture.currentAngle = 0.0D;
                texture.angleDelta = 0.0D;
                texture.updateCompass(event.entityItemFrame.worldObj, event.entityItemFrame.posX, event.entityItemFrame.posZ, (double) MathHelper.wrapAngleTo180_float((float) (180 + event.entityItemFrame.hangingDirection * 90)), false, true);
                texture.currentAngle = angle;
                texture.angleDelta = delta;
            }

            EntityItem entityItem = new EntityItem(event.entityItemFrame.worldObj, 0.0D, 0.0D, 0.0D, event.item);
            entityItem.getEntityItem().stackSize = 1;
            entityItem.hoverStart = 0.0F;

            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;

            if (sprite.getFrameCount() > 0) {
                sprite.updateAnimation();
            }

            event.setCanceled(true);
        }
    }
}
