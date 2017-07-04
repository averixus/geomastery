/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Collections;
import java.util.Map;
import com.google.common.collect.Maps;
import jayavery.geomastery.blocks.BlockCropBlockfruit;
import jayavery.geomastery.blocks.BlockHarvestableLeaves;
import jayavery.geomastery.blocks.BlockWall;
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
import jayavery.geomastery.render.RenderFallingTreeBlock;
import jayavery.geomastery.render.block.RenderBeamThick;
import jayavery.geomastery.render.block.RenderBeamThin;
import jayavery.geomastery.render.block.RenderWallAbstract;
import jayavery.geomastery.render.block.RenderWallComplex;
import jayavery.geomastery.render.block.RenderWallSingle;
import jayavery.geomastery.render.block.RenderWallStraight;
import jayavery.geomastery.render.projectile.RenderArrowFactory;
import jayavery.geomastery.render.projectile.RenderSpearFactory;
import jayavery.geomastery.render.tileentity.RenderBox;
import jayavery.geomastery.render.tileentity.RenderChest;
import jayavery.geomastery.tileentities.TEStorage;
import jayavery.geomastery.utilities.IProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/** Client-side proxy. */
public class ClientProxy implements IProxy {
    
    /** Map of wall blocks to renderers. */
    public static final Map<BlockWall, RenderWallAbstract> WALL_RENDERS = Maps.newHashMap();
    
    @Override
    public void registerModels() {
        
        Geomastery.LOG.info("Registering item models");
        for (Item item : GeoItems.ITEMS) {
            
            NonNullList<ItemStack> stacks = NonNullList.create();
            item.getSubItems(item, null, stacks);
            
            for (ItemStack stack : stacks) {
                
                ModelLoader.setCustomModelResourceLocation(item, stack
                        .getMetadata(), new ModelResourceLocation(item
                        .getRegistryName(), "inventory"));
            }
        }
        
        Geomastery.LOG.info("Setting leaves transparency");
        boolean fancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
        for (BlockHarvestableLeaves block : GeoBlocks.LEAVES) {
            
            block.setGraphicsLevel(fancy);
        }
        
        Geomastery.LOG.info("Registering blockfruit crop custom state mappers");
        for (BlockCropBlockfruit block : GeoBlocks.CROP_BLOCKFRUIT) {
            
            ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
                
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    
                    Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());

                    if (state.getValue(BlockStem.FACING) != EnumFacing.UP) {
                        
                        map.remove(BlockStem.AGE);
                    }

                    return new ModelResourceLocation(block.getRegistryName(), this.getPropertyString(map));
                }
            });
        }
        
        Geomastery.LOG.info("Registering delayed baking custom state mappers");
        for (Block block : GeoBlocks.DELAYED_BAKE) {
            
            ModelResourceLocation loc = new ModelResourceLocation(block.getRegistryName(), "delayedbake");

            ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
                
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    
                    return loc;
                }
            });
        }
        
        Geomastery.LOG.info("Registering beam model loaders");
        ModelLoaderRegistry.registerLoader(new RenderBeamThin());
        ModelLoaderRegistry.registerLoader(new RenderBeamThick());
        
        Geomastery.LOG.info("Registering straight wall model loaders");
        for (BlockWall block : GeoBlocks.RENDER_STRAIGHT) {
            
            RenderWallAbstract render = new RenderWallStraight(block.getRegistryName());
            ModelLoaderRegistry.registerLoader(render);
            WALL_RENDERS.put(block, render);
        }
        
        Geomastery.LOG.info("Registering complex wall model loaders");
        for (BlockWall block : GeoBlocks.RENDER_COMPLEX) {
            
            RenderWallAbstract render = new RenderWallComplex(block.getRegistryName(), block.isDouble());
            ModelLoaderRegistry.registerLoader(render);
            WALL_RENDERS.put(block, render);
        }
        
        Geomastery.LOG.info("Registering single wall model loaders");
        for (BlockWall block : GeoBlocks.RENDER_SINGLE) {
            
            RenderWallAbstract render = new RenderWallSingle(block.getRegistryName(), block.getSideAngle());
            ModelLoaderRegistry.registerLoader(render);
            WALL_RENDERS.put(block, render);
        }
    }
    
    @Override
    public void preInit() {
        
        Geomastery.LOG.info("Registering client event handler");
        MinecraftForge.EVENT_BUS.register(new GuiEvents());
                
        Geomastery.LOG.info("Registering entity renderers");
        entity(EntitySpearWood.class, RenderSpearFactory.SPEAR_WOOD);
        entity(EntitySpearFlint.class, RenderSpearFactory.SPEAR_FLINT);
        entity(EntitySpearCopper.class, RenderSpearFactory.SPEAR_COPPER);
        entity(EntitySpearBronze.class, RenderSpearFactory.SPEAR_BRONZE);
        entity(EntitySpearSteel.class, RenderSpearFactory.SPEAR_STEEL);
        entity(EntityArrowWood.class, RenderArrowFactory.ARROW_WOOD);
        entity(EntityArrowFlint.class, RenderArrowFactory.ARROW_FLINT);
        entity(EntityArrowCopper.class, RenderArrowFactory.ARROW_COPPER);
        entity(EntityArrowBronze.class, RenderArrowFactory.ARROW_BRONZE);
        entity(EntityArrowSteel.class, RenderArrowFactory.ARROW_STEEL);
        entity(FallingTreeBlock.Leaves.class, RenderFallingTreeBlock::new);
        entity(FallingTreeBlock.Trunk.class, RenderFallingTreeBlock::new);
        
        Geomastery.LOG.info("Registering tileentity renderers");
        ClientRegistry.bindTileEntitySpecialRenderer(TEStorage.Box.class, new RenderBox());
        ClientRegistry.bindTileEntitySpecialRenderer(TEStorage.Chest.class, new RenderChest());
        
        Geomastery.LOG.info("Registering tar state mapper and model loader");
        BlockFluidBase tarBlock = GeoBlocks.tar;
        ResourceLocation tarRegistry = tarBlock.getRegistryName();
        ModelResourceLocation tarLoc = new ModelResourceLocation(
                tarRegistry.getResourceDomain() + ":fluid#" +
                tarRegistry.getResourcePath());
        
        Item tarItem = Item.getItemFromBlock(tarBlock);
        ModelBakery.registerItemVariants(tarItem);
        ModelLoader.setCustomMeshDefinition(tarItem, stack -> tarLoc);
        ModelLoader.setCustomStateMapper(tarBlock, new StateMapperBase() {
            
            @Override
            protected ModelResourceLocation getModelResourceLocation(
                    IBlockState state) {
                
                return tarLoc;
            }
        });
    }

    @Override
    public void init() {}

    @Override
    public void postInit() {}
    
    @Override
    public EntityPlayer getClientPlayer() {
        
        return Minecraft.getMinecraft().player;
    }
    
    @Override
    public World getClientWorld() {
        
        return Minecraft.getMinecraft().world;
    }
    
    @Override
    public void addClientRunnable(Runnable task) {
        
        Minecraft.getMinecraft().addScheduledTask(task);
    }
    
    /** Helper for registering entity renders. */
    private static <E extends Entity> void entity(Class<E> clas,
            IRenderFactory<? super E> renderer) {
        
        RenderingRegistry.registerEntityRenderingHandler(clas, renderer);
    }
}
