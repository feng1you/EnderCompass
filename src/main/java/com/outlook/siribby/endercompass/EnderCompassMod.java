package com.outlook.siribby.endercompass;

import com.outlook.siribby.endercompass.client.EnderCompassClient;
import com.outlook.siribby.endercompass.network.EnderCompassProxy;
import com.outlook.siribby.endercompass.network.MessageGetStrongholdPos;
import com.outlook.siribby.endercompass.network.MessageSetStrongholdPos;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "endercompass", name = "Ender Compass", version = "@VERSION@")
public class EnderCompassMod {
    public static final Item ender_compass = new Item() {
        @Override
        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegister register) {
            itemIcon = EnderCompassClient.ender_compass_icon;
        }
    }.setUnlocalizedName("compassEnd").setCreativeTab(CreativeTabs.tabTools).setTextureName("endercompass:ender_compass");

    @SidedProxy(clientSide = "com.outlook.siribby.endercompass.client.EnderCompassClient", serverSide = "com.outlook.siribby.endercompass.network.EnderCompassProxy")
    public static EnderCompassProxy proxy;
    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerItem(ender_compass, "ender_compass");
        GameRegistry.addRecipe(new ItemStack(ender_compass), " E ", "ECE", " E ", 'E', Items.ender_eye, 'C', Items.compass);
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(ender_compass, 0, 1, 1, 1));

        network = NetworkRegistry.INSTANCE.newSimpleChannel("endercompass");
        network.registerMessage(new MessageGetStrongholdPos(), MessageGetStrongholdPos.class, 0, Side.SERVER);
        network.registerMessage(new MessageSetStrongholdPos(), MessageSetStrongholdPos.class, 1, Side.CLIENT);

        FMLCommonHandler.instance().bus().register(proxy);
        MinecraftForge.EVENT_BUS.register(proxy);
    }
}
