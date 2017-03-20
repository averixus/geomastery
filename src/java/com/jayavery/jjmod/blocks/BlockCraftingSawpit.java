package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TECraftingSawpit;
import com.jayavery.jjmod.tileentities.TECraftingSawpit.EnumPartSawpit;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Sawpit crafting block. */
public class BlockCraftingSawpit extends BlockMultiAbstract<EnumPartSawpit> {

    public static final PropertyEnum<EnumPartSawpit> PART =
            PropertyEnum.<EnumPartSawpit>create("part", EnumPartSawpit.class);
    
    public BlockCraftingSawpit() {
        
        super("crafting_sawpit", BlockMaterial.WOOD_HANDHARVESTABLE, 5F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TECraftingSawpit();
    }
    
    @Override
    protected PropertyEnum<EnumPartSawpit> getPartProperty() {
        
        return PART;
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.SAWPIT.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }
}
