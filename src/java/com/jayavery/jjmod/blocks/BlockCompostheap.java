package com.jayavery.jjmod.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECompost;
import com.jayavery.jjmod.utilities.BlockMaterial;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Compost heap block. */
public class BlockCompostheap extends BlockComplexAbstract {

    public BlockCompostheap() {
        
        super("compostheap", BlockMaterial.WOOD_HANDHARVESTABLE, 1F, null);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TECompost();
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
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
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.COMPOST.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
