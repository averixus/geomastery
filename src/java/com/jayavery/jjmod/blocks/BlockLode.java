package com.jayavery.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/** Lode block. */
public class BlockLode extends BlockRock {

    /** Supplier for item. */
    private Supplier<Item> itemRef;
    /* Maximum number of items dropped. */
    private int maxDropped;

    public BlockLode(String name, float hardness,
            Supplier<Item> item, int maxDropped) {

        super(name, hardness);
        this.itemRef = item;
        this.maxDropped = maxDropped;
    }

    @Override
    public Item getItemDropped(IBlockState state,
            Random rand, int fortune) {
        
        return this.itemRef.get();
    }
    
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        
        return random.nextInt(this.maxDropped) + 1;
    }
}
