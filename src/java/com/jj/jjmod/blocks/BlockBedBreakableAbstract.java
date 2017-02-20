package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import com.jj.jjmod.tileentities.TEBed;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Abstract superclass for bed blocks which break after a fixed number of uses. */
public abstract class BlockBedBreakableAbstract extends BlockBedAbstract
        implements ITileEntityProvider {

    public BlockBedBreakableAbstract(String name, float hardness, float healAmount,
            Supplier<Item> itemRef, ToolType harvestTool) {

        super(name, hardness, healAmount, itemRef, harvestTool);
    }
    
    @Override
    protected void drop(World world, BlockPos pos, TEBed bed) {

        int usesLeft = bed.getUsesLeft();
        Item item = this.itemRef.get();
        ItemStack drop = new ItemStack(item, 1, item.getMaxDamage() - usesLeft);
        spawnAsEntity(world, pos, drop);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        if (this.getStateFromMeta(meta).getValue(PART) == EnumPartBed.FOOT) {
            System.out.println("creating tile entity");
            TEBed tile = new TEBed();
            tile.setUsesLeft(this.itemRef.get().getMaxDamage());
            return tile;

        } else {

            return null;
        }
    }
}
