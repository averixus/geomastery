package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECraftingCandlemaker;
import com.jayavery.jjmod.tileentities.TECraftingCandlemaker.EnumPartCandlemaker;
import com.jayavery.jjmod.utilities.BlockMaterial;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** Candlemaker crafting block. */
public class BlockCraftingCandlemaker extends
        BlockMultiAbstract<EnumPartCandlemaker> {

    public static final PropertyEnum<EnumPartCandlemaker> PART = PropertyEnum
            .<EnumPartCandlemaker>create("part", EnumPartCandlemaker.class);
    
    public BlockCraftingCandlemaker() {

        super("crafting_candlemaker", BlockMaterial.WOOD_HANDHARVESTABLE, 5F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TECraftingCandlemaker();
    }
    
    @Override
    public PropertyEnum<EnumPartCandlemaker> getPartProperty() {
        
        return PART;
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.CANDLEMAKER.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }
}
