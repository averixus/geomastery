package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
import com.jayavery.jjmod.tileentities.TECraftingWoodworking;
import com.jayavery.jjmod.tileentities.TECraftingWoodworking.EnumPartWoodworking;
import com.jayavery.jjmod.utilities.BlockMaterial;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** Woodworking crafting block. */
public class BlockCraftingWoodworking extends
        BlockMultiAbstract<EnumPartWoodworking> {

    public static final PropertyEnum<EnumPartWoodworking> PART = PropertyEnum
            .<EnumPartWoodworking>create("part", EnumPartWoodworking.class);

    public BlockCraftingWoodworking() {

        super("crafting_woodworking", BlockMaterial.WOOD_HANDHARVESTABLE, 5F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TECraftingWoodworking();
    }
    
    @Override
    protected PropertyEnum<EnumPartWoodworking> getPartProperty() {
        
        return PART;
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.WOODWORKING.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }
}
