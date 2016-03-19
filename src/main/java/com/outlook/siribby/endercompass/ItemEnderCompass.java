package com.outlook.siribby.endercompass;

import com.outlook.siribby.endercompass.client.EnderCompassClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnderCompass extends Item {
    public ItemEnderCompass() {
        addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            double field_185095_a;
            @SideOnly(Side.CLIENT)
            double field_185096_b;
            @SideOnly(Side.CLIENT)
            long field_185097_c;

            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
                if (entityIn == null && !stack.isOnItemFrame()) {
                    return 0.0F;
                } else {
                    boolean flag = entityIn != null;
                    Entity entity = flag ? entityIn : stack.getItemFrame();

                    if (worldIn == null) {
                        worldIn = entity.worldObj;
                    }

                    double d0;

                    if (EnderCompassClient.strongholdPos != null) {
                        double d1 = flag ? (double) entity.rotationYaw : func_185094_a((EntityItemFrame) entity);
                        d1 = d1 % 360.0D;
                        double d2 = func_185092_a(worldIn, entity);
                        d0 = Math.PI - ((d1 - 90.0D) * 0.01745329238474369D - d2);
                    } else {
                        d0 = Math.random() * (Math.PI * 2D);
                    }

                    if (flag) {
                        d0 = func_185093_a(worldIn, d0);
                    }

                    float f = (float) (d0 / (Math.PI * 2D));
                    return MathHelper.func_188207_b(f, 1.0F);
                }
            }

            @SideOnly(Side.CLIENT)
            private double func_185093_a(World p_185093_1_, double p_185093_2_) {
                if (p_185093_1_.getTotalWorldTime() != field_185097_c) {
                    field_185097_c = p_185093_1_.getTotalWorldTime();
                    double d0 = p_185093_2_ - field_185095_a;
                    d0 = d0 % (Math.PI * 2D);
                    d0 = MathHelper.clamp_double(d0, -1.0D, 1.0D);
                    field_185096_b += d0 * 0.1D;
                    field_185096_b *= 0.8D;
                    field_185095_a += field_185096_b;
                }

                return field_185095_a;
            }

            @SideOnly(Side.CLIENT)
            private double func_185094_a(EntityItemFrame p_185094_1_) {
                return (double) MathHelper.clampAngle(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
            }

            @SideOnly(Side.CLIENT)
            private double func_185092_a(World p_185092_1_, Entity p_185092_2_) {
                BlockPos blockpos = EnderCompassClient.strongholdPos;
                return Math.atan2((double) blockpos.getZ() - p_185092_2_.posZ, (double) blockpos.getX() - p_185092_2_.posX);
            }
        });
    }
}
