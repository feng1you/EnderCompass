package com.outlook.siribby.endercompass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class TextureEnderCompass extends TextureCompass {
    public TextureEnderCompass(String par1Str) {
        super(par1Str);
    }

    @Override
    public void updateCompass(World par1World, double par2, double par4, double par6, boolean par8, boolean par9) {
        ChunkPosition chunkposition = EnderCompass.strongholdPos;
        if (chunkposition != null && !this.framesTextureData.isEmpty()) {
            double d3 = 0.0D;
            if (par1World != null && !par8) {
                ChunkCoordinates chunkcoordinates = new ChunkCoordinates(chunkposition.chunkPosX, chunkposition.chunkPosY, chunkposition.chunkPosZ);
                double d4 = (double) chunkcoordinates.posX - par2;
                double d5 = (double) chunkcoordinates.posZ - par4;
                par6 %= 360.0D;
                d3 = -((par6 - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));

                if (!par1World.provider.isSurfaceWorld()) {
                    d3 = Math.random() * Math.PI * 2.0D;
                }
            }

            if (par9) {
                this.currentAngle = d3;
            } else {
                double d6;

                for (d6 = d3 - this.currentAngle; d6 < -Math.PI; d6 += (Math.PI * 2D)) {
                    ;
                }

                while (d6 >= Math.PI) {
                    d6 -= (Math.PI * 2D);
                }

                if (d6 < -1.0D) {
                    d6 = -1.0D;
                }

                if (d6 > 1.0D) {
                    d6 = 1.0D;
                }

                this.angleDelta += d6 * 0.1D;
                this.angleDelta *= 0.8D;
                this.currentAngle += this.angleDelta;
            }

            int i;

            for (i = (int) ((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double) this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {
                ;
            }

            if (i != this.frameCounter) {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap((int[][]) this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}
