package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Main;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TECraftingSawpit;
import com.jayavery.jjmod.tileentities.TECraftingSawpit.EnumPartSawpit;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.sun.istack.internal.Nullable;
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
public class BlockCraftingSawpit extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartSawpit> PART =
            PropertyEnum.<EnumPartSawpit>create("part", EnumPartSawpit.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public BlockCraftingSawpit() {
        
        super("crafting_sawpit", BlockMaterial.WOOD_HANDHARVESTABLE, 5F, null);
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TECraftingSawpit) te).getPart().shouldDrop()) {

            spawnAsEntity(world, pos, new ItemStack(ModItems.craftingSawpit));
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TECraftingSawpit();
    }
    
    @Override
    public boolean hasTileEntity() {
        
        return true;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        state = this.getActualState(state, world, pos);
        EnumPartSawpit part = state.getValue(PART);
        EnumFacing facing = state.getValue(FACING);
        boolean broken = false;
                
        switch (part) {
            
            case B1: {
                
                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }
            
            case B2: {
                
                boolean brokenB3 = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                boolean brokenB1 = world.getBlockState(pos
                        .offset(facing.rotateYCCW())).getBlock() != this;
                
                broken = brokenB3 || brokenB1;
                break;
            }
            
            case B3: {
                
                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }
            
            case B4: {
                
                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }
            
            case B5: {
                
                broken = world.getBlockState(pos.up()).getBlock() != this;
                break;
            }
            
            case M5: {
                
                boolean brokenM4 = world.getBlockState(pos
                        .offset(facing.rotateYCCW())).getBlock() != this;
                boolean brokenB5 = world.getBlockState(pos
                        .down()).getBlock() != this;
                
                broken = brokenM4 || brokenB5;
                break;
            }
            
            case M4: {
                
                broken = world.getBlockState(pos.offset(facing.rotateYCCW()))
                        .getBlock() != this;
                break;
            }
            
            case M3: {
                
                broken = world.getBlockState(pos.offset(facing.rotateYCCW()))
                        .getBlock() != this;
                break;
            }
            
            case M2: {
                
                broken = world.getBlockState(pos.offset(facing.rotateYCCW()))
                        .getBlock() != this;
                break;
            }
            
            case M1: {
                
                boolean brokenT1 = world.getBlockState(pos.up())
                        .getBlock() != this;
                boolean brokenB1 = world.getBlockState(pos.down())
                        .getBlock() != this;
                
                broken = brokenT1 || brokenB1;
                break;
            }
            
            case T1: {
                
                boolean brokenT2 = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                boolean brokenM1 = world.getBlockState(pos
                        .down()).getBlock() != this;
                
                IBlockState frontSupport = world.getBlockState(pos
                        .offset(facing.getOpposite()));
                Block fsBlock = frontSupport.getBlock();
                boolean fsValid = false;
                
                if (fsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) fsBlock;
                    
                    fsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(fsBlock)) {
                    
                    fsValid = true;
                }
                
                IBlockState backSupport = world.getBlockState(pos
                        .offset(facing));
                Block bsBlock = backSupport.getBlock();
                boolean bsValid = false;
                
                if (bsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) bsBlock;
                    bsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(bsBlock)) {
                    
                    bsValid = true;
                }
                
                broken = brokenT2 || brokenM1 || !fsValid || !bsValid;
                break;
            }
            
            case T2: {
                
                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }
            
            case T3: {
                
                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;                
                break;
            }
            
            case T4: {
                
                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }
            
            case T5: {
                
                boolean brokenT4 = world.getBlockState(pos
                        .offset(facing.rotateYCCW())).getBlock() != this;
                boolean brokenM5 = world.getBlockState(pos
                        .down()).getBlock() != this;
                
                IBlockState frontSupport = world.getBlockState(pos
                        .offset(facing.getOpposite()));
                Block fsBlock = frontSupport.getBlock();
                boolean fsValid = false;
                
                if (fsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) fsBlock;
                    
                    fsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(fsBlock)) {
                    
                    fsValid = true;
                }
                
                IBlockState backSupport = world.getBlockState(pos
                        .offset(facing));
                Block bsBlock = backSupport.getBlock();
                boolean bsValid = false;
                
                if (bsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) bsBlock;
                    bsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(bsBlock)) {
                    
                    bsValid = true;
                }
                
                broken = brokenT4 || brokenM5 || !fsValid || !bsValid;
                break;
            }
        }
        
        if (broken) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
                
                spawnAsEntity(world, pos,
                        new ItemStack(ModItems.craftingSawpit));
            }
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumPartSawpit part = state.getValue(PART);
        
        if (part.isPassable()) {
        
            return FULL_BLOCK_AABB;
        }
        
        int axis = state.getValue(FACING).getHorizontalIndex() % 2;
        return CENTRE_HALF_LOW[axis];
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);        
        return state.getValue(PART).isPassable() ? NULL_AABB :
            this.getBoundingBox(state, world, pos);

    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TECraftingSawpit) {
            
            TECraftingSawpit tileSawpit = (TECraftingSawpit) tileEntity;
            
            state = tileSawpit.getPart() == null ? state :
                state.withProperty(PART, tileSawpit.getPart());
            state = tileSawpit.getFacing() == null ? state :
                state.withProperty(FACING, tileSawpit.getFacing());
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
            
            player.openGui(Main.instance, GuiList.SAWPIT.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
