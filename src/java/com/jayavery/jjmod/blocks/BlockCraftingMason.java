package com.jayavery.jjmod.blocks;

import javax.annotation.Nullable;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECraftingMason;
import com.jayavery.jjmod.tileentities.TECraftingMason.EnumPartMason;
import com.jayavery.jjmod.utilities.BlockMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
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

/** Mason crafting block. */
public class BlockCraftingMason extends BlockMultiAbstract<EnumPartMason> {

    public static final PropertyEnum<EnumPartMason> PART =
            PropertyEnum.<EnumPartMason>create("part", EnumPartMason.class);
    
    public BlockCraftingMason() {

        super("crafting_mason", BlockMaterial.STONE_HANDHARVESTABLE, 5F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TECraftingMason();
    }
    
    @Override
    public PropertyEnum<EnumPartMason> getPartProperty() {
        
        return PART;
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.MASON.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }
}
