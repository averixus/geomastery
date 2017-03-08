package com.jayavery.jjmod.blocks;

import java.util.Random;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Main;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TEDrying;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
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

/** Drying rack block. */
public class BlockDrying extends BlockComplexAbstract {

    public BlockDrying() {

        super("drying", BlockMaterial.WOOD_HANDHARVESTABLE, 5F, null);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand,
            int fortune) {
        
        return Item.getItemFromBlock(this);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TEDrying();
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {}

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
            
            player.openGui(Main.instance, GuiList.DRYING.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
