package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockLode extends BlockRock {

    private Supplier<Item> itemRef;
    private int maxDropped;

    public BlockLode(String name, Supplier<Item> item, int maxDropped) {

        super(name, 4F);
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
