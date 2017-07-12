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
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Complex adaptive stairs block. */
public class BlockStairsComplex extends BlockBuilding {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<BlockStairs.EnumShape> SHAPE =
            PropertyEnum.<BlockStairs.EnumShape>create("shape",
            BlockStairs.EnumShape.class);
    
    public BlockStairsComplex(Material material,
            String name, float hardness, ToolType tool) {
        
        super(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, tool);
    }
    
    /** Adds this item's build reqs to the tooltip if config. */
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.buildTooltips) {
            
            tooltip.add(I18n.translateToLocal(BlockWeight.HEAVY.build()));
            tooltip.add(net.minecraft.client.resources.I18n.format(BlockWeight.NONE.support()));
        }
    }

    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return direction == state.getValue(FACING);
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
                }

                shape = BlockStairs.EnumShape.OUTER_RIGHT;
            }
        }

        offsetState = world.getBlockState(pos.offset(facing.getOpposite()));

        if (offsetState.getBlock() instanceof BlockStairsComplex) {
            
            EnumFacing offsetFacing = offsetState.getValue(FACING);

            if (offsetFacing.getAxis() != state.getValue(FACING).getAxis()) {
                
                if (offsetFacing == facing.rotateYCCW()) {
                    
                    shape = BlockStairs.EnumShape.INNER_LEFT;
                }

                shape = BlockStairs.EnumShape.INNER_RIGHT;
            }
        }

        return state.withProperty(SHAPE, shape);
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
        
        if (shape == EnumShape.INNER_LEFT || shape == EnumShape.OUTER_LEFT) {
            
            facing++;
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
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, SHAPE);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing side, float x, float y, float z,
            int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING,
                placer.getHorizontalFacing());
    }
}
