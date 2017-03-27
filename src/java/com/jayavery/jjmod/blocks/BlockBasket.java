package com.jayavery.jjmod.blocks;

import java.util.Random;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TEBasket;
import com.jayavery.jjmod.utilities.BlockMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBasket extends BlockComplexAbstract {

    public BlockBasket() {
        
        super("basket", BlockMaterial.WOOD_HANDHARVESTABLE, 2, null);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return Item.getItemFromBlock(this);
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.BASKET.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TEBasket();
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        Block below = world.getBlockState(pos.down()).getBlock();
        return ModBlocks.LIGHT.contains(below) ||
                ModBlocks.HEAVY.contains(below);
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess source, BlockPos pos) {
        
        return CENTRE_EIGHT;
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
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[0]);
    }
}
