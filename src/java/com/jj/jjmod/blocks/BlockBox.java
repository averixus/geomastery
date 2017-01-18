package com.jj.jjmod.blocks;

import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TEBox;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBox extends BlockNew implements ITileEntityProvider {
    
    public static final AxisAlignedBB BOUNDS = new
            AxisAlignedBB(0.1875, 0, 0.125, 0.875, 0.4375, 0.8125);
    
    public BlockBox() {
        
        super(BlockMaterial.WOOD_FURNITURE, "box",
                CreativeTabs.BUILDING_BLOCKS, 5, ToolType.NONE);
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        if (!world.isRemote) {
            System.out.println("activating block");
            player.openGui(Main.instance, GuiList.BOX.ordinal(), world,
                    pos.getX(), pos.getY(), pos.getZ());
        }
        
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TEBox();
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TEBox) {
            
            InventoryHelper.dropInventoryItems(world,
                    pos, (IInventory) tileEntity);
        }
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess source, BlockPos pos) {
        
        return BOUNDS;
    }

    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
