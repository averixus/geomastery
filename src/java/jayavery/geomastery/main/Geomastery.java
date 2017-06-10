package jayavery.geomastery.main;

import java.util.Map.Entry;
import jayavery.geomastery.entities.FallingTreeBlock;
import jayavery.geomastery.entities.projectile.EntityArrowBronze;
import jayavery.geomastery.entities.projectile.EntityArrowCopper;
import jayavery.geomastery.entities.projectile.EntityArrowFlint;
import jayavery.geomastery.entities.projectile.EntityArrowSteel;
import jayavery.geomastery.entities.projectile.EntityArrowWood;
import jayavery.geomastery.entities.projectile.EntitySpearBronze;
import jayavery.geomastery.entities.projectile.EntitySpearCopper;
import jayavery.geomastery.entities.projectile.EntitySpearFlint;
import jayavery.geomastery.entities.projectile.EntitySpearSteel;
import jayavery.geomastery.entities.projectile.EntitySpearWood;
import jayavery.geomastery.packets.CPacketBackpack;
import jayavery.geomastery.packets.CPacketBox;
import jayavery.geomastery.packets.CPacketCompost;
import jayavery.geomastery.packets.CPacketContainer;
import jayavery.geomastery.packets.CPacketCrafting;
import jayavery.geomastery.packets.CPacketDrying;
import jayavery.geomastery.packets.CPacketFloor;
import jayavery.geomastery.packets.CPacketFood;
import jayavery.geomastery.packets.CPacketFurnace;
import jayavery.geomastery.packets.CPacketTemp;
import jayavery.geomastery.packets.CPacketYoke;
import jayavery.geomastery.packets.SPacketContainer;
import jayavery.geomastery.tileentities.TEBasket;
import jayavery.geomastery.tileentities.TEBeam;
import jayavery.geomastery.tileentities.TEBed;
import jayavery.geomastery.tileentities.TEBox;
import jayavery.geomastery.tileentities.TECarcass;
import jayavery.geomastery.tileentities.TECompost;
import jayavery.geomastery.tileentities.TECraftingArmourer;
import jayavery.geomastery.tileentities.TECraftingCandlemaker;
import jayavery.geomastery.tileentities.TECraftingForge;
import jayavery.geomastery.tileentities.TECraftingKnapping;
import jayavery.geomastery.tileentities.TECraftingMason;
import jayavery.geomastery.tileentities.TECraftingSawpit;
import jayavery.geomastery.tileentities.TECraftingTextiles;
import jayavery.geomastery.tileentities.TECraftingWoodworking;
import jayavery.geomastery.tileentities.TECrop;
import jayavery.geomastery.tileentities.TEDrying;
import jayavery.geomastery.tileentities.TEFurnaceCampfire;
import jayavery.geomastery.tileentities.TEFurnaceClay;
import jayavery.geomastery.tileentities.TEFurnacePotfire;
import jayavery.geomastery.tileentities.TEFurnaceStone;
import jayavery.geomastery.utilities.IProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Geomastery.MODID, version = Geomastery.VERSION, name = Geomastery.NAME)
@EventBusSubscriber
public class Geomastery {

    public static final String MODID = "geomastery";
    public static final String VERSION = "1.3.1";
    public static final String NAME = "Geomastery";

    @SidedProxy(clientSide = "jayavery.geomastery.main.ClientProxy", serverSide = "jayavery.geomastery.main.ServerProxy")
    public static IProxy proxy;

