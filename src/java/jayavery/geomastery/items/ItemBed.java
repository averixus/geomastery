/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.function.Supplier;
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
    private final Supplier<BlockBed> bed;

    public ItemBed(String name, Supplier<BlockBed> bed) {
        
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
        
        BlockBed bedBlock = this.bed.get();
        
        boolean validFoot = blockFoot.isReplaceable(world, posFoot) &&
                bedBlock.isValid(world, posFoot);
        boolean validHead = blockHead.isReplaceable(world, posHead) &&
                bedBlock.isValid(world, posHead);
        
        if (!validFoot || !validHead) {
            
            return false;
        }
        
        int usesLeft = stack.getMaxDamage() - stack.getItemDamage();
        
        IBlockState placeFoot = bedBlock.getDefaultState()
                .withProperty(BlockBed.OCCUPIED, false)
                .withProperty(BlockBed.FACING, placeFacing)
                .withProperty(BlockBed.PART, EnumPartBed.FOOT);
        
        IBlockState placeHead = placeFoot
                .withProperty(BlockBed.PART, EnumPartBed.HEAD);
        
        world.setBlockState(posHead, placeHead);
        world.setBlockState(posFoot, placeFoot);
        
        TEBed bedTE = (TEBed) world.getTileEntity(posFoot);
        bedTE.setUsesLeft(usesLeft);
        
        return true;
    }
}
