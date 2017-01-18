package com.jj.jjmod.blocks;

import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRock extends BlockNew {

    public BlockRock(String name, float hardness) {

        super(Material.ROCK, name, hardness, ToolType.PICKAXE);
    }
}
