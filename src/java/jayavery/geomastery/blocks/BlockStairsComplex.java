/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Complex adaptive stairs block. */
public class BlockStairsComplex extends BlockFacing {

    public static final PropertyEnum<BlockStairs.EnumShape> SHAPE = PropertyEnum.<BlockStairs.EnumShape>create("shape", BlockStairs.EnumShape.class);
    
    public BlockStairsComplex(Material material,
            String name, float hardness, int stackSize) {
        
        super(name, material, hardness, stackSize, EBlockWeight.NONE);
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return direction == state.getValue(FACING);
    }

    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (!alreadyPresent && !world.getBlockState(pos).getBlock()
                .isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
        
        if (!weightBelow.canSupport(EBlockWeight.HEAVY)) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        
        EnumShape shape = BlockStairs.EnumShape.STRAIGHT;
        EnumFacing facing = state.getValue(FACING);
        IBlockState offsetState = world.getBlockState(pos.offset(facing));
    
        if (offsetState.getBlock() instanceof BlockStairsComplex) {
            
            EnumFacing offsetFacing = offsetState.getValue(FACING);
    
            if (offsetFacing.getAxis() != state.getValue(FACING).getAxis()) {
                
                if (offsetFacing == facing.rotateYCCW()) {
                    
                    shape = BlockStairs.EnumShape.OUTER_LEFT;
                    
                } else {
    
                    shape = BlockStairs.EnumShape.OUTER_RIGHT;
                }
            }
        }
    
        offsetState = world.getBlockState(pos.offset(facing.getOpposite()));
    
        if (offsetState.getBlock() instanceof BlockStairsComplex) {
            
            EnumFacing offsetFacing = offsetState.getValue(FACING);
    
            if (offsetFacing.getAxis() != state.getValue(FACING).getAxis()) {
                
                if (offsetFacing == facing.rotateYCCW()) {
                    
                    shape = BlockStairs.EnumShape.INNER_LEFT;
                    
                } else {
    
                    shape = BlockStairs.EnumShape.INNER_RIGHT;
                }
            }
        }
    
        return state.withProperty(SHAPE, shape);
    }

    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, SHAPE);
    }

    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, World world,
            List<String> tooltip, ITooltipFlag advanced) {
        
        if (GeoConfig.textVisual.buildTooltips) {
            
            tooltip.add(I18n.format(EBlockWeight.HEAVY.requires()));
            tooltip.add(I18n.format(EBlockWeight.NONE.supports()));
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        EnumShape shape = state.getValue(SHAPE);

        int facing = state.getValue(FACING).getHorizontalIndex() + 1;
        
        if (shape == EnumShape.OUTER_LEFT) {
            
            facing--;
        } 
        
        facing %= 4;
        
        if (shape == EnumShape.INNER_LEFT || shape == EnumShape.INNER_RIGHT) {
            
            for (AxisAlignedBB box : STAIRS_INTERNAL[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
            
        } else if (shape == EnumShape.OUTER_LEFT ||
                shape == EnumShape.OUTER_RIGHT) {
            
            for (AxisAlignedBB box : STAIRS_EXTERNAL[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
            
        } else {
            
            for (AxisAlignedBB box : STAIRS_STRAIGHT[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
        }
    }
}
