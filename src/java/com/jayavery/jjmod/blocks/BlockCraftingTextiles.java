package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECraftingTextiles;
import com.jayavery.jjmod.tileentities.TECraftingTextiles.EnumPartTextiles;
import com.jayavery.jjmod.utilities.BlockMaterial;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** Textiles crafting block. */
public class BlockCraftingTextiles extends
        BlockMultiAbstract<EnumPartTextiles> {

    public static final PropertyEnum<EnumPartTextiles> PART = PropertyEnum
            .<EnumPartTextiles>create("part", EnumPartTextiles.class);

    public BlockCraftingTextiles() {

        super("crafting_textiles", BlockMaterial.WOOD_HANDHARVESTABLE, 5F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TECraftingTextiles();
    }
    
    @Override
    protected PropertyEnum<EnumPartTextiles> getPartProperty() {
        
        return PART;
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world, int x, int y,
            int z) {

        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.TEXTILES.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }
}
