package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TEFurnaceStone;
import com.jayavery.jjmod.tileentities.TEFurnaceStone.EnumPartStone;
import com.jayavery.jjmod.utilities.BlockMaterial;
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

/** Stone furnace block. */
public class BlockFurnaceStone extends BlockMultiAbstract<EnumPartStone> {

    public static final PropertyEnum<EnumPartStone> PART = PropertyEnum
            .<EnumPartStone>create("part", EnumPartStone.class);
    
    public BlockFurnaceStone() {

        super("furnace_stone", BlockMaterial.STONE_HANDHARVESTABLE, 5F);
        this.lightValue = 12;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TEFurnaceStone();
    }
    
    @Override
    public PropertyEnum<EnumPartStone> getPartProperty() {
        
        return PART;
    }
    
    @Override
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {
        
        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.STONE.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }
}