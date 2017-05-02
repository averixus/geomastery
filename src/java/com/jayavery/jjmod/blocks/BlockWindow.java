package com.jayavery.jjmod.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.blocks.BlockDoor.VaultAbove;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Window block. */
public class BlockWindow extends BlockBuilding {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<VaultAbove> VAULT =
            PropertyEnum.create("vault", VaultAbove.class);
    
    public BlockWindow() {
        
        super(BlockMaterial.WOOD_FURNITURE, "window",
                CreativeTabs.BUILDING_BLOCKS, 2F, ToolType.AXE);
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.LIGHT;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        EnumFacing facing = state.getValue(FACING);
        return facing != direction && facing != direction.getOpposite();
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing side, float x, float y, float z,
            int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING,
                placer.getHorizontalFacing());
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        
        int facing = state.getValue(FACING).getHorizontalIndex();
        return DOOR_CLOSED[(facing + 2) % 4];
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return this.getBoundingBox(state, world, pos);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        EnumFacing facing = state.getValue(FACING);
        BlockPos posUp = pos.up();
        IBlockState upState = world.getBlockState(posUp);
        VaultAbove vault = VaultAbove.NONE;
        
        if (upState.getBlock() instanceof BlockVault) {
            
            EnumFacing upFacing = upState.getActualState(world, posUp)
                    .getValue(BlockVault.FACING);
            
            if (upFacing == facing.rotateY()) {
                
                vault = VaultAbove.LEFT;
                
            } else if (upFacing == facing.rotateYCCW()) {
                
                vault = VaultAbove.RIGHT;
            }
        }
        
        return state.withProperty(VAULT, vault);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(FACING,
                EnumFacing.getHorizontal(meta));
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, VAULT);
    }
}
