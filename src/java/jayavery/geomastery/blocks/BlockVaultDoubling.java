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
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.IDoublingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Doubling vault block. */
public class BlockVaultDoubling extends BlockVault implements IDoublingBlock {

    public static final PropertyBool DOUBLE = PropertyBool.create("double");
    
    public BlockVaultDoubling(String name, EBlockWeight weight, int stackSize) {
        
        super(name, BlockMaterial.STONE_FURNITURE, weight, stackSize);
    }
    
    @Override
    public boolean isDouble(IBlockState state) {
        
        return state.getValue(DOUBLE);
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
        
        IBlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();
        placeFacing = targetSide.getAxis().isHorizontal() ?
                targetSide.getOpposite() : placeFacing;
        IBlockState setState = this.getDefaultState()
                .withProperty(FACING, placeFacing);

        if (targetBlock == this && this.shouldDouble(targetState
                .getActualState(world, targetPos), targetSide)) {
            
            world.setBlockState(targetPos,
                    setState.withProperty(DOUBLE, true));
       
        } else {
            
            targetPos = targetPos.offset(targetSide);
            targetState = world.getBlockState(targetPos);
            targetBlock = targetState.getBlock();
            setState = setState.withProperty(DOUBLE, false);
            
            if (!this.isValid(world, targetPos, stack,
                    false, setState, player)) {
                
                return false;
            }
            
            world.setBlockState(targetPos, setState);
        }
        
        return true;
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack tool) {
            
        if (this.isDouble(state)) {
            
            world.setBlockState(pos, state.withProperty(DOUBLE, false));
            
        } else {
            
            world.setBlockToAir(pos);
        }
        
        this.doHarvest(world, pos, state, player, te, tool);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {
        
        return Lists.newArrayList(new ItemStack(this.item,
                this.isDouble(state) && player == null ? 2 : 1));
    }

    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        if (state.getValue(DOUBLE)) {
            
            return false;
        }
        
        EVaultShape shape = state.getValue(SHAPE);
        
        if (shape != EVaultShape.SINGLE) {
            
            return true;
            
        } else {
            
            EnumFacing facing = state.getValue(FACING);
            return side != facing.rotateY() && side != facing.rotateYCCW();            
        }
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, SHAPE, FACING, DOUBLE);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = super.getMetaFromState(state);
        return state.getValue(DOUBLE) ? meta | 8 : meta;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return super.getStateFromMeta(meta)
                .withProperty(DOUBLE, (meta & 8) != 0);
    }
}
