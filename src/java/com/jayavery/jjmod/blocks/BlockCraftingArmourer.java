package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TECraftingArmourer;
import com.jayavery.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
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

/** Armourer crafting block. */
public class BlockCraftingArmourer extends BlockComplexAbstract {
    
    public static final PropertyEnum<EnumPartArmourer> PART = PropertyEnum
            .<EnumPartArmourer>create("part", EnumPartArmourer.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingArmourer() {
        
        super("crafting_armourer", BlockMaterial.STONE_HANDHARVESTABLE,
                5F, null);
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TECraftingArmourer) te).getPart().shouldDrop()) {

            spawnAsEntity(world, pos, new ItemStack(ModItems.craftingArmourer));
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        return new TECraftingArmourer();
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
                
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TECraftingArmourer)) {
            
            return;
        }
        
        TECraftingArmourer tileArmourer = (TECraftingArmourer) tileEntity;
        EnumPartArmourer part = tileArmourer.getPart();
        EnumFacing facing = tileArmourer.getFacing();
        boolean broken = false;
        
        switch (part) {
            
            case T: {
                
                broken = world.getBlockState(pos.down())
                        .getBlock() != this;
                break;
            }
            
            case L: {
                
                boolean brokenT = world.getBlockState(pos.up())
                        .getBlock() != this;
                boolean brokenM = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                
                broken = brokenM || brokenT;
                break;
            }
            
            case M: {
                
                boolean brokenL = world.getBlockState(pos
                        .offset(facing.rotateYCCW())).getBlock() != this;
                boolean brokenR = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                
                broken = brokenL || brokenR;
                break;
            }
            
            case R: {
                
                broken = world.getBlockState(pos.offset(facing.rotateYCCW()))
                        .getBlock() != this;
                break;
            }
        }
        
        if (broken) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
                
                spawnAsEntity(world, pos,
                        new ItemStack(ModItems.craftingArmourer));
            }
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        EnumPartArmourer part = state.getValue(PART);
        int facing = state.getValue(FACING).getHorizontalIndex();
        
        switch (part) {
            
            case R: 
                return TWELVE;

            case L: 
                return HALF[(facing + 1) % 4];
            
            case T: 
                return CORNER[(facing + 2) % 4];
                
            case M: 
            default: 
                return FULL_BLOCK_AABB;
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        EnumPartArmourer part = state.getValue(PART);
        
        switch (part) {
            
            case R: 
                return TWELVE;
            
            case M: 
                return FOURTEEN;
            
            case L: 
            case T: 
                return NULL_AABB;
            
            default: 
                return FULL_BLOCK_AABB;
        }
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TECraftingArmourer) {
            
            TECraftingArmourer tileArmourer = (TECraftingArmourer) tileEntity;
            
            state = tileArmourer.getPart() == null ? state :
                state.withProperty(PART, tileArmourer.getPart());
            state = tileArmourer.getFacing() == null ? state :
                state.withProperty(FACING, tileArmourer.getFacing());
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
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {

        if (!world.isRemote) {
            
            player.openGui(Jjmod.instance, GuiList.ARMOURER.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
