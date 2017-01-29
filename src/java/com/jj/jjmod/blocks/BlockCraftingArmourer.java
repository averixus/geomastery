package com.jj.jjmod.blocks;

import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TECraftingArmourer;
import com.jj.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCraftingArmourer extends BlockComplexAbstract {
    
    public static final PropertyEnum<EnumPartArmourer> PART = PropertyEnum.<EnumPartArmourer>create("part", EnumPartArmourer.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingArmourer() {
        
        super("crafting_armourer", BlockMaterial.STONE_FURNITURE, 5F, ToolType.NONE);
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        System.out.println("creating tile entity");
        return new TECraftingArmourer();
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos unused) {
        
        //TODO
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TECraftingArmourer)) {
            
            return;
        }
        
        TECraftingArmourer tileArmourer = (TECraftingArmourer) tileEntity;
        EnumPartArmourer part = tileArmourer.getPart();
        EnumFacing facing = tileArmourer.getFacing();
        
        switch (part) {
            
            case T: {
                
                boolean brokenL = world.getBlockState(pos.down()).getBlock() != this;
                
                if (brokenL) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking top");
                }
                
                break;
            }
            
            case L: {
                
                boolean brokenT = world.getBlockState(pos.up()).getBlock() != this;
                boolean brokenM = world.getBlockState(pos.offset(facing.rotateY())).getBlock() != this;
                
                if (brokenT || brokenM) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking left");
                }
                
                break;
            }
            
            case M: {
                
                boolean brokenL = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
                boolean brokenR = world.getBlockState(pos.offset(facing.rotateY())).getBlock() != this;
                
                if (brokenL || brokenR) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking middle");
                }
                
                break;
            }
            
            case R: {
                
                boolean brokenM = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
                
                if (brokenM) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking right at " + pos + " because " + pos.offset(facing.rotateYCCW()) + " not this");
                }
                
                break;
            }
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        //TODO
        return FULL_BLOCK_AABB;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TECraftingArmourer) {
            
            TECraftingArmourer tileArmourer = (TECraftingArmourer) tileEntity;
            
            state = state.withProperty(PART, tileArmourer.getPart());
            state = state.withProperty(FACING, tileArmourer.getFacing());
        }
        
        return state;
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }
    
    @Override
    public void activate(EntityPlayer player, World world, int x, int y, int z) {
        
        player.openGui(Main.instance, GuiList.ARMOURER.ordinal(), world, x, y, z);
    }
}