    @Instance
    public static Geomastery instance = new Geomastery();
    
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Geomastery.MODID);

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
                
        for (Block block : GeoBlocks.BLOCKS) {
            
            event.getRegistry().register(block);
        }
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        for (Item item : GeoItems.ITEMS) {
            
            event.getRegistry().register(item);
        }
        
        for (Item item : GeoBlocks.ITEM_MAP.values()) {

            event.getRegistry().register(item);
        }
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        
        proxy.registerModels();
    }
    
    /** Registers everything other than blocks and items. */
    @EventHandler
    public static void preInit(FMLPreInitializationEvent e) {

        GeoBlocks.preInit();
        GeoItems.preInit();
        GeoCaps.preInit();
                
        // Event handlers
        WorldGenerator worldgen = new WorldGenerator();
        GameRegistry.registerWorldGenerator(worldgen, 0);
        MinecraftForge.TERRAIN_GEN_BUS.register(worldgen);
        MinecraftForge.ORE_GEN_BUS.register(worldgen);
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        MinecraftForge.EVENT_BUS.register(new EntityEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        
        // Entities
        entity("spear_wood", EntitySpearWood.class);
        entity("spear_flint", EntitySpearFlint.class);
        entity("spear_copper", EntitySpearCopper.class);
        entity("spear_bronze", EntitySpearBronze.class);
        entity("spear_steel", EntitySpearSteel.class);
        entity("arrow_wood", EntityArrowWood.class);
        entity("arrow_flint", EntityArrowFlint.class);
        entity("arrow_copper", EntityArrowCopper.class);
        entity("arrow_bronze", EntityArrowBronze.class);
        entity("arrow_steel", EntityArrowSteel.class);
        entity("falling_trunk", FallingTreeBlock.Trunk.class);
        entity("falling_leaves", FallingTreeBlock.Leaves.class);
        
        // Packets
        sPacket(SPacketContainer.Handler.class, SPacketContainer.class);
        cPacket(CPacketContainer.Handler.class, CPacketContainer.class);
        cPacket(CPacketTemp.Handler.class, CPacketTemp.class);
        cPacket(CPacketFood.Handler.class, CPacketFood.class);
        cPacket(CPacketFloor.Handler.class, CPacketFloor.class);
        cPacket(CPacketDrying.Handler.class, CPacketDrying.class);
        cPacket(CPacketFurnace.Handler.class, CPacketFurnace.class);
        cPacket(CPacketCrafting.Handler.class, CPacketCrafting.class);
        cPacket(CPacketBackpack.Handler.class, CPacketBackpack.class);
        cPacket(CPacketYoke.Handler.class, CPacketYoke.class);
        cPacket(CPacketBox.Handler.class, CPacketBox.class);
        cPacket(CPacketCompost.Handler.class, CPacketCompost.class);
        
        // Tileentities
        tileentity(TEFurnaceCampfire.class, "furnace_campfire");
        tileentity(TEFurnacePotfire.class, "furnace_potfire");
        tileentity(TEFurnaceClay.class, "furnace_clay");
        tileentity(TEFurnaceStone.class, "furnace_stone");
        tileentity(TECraftingMason.class, "crafting_mason");
        tileentity(TECraftingForge.class, "crafting_forge");
        tileentity(TECraftingWoodworking.class, "crafting_woodworking");
        tileentity(TECraftingSawpit.class, "crafting_sawpit");
        tileentity(TECraftingArmourer.class, "crafting_armourer");
        tileentity(TECraftingCandlemaker.class, "crafting_candlemaker");
        tileentity(TECraftingKnapping.class, "crafting_knapping");
        tileentity(TECraftingTextiles.class, "crafting_textiles");
        tileentity(TEDrying.class, "drying");
        tileentity(TEBox.class, "box");
        tileentity(TEBasket.class, "basket");
        tileentity(TEBed.class, "bed");
        tileentity(TEBeam.class, "beam");
        tileentity(TECarcass.class, "carcass");
        tileentity(TECompost.class, "compost");
        tileentity(TECrop.class, "crop");
        
        proxy.preInit();
    }

    @EventHandler
    public static void init(FMLInitializationEvent e) {
        
        GeoBiomes.init();
        GeoRecipes.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(Geomastery.instance, new GuiHandler());
        
        proxy.init();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent e) {

        proxy.postInit();
    }
    
    private static int entityID = 0;
    
    /** Helper for registering entities. */
    private static void entity(String name, Class<? extends Entity> clas) {
        
        EntityRegistry.registerModEntity(new ResourceLocation(Geomastery.MODID,
                name), clas, name, entityID++,
                Geomastery.instance, 80, 3, true);
    }
    
    private static int packetID = 0;
    
    /** Helper for registering server -> client packets. */
    private static <REQ extends IMessage, REPLY extends IMessage> void
            cPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler,
            Class<REQ> packet) {
        
        NETWORK.registerMessage(handler, packet, packetID++, Side.CLIENT);
    }
    
    /** Helper for registering client -> server packets. */
    private static <REQ extends IMessage, REPLY extends IMessage> void
            sPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler,
            Class<REQ> packet) {
        
        NETWORK.registerMessage(handler, packet, packetID++, Side.SERVER);
    }
    
    /** Helper for registering tileentities. */
    private static void tileentity(Class<? extends TileEntity> clas,
            String id) {
        
        GameRegistry.registerTileEntity(clas, id);
    }
}
