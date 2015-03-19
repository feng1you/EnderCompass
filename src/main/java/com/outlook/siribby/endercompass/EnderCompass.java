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
import net.minecraftforge.common.config.Configuration;

@Mod(modid = EnderCompass.MOD_ID, version = EnderCompass.VERSION, useMetadata = true)
public class EnderCompass {
    public static final String MOD_ID = "endercompass";
    public static final String VERSION = "@VERSION@";
    public static final Item ENDER_COMPASS = new ItemEnderCompass();

    @SidedProxy(clientSide = "com.outlook.siribby.endercompass.ProxyClient", serverSide = "com.outlook.siribby.endercompass.Proxy")
    public static Proxy proxy;
    public static SimpleNetworkWrapper networkWrapper;
    public static ChunkPosition strongholdPos;
    public static boolean oldRecipe = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        oldRecipe = config.get("general", "oldRecipe", oldRecipe).getBoolean();
        config.save();

        GameRegistry.registerItem(ENDER_COMPASS, "ender_compass");

        if (oldRecipe) {
            GameRegistry.addRecipe(new ItemStack(ENDER_COMPASS), " P ", "PEP", " P ", 'P', Items.ender_pearl, 'E', Items.ender_eye);
        } else {
            GameRegistry.addRecipe(new ItemStack(ENDER_COMPASS), " E ", "ECE", " E ", 'E', Items.ender_eye, 'C', Items.compass);
        }

        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        networkWrapper.registerMessage(MessageStrongholdPos.class, MessageStrongholdPos.class, 0, Side.CLIENT);

        proxy.registerRender();
    }
}
