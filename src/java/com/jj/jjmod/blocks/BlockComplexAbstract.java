package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for complex blocks with TileEntities,
 * GUIs, non-solid models, complex drops. */
public abstract class BlockComplexAbstract extends BlockNew
        implements ITileEntityProvider {

    protected static final AxisAlignedBB FLAT_BOUNDS
            = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.18D, 1.0D);

    public BlockComplexAbstract(String name, Material material,
            float hardness, ToolType harvestTool) {

        super(material, name, null, hardness, harvestTool);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand,
            int fortune) {
        
        return Items.AIR;
    }

    @Override
    public abstract void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused);

    @Override
    public abstract AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos);

    @Override
    public abstract BlockStateContainer createBlockState();

    @Override
    public abstract IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos);

    @Override
    public abstract int getMetaFromState(IBlockState state);

    @Override
    public abstract IBlockState getStateFromMeta(int meta);

    /** Activates TileEntitiy/GUI. */
    public abstract void activate(EntityPlayer player, World world, int x,
            int y, int z);

    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {

        if (!world.isRemote) {

            this.activate(player, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {

        TileEntity tileentity = world.getTileEntity(pos);

        if (tileentity instanceof IInventory) {

            InventoryHelper.dropInventoryItems(world, pos,
                    (IInventory) tileentity);
        }

        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return this.blockMaterial.isSolid() ?
                this.getBoundingBox(state, world, pos) : NULL_AABB;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
