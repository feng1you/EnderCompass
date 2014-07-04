package com.outlook.siribby.endercompass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;

public class ItemEnderCompass extends Item {
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        TextureMap textureMap = (TextureMap) par1IconRegister;
        boolean hasTexture = textureMap.setTextureEntry(iconString, new TextureEnderCompass(iconString));
        itemIcon = hasTexture ? textureMap.getTextureExtry(iconString) : null;
    }
}
