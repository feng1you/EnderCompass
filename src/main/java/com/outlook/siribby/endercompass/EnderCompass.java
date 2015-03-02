package com.outlook.siribby.endercompass;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;

@Mod(modid = EnderCompass.MOD_ID, version = EnderCompass.VERSION, useMetadata = true)
public class EnderCompass {
    public static final String MOD_ID = "endercompass";
    public static final String VERSION = "@VERSION@";
    public static final Item ENDER_COMPASS = new ItemEnderCompass();

    @SidedProxy(clientSide = "com.outlook.siribby.endercompass.ClientProxy", serverSide = "com.outlook.siribby.endercompass.CommonProxy")
    public static Proxy proxy;
    public static SimpleNetworkWrapper networkWrapper;
    public static ChunkPosition strongholdPos;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerItem(ENDER_COMPASS, "ender_compass");
        GameRegistry.addRecipe(new ItemStack(ENDER_COMPASS), " P ", "PEP", " P ", 'P', Items.ender_pearl, 'E', Items.ender_eye);

        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        networkWrapper.registerMessage(MessageStrongholdPos.class, MessageStrongholdPos.class, 0, Side.CLIENT);

        proxy.registerRender();
    }
}
