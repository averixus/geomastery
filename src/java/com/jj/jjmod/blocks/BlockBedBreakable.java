package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import com.jj.jjmod.tileentities.TEBed;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Bed block which breaks after a fixed number of uses. */
public class BlockBedBreakable extends BlockBedAbstract
        implements ITileEntityProvider {

    public BlockBedBreakable(String name, float hardness,
            Supplier<Item> itemRef, boolean isFlat, ToolType harvestTool) {

        super(name, hardness, itemRef, isFlat, harvestTool);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world,
            BlockPos pos, IBlockState state, int fortune) {
        
        List<ItemStack> drops = new ArrayList<ItemStack>();
        
        if (state.getValue(PART) == EnumPartBed.FOOT) {
            
            TEBed tileBed = (TEBed) world.getTileEntity(pos);
            int damage = tileBed.getDropDamage();
            ItemStack stack = new ItemStack(this.itemRef.get(), 1, damage);

            if (!tileBed.isBroken()) {

                drops.add(stack);
            }
        }
        
        return drops;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        if (this.getStateFromMeta(meta).getValue(PART) == EnumPartBed.FOOT) {

            return new TEBed(this.itemRef.get().getMaxDamage());

        } else {

            return null;
        }
    }
}
