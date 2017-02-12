package com.jj.jjmod.blocks;

import com.jj.jjmod.main.GuiHandler.GuiList;
import java.util.Random;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Knapping crafting block. */
public class BlockCraftingKnapping extends BlockComplexAbstract {

    public BlockCraftingKnapping() {

        super("crafting_knapping", BlockMaterial.STONE_HANDHARVESTABLE,
                5F, null);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand,
            int fortune) {
        
        return Item.getItemFromBlock(this);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return null;
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return FLAT_BOUNDS;
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
    public void activate(EntityPlayer player, World world,
            int x, int y, int z) {

        player.openGui(Main.instance, GuiList.KNAPPING.ordinal(),
                world, x, y,z);
    }
}
