package com.jayavery.jjmod.blocks;

import java.util.Random;
import com.jayavery.jjmod.tileentities.TEContainerAbstract;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/** Abstract superclass for complex blocks with TileEntities,
 * GUIs, non-solid models, complex drops. */
public abstract class BlockComplexAbstract extends BlockNew
        implements ITileEntityProvider {

    public BlockComplexAbstract(String name, Material material,
            float hardness, ToolType harvestTool) {

        super(material, name, null, hardness, harvestTool);
    }

    /** All implementations have to override this. Activates TileEntitiy/GUI. */
    public abstract boolean activate(EntityPlayer player, World world, int x,
            int y, int z);
    
    /** All implementations have to override this.
     * Checks whether this block is still in a valid position
     * and/or part of a valid structure, breaks if not. */
    @Override
    public abstract void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused);

    /** All implementations have to override this.
     * @return Highlight bounding box of complex-shaped block. */
    @Override
    public abstract AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos);

    /** All implementations have to override this.
     * @return BlockStateContainer of all needed properties. */
    @Override
    public abstract BlockStateContainer createBlockState();

    /** All implementations have to override this.
     * @return Actual state using TileEntity and position data. */
    @Override
    public abstract IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos);

    /** All implementations have to override this. Needed for blockstates. */
    @Override
    public abstract int getMetaFromState(IBlockState state);

    /** All implementations have to override this. Needed for blockstates. */
    @Override
    public abstract IBlockState getStateFromMeta(int meta);

    /** Cancel normal ItemBlock drop. */
    @Override
    public Item getItemDropped(IBlockState state, Random rand,
            int fortune) {
        
        return Items.AIR;
    }
    
    /** Convenience to activate TileEntity/GUI. */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {

        return this.activate(player, world, pos.getX(), pos.getY(), pos.getZ());
    }

    /** Always remove any TileEntity and drop contents. */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TEContainerAbstract) {

            ((TEContainerAbstract) tileEntity).dropItems();
            
        } else if (tileEntity.hasCapability(CapabilityItemHandler
                .ITEM_HANDLER_CAPABILITY, null)) {
            
            IItemHandler inventory = tileEntity
                    .getCapability(CapabilityItemHandler
                    .ITEM_HANDLER_CAPABILITY, null);

            for (int i = 0; i < inventory.getSlots(); i++) {
                
                ItemStack stack = inventory.getStackInSlot(i);
                
                if (!stack.isEmpty()) {
                    
                    world.spawnEntity(new EntityItem(world, pos.getX(),
                            pos.getY(), pos.getZ(), stack));
                }
            }
        }

        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
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
