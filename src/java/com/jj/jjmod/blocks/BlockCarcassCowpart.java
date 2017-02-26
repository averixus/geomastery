package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Cow part carcass block. */
public class BlockCarcassCowpart extends BlockCarcassAbstract {

    public BlockCarcassCowpart() {
        
        super("carcass_cowpart", 2F, 2, () -> ModItems.carcassCowpart);
    }
    
    @Override
    protected void spawnDrops(World world, BlockPos pos, long age) {
        
        ItemStack meat = new ItemStack(ModItems.beefRaw, 5);
        meat.getCapability(ModCaps.CAP_DECAY, null).setBirthTime(age);
        
        spawnAsEntity(world, pos, meat);
        spawnAsEntity(world, pos, new ItemStack(ModItems.skinCow, 6));
        spawnAsEntity(world, pos, new ItemStack(Items.BONE, 5));
        spawnAsEntity(world, pos, new ItemStack(ModItems.tallow));
    }
}
