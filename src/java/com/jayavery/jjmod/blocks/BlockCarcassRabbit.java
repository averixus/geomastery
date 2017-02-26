package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Rabbit carcass block. */
public class BlockCarcassRabbit extends BlockCarcassAbstract {

    public BlockCarcassRabbit() {
        
        super("carcass_rabbit", 1F, 2, () -> ModItems.carcassRabbit);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos, long age) {
        
        ItemStack meat = new ItemStack(ModItems.rabbitRaw);
        meat.getCapability(ModCaps.CAP_DECAY, null).setBirthTime(age);
        
        spawnAsEntity(world, pos, meat);
        spawnAsEntity(world, pos, new ItemStack(Items.RABBIT_HIDE));
        spawnAsEntity(world, pos, new ItemStack(Items.BONE));
    }
}
