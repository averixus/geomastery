package jayavery.geomastery.items;

import jayavery.geomastery.blocks.BlockBed;
import jayavery.geomastery.blocks.BlockBed.EnumPartBed;
import jayavery.geomastery.tileentities.TEBed;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Bed placing item. */
public class ItemBed extends ItemBlockplacer {
        
    /** The block type this item places. */
    private final BlockBed bed;

    public ItemBed(String name, BlockBed bed) {
        
        super(name, 1, CreativeTabs.DECORATIONS, SoundType.CLOTH);
        this.bed = bed;
        this.setMaxDamage(TEBed.MAX_USES);
    }

    @Override
    protected boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing, ItemStack stack) {

        BlockPos posFoot = targetPos.offset(targetSide);
        IBlockState stateFoot = world.getBlockState(posFoot);
        Block blockFoot = stateFoot.getBlock();
        
        BlockPos posHead = posFoot.offset(placeFacing);
        IBlockState stateHead = world.getBlockState(posHead);
        Block blockHead = stateHead.getBlock();
        
        boolean validFoot = blockFoot.isReplaceable(world, posFoot) &&
                this.bed.isValid(world, posFoot);
        boolean validHead = blockHead.isReplaceable(world, posHead) &&
                this.bed.isValid(world, posHead);
        
        if (!validFoot || !validHead) {
            
            return false;
        }
        
        int usesLeft = stack.getMaxDamage() - stack.getItemDamage();
        
        IBlockState placeFoot = this.bed.getDefaultState()
                .withProperty(BlockBed.OCCUPIED, false)
                .withProperty(BlockBed.FACING, placeFacing)
                .withProperty(BlockBed.PART, EnumPartBed.FOOT);
        
        IBlockState placeHead = placeFoot
                .withProperty(BlockBed.PART, EnumPartBed.HEAD);
        
        world.setBlockState(posHead, placeHead);
        world.setBlockState(posFoot, placeFoot);
        
        TEBed bed = (TEBed) world.getTileEntity(posFoot);
        bed.setUsesLeft(usesLeft);
        
        return true;
    }
}
