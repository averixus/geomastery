package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.tileentities.TEMultiAbstract;
import com.jayavery.jjmod.tileentities.TEMultiAbstract;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockMultiAbstract<E extends Enum<E> & IMultipart> extends BlockComplexAbstract {
    
    public static final PropertyDirection FACING = PropertyDirection
            .create("facing", EnumFacing.Plane.HORIZONTAL);
        
    public BlockMultiAbstract(String name, BlockMaterial material,
            float hardness) {
        
        super(name, material, hardness, null);
    }
    
    protected abstract PropertyEnum<E> getPartProperty();
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0005F);
        spawnAsEntity(world, pos, ((TEMultiAbstract<?>) te).getDrop());
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos unused) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEMultiAbstract<?>)) {
            
            return;
        }
        
        TEMultiAbstract<?> tileCrafting = (TEMultiAbstract<?>) tileEntity;
        
        if (tileCrafting.shouldBreak()) {
            
            world.setBlockToAir(pos);
            spawnAsEntity(world, pos, tileCrafting.getDrop());
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEMultiAbstract<?>)) {
            
            return FULL_BLOCK_AABB;
        }
        
        TEMultiAbstract<?> tileCrafting = (TEMultiAbstract<?>) tileEntity;
        return tileCrafting.getBoundingBox();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEMultiAbstract<?>)) {
            
            return FULL_BLOCK_AABB;
        }
        
        TEMultiAbstract<?> tileCrafting = (TEMultiAbstract<?>) tileEntity;
        return tileCrafting.getCollisionBox();
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TEMultiAbstract<?>) {
            
            @SuppressWarnings("unchecked")
            TEMultiAbstract<E> tileCrafting = (TEMultiAbstract<E>) tileEntity;
            state = tileCrafting.applyActualState(state, this.getPartProperty());
        }
        
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState();
    }
}
