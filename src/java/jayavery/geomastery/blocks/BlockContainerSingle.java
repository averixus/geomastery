/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.GuiHandler.GuiList;
import jayavery.geomastery.tileentities.TECraftingKnapping;
import jayavery.geomastery.tileentities.TEDrying;
import jayavery.geomastery.tileentities.TEFurnaceSingle;
import jayavery.geomastery.tileentities.TEStorage;
import jayavery.geomastery.utilities.BlockMaterial;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockContainerSingle
        extends BlockContainerAbstract<ItemPlacing.Building> {
    
    public BlockContainerSingle(String name, Material material,
            float hardness, int ordinal) {
        
        super(name, material, hardness, ordinal);
    }
    
    @Override
    protected ItemPlacing.Building createItem(int stackSize) {

        return new ItemPlacing.Building(this, stackSize);
    }
    
    /** Knapping block. */
    public static class Knapping extends BlockContainerSingle {

        public Knapping() {
            
            super("crafting_knapping", BlockMaterial.STONE_FURNITURE,
                    5F, GuiList.KNAPPING.ordinal());
        }
        
        @Override
        public TileEntity createTileEntity(World world, IBlockState state) {
            
            return new TECraftingKnapping();
        }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {

            return FOUR;
        } 
    }
    
    /** Drying rack block. */
    public static class Drying extends BlockContainerSingle {
        
        public Drying() {
            
            super("drying", BlockMaterial.WOOD_FURNITURE,
                    5F, GuiList.DRYING.ordinal());
        }

        @Override
        public TileEntity createTileEntity(World worldIn, IBlockState state) {

            return new TEDrying();
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {

            return EIGHT;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return SIX;
        }
    }
    
    /** Basket block. */
    public static class Basket extends BlockContainerSingle {
        
        public Basket() {
            
            super("basket", BlockMaterial.WOOD_FURNITURE,
                    2F, GuiList.BASKET.ordinal());
        }
        
        @Override
        public TileEntity createTileEntity(World world, IBlockState state) {
            
            return new TEStorage.Basket();
        }
         
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess source, BlockPos pos) {
            
            return CENTRE_EIGHT;
        }
    }
    
    /** Box block. */
    public static class Box extends BlockContainerSingle {
        
        private static final AxisAlignedBB BOX =
                new AxisAlignedBB(0.25,0,0.25,0.75,0.56,0.75);
        
        public Box() {
        
            super("box", BlockMaterial.WOOD_FURNITURE,
                    5F, GuiList.BOX.ordinal());
        }

        @Override
        public TileEntity createTileEntity(World world, IBlockState state) {

            return new TEStorage.Box();
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess source, BlockPos pos) {
            
            return BOX;
        }
        
        @Override
        @SideOnly(Side.CLIENT)
        public boolean hasCustomBreakingProgress(IBlockState state) {
            
            return true;
        }
        
        @Override
        public EnumBlockRenderType getRenderType(IBlockState state) {
            
            return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
        }
    }
    
    /** Campfire block. */
    public static class Campfire extends BlockContainerSingle {
        
        public Campfire() {
            
            super("furnace_campfire", BlockMaterial.STONE_FURNITURE,
                    5F, GuiList.CAMPFIRE.ordinal());
        }
        
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world,
                BlockPos pos) {
            
            TileEntity te = world.getTileEntity(pos);
        
            if (te instanceof TEFurnaceSingle.Campfire) {
                
                if (((TEFurnaceSingle.Campfire) te).isHeating()) {
                    
                    return 14;
                }
            }
            
            return 12;
        }

        @Override
        public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
                IBlockState state, int fortune, TileEntity te,
                ItemStack tool, EntityPlayer player) {
            
            return Collections.emptyList();
        }
        
        @Override
        public TileEntity createTileEntity(World worldIn, IBlockState state) {

            return new TEFurnaceSingle.Campfire();
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {

            return SIX;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return TWO;
        }
    }
    
    /** Pot fire block. */
    public static class Potfire extends BlockContainerSingle {
        
        public Potfire() {
            
            super("furnace_potfire", BlockMaterial.WOOD_FURNITURE,
                    5F, GuiList.POTFIRE.ordinal());
        }
        
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world,
                BlockPos pos) {
            
            TileEntity te = world.getTileEntity(pos);
        
            if (te instanceof TEFurnaceSingle.Potfire) {
                
                if (((TEFurnaceSingle.Potfire) te).isHeating()) {
                    
                    return 14;
                }
            }
            
            return 12;
        }
            
        @Override
        public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
                IBlockState state, int fortune, TileEntity te,
                ItemStack tool, EntityPlayer player) {
            
            return Lists.newArrayList(new ItemStack(GeoItems.POT_CLAY));
        }

        @Override
        public TileEntity createTileEntity(World worldIn, IBlockState state) {

            return new TEFurnaceSingle.Potfire();
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {

            return SIX;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return CENTRE_FOURTEEN;
        }
    }
}
