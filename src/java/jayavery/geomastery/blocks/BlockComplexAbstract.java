package jayavery.geomastery.blocks;

import java.util.Random;
import jayavery.geomastery.tileentities.TEContainerAbstract;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.Block;
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
 * GUIs, Containers, complex drops. */
public abstract class BlockComplexAbstract extends BlockBuilding {

    public BlockComplexAbstract(String name, Material material,
            float hardness, ToolType harvestTool) {

        super(material, name, null, hardness, harvestTool);
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }

    /** All implementations have to override this. Activates TileEntitiy/GUI. */
    public abstract boolean activate(EntityPlayer player, World world, int x,
            int y, int z);
    
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
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
}
