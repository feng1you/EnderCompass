package com.outlook.siribby.endercompass.client;

import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class TextureEnderCompass extends TextureCompass {
    public TextureEnderCompass(String iconName) {
        super(iconName);
    }

    @Override
    public void updateCompass(World world, double playerX, double playerZ, double cameraDirection, boolean par8, boolean par9) {
        if (!framesTextureData.isEmpty()) {
            double d3 = 0.0D;

            if (world != null && !par8) {
                ChunkPosition position = EnderCompassClient.strongholdPos;

                if (position != null) {
                    double d4 = (double) position.chunkPosX - playerX;
                    double d5 = (double) position.chunkPosZ - playerZ;
                    cameraDirection %= 360.0D;
                    d3 = -((cameraDirection - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));
                } else {
                    d3 = Math.random() * Math.PI * 2.0D;
                }
            }

            if (par9) {
                currentAngle = d3;
            } else {
                double d6 = d3 - currentAngle;

                while (d6 < -Math.PI) {
                    d6 += (Math.PI * 2D);
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

                angleDelta += d6 * 0.1D;
                angleDelta *= 0.8D;
                currentAngle += angleDelta;
            }

            int i = (int) ((currentAngle / (Math.PI * 2D) + 1.0D) * (double) framesTextureData.size()) % framesTextureData.size();

            while (i < 0) {
                i = (i + framesTextureData.size()) % framesTextureData.size();
            }

            if (i != frameCounter) {
                frameCounter = i;
                TextureUtil.uploadTextureMipmap((int[][]) framesTextureData.get(frameCounter), width, height, originX, originY, false, false);
            }
        }
    }
}
