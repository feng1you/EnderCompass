package com.outlook.siribby.endercompass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemEnderCompass extends Item {
    public ItemEnderCompass() {
        setUnlocalizedName("compassEnd");
        setCreativeTab(CreativeTabs.tabTools);
        setTextureName(EnderCompass.MOD_ID + ":ender_compass");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        if (iconRegister instanceof TextureMap) {
            TextureEnderCompass texture = new TextureEnderCompass(iconString);
            itemIcon = ((TextureMap) iconRegister).setTextureEntry(iconString, texture) ? texture : null;
        }
    }
}
