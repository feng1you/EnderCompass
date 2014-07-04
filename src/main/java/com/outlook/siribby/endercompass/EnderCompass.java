package com.outlook.siribby.endercompass;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;

@Mod(modid = EnderCompass.MOD_ID, name = EnderCompass.NAME, version = EnderCompass.VERSION, dependencies = "required-after:Forge")
public class EnderCompass {
    public static final String MOD_ID = "endercompass";
    public static final String NAME = "Ender Compass";
    public static final String VERSION = "1.0";
    @Mod.Instance(MOD_ID)
    public static EnderCompass instance;
    @SidedProxy(clientSide = "com.outlook.siribby.endercompass.ClientProxy", serverSide = "com.outlook.siribby.endercompass.CommonProxy")
    public static CommonProxy proxy;
    public static ChunkPosition strongholdPos;
    public static SimpleNetworkWrapper channel;
    public static Item ender_compass;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ender_compass = new ItemEnderCompass().setUnlocalizedName("compassEnd").setCreativeTab(CreativeTabs.tabTools).setTextureName(MOD_ID + ":ender_compass");
        GameRegistry.registerItem(ender_compass, "ender_compass");
        channel = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        channel.registerMessage(PacketSyncStronghold.Handler.class, PacketSyncStronghold.class, 0, Side.CLIENT);
        FMLCommonHandler.instance().bus().register(this);
        proxy.registerRenders();
        GameRegistry.addRecipe(new ItemStack(ender_compass), " P ", "PEP", " P ", 'P', Items.ender_pearl, 'E', Items.ender_eye);
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.worldObj.isRemote) {
            strongholdPos = event.player.worldObj.findClosestStructure("Stronghold", (int) event.player.posX, (int) event.player.posY, (int) event.player.posZ);
            if (event.side == Side.SERVER && event.player instanceof EntityPlayerMP){
                EntityPlayerMP player = (EntityPlayerMP) event.player;
                channel.sendTo(new PacketSyncStronghold(strongholdPos), player);
            }
        }
    }
}
