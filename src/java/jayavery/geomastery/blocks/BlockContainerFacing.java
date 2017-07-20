/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.main.GuiHandler.GuiList;
import jayavery.geomastery.tileentities.TECompostheap;
import jayavery.geomastery.tileentities.TEStorage;
import jayavery.geomastery.utilities.BlockMaterial;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Container block with horizontal facing. */
public class BlockContainerFacing extends BlockContainerSingle {
    
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
        
    public BlockContainerFacing(String name, Material material,
            float hardness, int ordinal) {
        
        super(name, material, hardness, ordinal);
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos, EnumFacing targetSide,
            EnumFacing placeFacing, ItemStack stack, EntityPlayer player) {
        
        BlockPos placePos = targetPos.offset(targetSide);
        IBlockState setState = this.getDefaultState()
                .withProperty(FACING, placeFacing);
        
        if (this.isValid(world, placePos, stack, false, setState, player)) {
            
            world.setBlockState(placePos, setState);
            return true;
        }
        
        return false;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(FACING,
                EnumFacing.getHorizontal(meta));
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }

    /** Compost heap block. */
    public static class Compostheap extends BlockContainerFacing {
        
        public Compostheap() {
            
            super("compostheap", BlockMaterial.WOOD_FURNITURE,
                    1F, GuiList.COMPOSTHEAP.ordinal());
        }
        
        @Override
        public TileEntity createTileEntity(World world, IBlockState state) {
            
            return new TECompostheap();
        }
        
        @Override
        public void addCollisionBoxToList(IBlockState state, World world,
                BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
                @Nullable Entity entity, boolean unused) {
            
            EnumFacing facing = state.getValue(FACING);
            int index = facing.getHorizontalIndex();
            
            for (int i = 0; i < 4; i++) {
                
                if (i != index) {
                    
                    addCollisionBoxToList(pos, entityBox, list, SIDE[i]);
                }
            }
        }
    }
    
    /** Chest block. */
    public static class Chest extends BlockContainerFacing {
        
        public Chest() {
            
            super("chest", BlockMaterial.WOOD_FURNITURE,
                    5F, GuiList.CHEST.ordinal());
        }
        
        @Override
        public TileEntity createTileEntity(World world, IBlockState state) {

            return new TEStorage.Chest();
        }
        
        @Override
        public EnumBlockRenderType getRenderType(IBlockState state) {
            
            return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public boolean hasCustomBreakingProgress(IBlockState state) {
            
            return true;
        }
    }
}
