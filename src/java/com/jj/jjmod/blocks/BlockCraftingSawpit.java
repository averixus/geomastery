package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TECraftingSawpit;
import com.jj.jjmod.tileentities.TECraftingSawpit.EnumPartSawpit;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
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

public class BlockCraftingSawpit extends BlockComplexAbstract implements ITileEntityProvider {

    public static final PropertyEnum<EnumPartSawpit> PART = PropertyEnum.<EnumPartSawpit>create("part", EnumPartSawpit.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public BlockCraftingSawpit() {
        
        super("crafting_sawpit", BlockMaterial.WOOD_FURNITURE, 5F, ToolType.NONE);
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
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos unused) {
        
        //TODO
        System.out.println("neighbor changed at " + pos + " from " + unused);
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TECraftingSawpit)) {
            
            return;
        }
        
        TECraftingSawpit tileSawpit = (TECraftingSawpit) tileEntity;
        EnumPartSawpit part = tileSawpit.getPart();
        EnumFacing facing = tileSawpit.getFacing();
        
        System.out.println("neighbour changed, pos " + pos + ", part " + part);
        
        switch (part) {
            
            case B1: {
                
                boolean brokenB2 = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                
                if (brokenB2) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case B2: {
                
                boolean brokenB3 = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                boolean brokenB1 = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
                
                if (brokenB3 || brokenB1) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case B3: {
                
                boolean brokenB4 = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                
                if (brokenB4) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case B4: {
                
                boolean brokenB5 = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;
                
                if (brokenB5) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case B5: {
                
                boolean brokenM5 = world.getBlockState(pos.up()).getBlock() != this;
                
                if (brokenM5) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case M5: {
                
                boolean brokenM4 = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
                boolean brokenB5 = world.getBlockState(pos.down()).getBlock() != this;
                
                if (brokenM4 || brokenB5) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case M4: {
                
                boolean brokenM3 = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
                
                if (brokenM3) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case M3: {
                
                boolean brokenM2 = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
            
                if (brokenM2) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case M2: {
                
                boolean brokenM1 = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
                
                if (brokenM1) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case M1: {
                
                boolean brokenT1 = world.getBlockState(pos.up()).getBlock() != this;
                boolean brokenB1 = world.getBlockState(pos.down()).getBlock() != this;
                
                if (brokenT1 || brokenB1) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case T1: {
                
                boolean brokenT2 = world.getBlockState(pos.offset(facing.rotateY())).getBlock() != this;
                boolean brokenM1 = world.getBlockState(pos.down()).getBlock() != this;
                
                IBlockState frontSupport = world.getBlockState(pos.offset(facing.getOpposite()));
                Block fsBlock = frontSupport.getBlock();
                boolean fsValid = false;
                
                if (fsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) fsBlock;
                    
                    fsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(fsBlock)) {
                    
                    fsValid = true;
                }
                
                IBlockState backSupport = world.getBlockState(pos.offset(facing));
                Block bsBlock = backSupport.getBlock();
                boolean bsValid = false;
                
                if (bsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) bsBlock;
                    bsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(bsBlock)) {
                    
                    bsValid = true;
                }
                
                if (brokenT2 || brokenM1 || !fsValid || !bsValid) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case T2: {
                
                boolean brokenT3 = world.getBlockState(pos.offset(facing.rotateY())).getBlock() != this;
                
                if (brokenT3) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case T3: {
                
                boolean brokenT4 = world.getBlockState(pos.offset(facing.rotateY())).getBlock() != this;
                
                if (brokenT4) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case T4: {
                
                boolean brokenT5 = world.getBlockState(pos.offset(facing.rotateY())).getBlock() != this;
                
                if (brokenT5) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
            
            case T5: {
                
                boolean brokenT4 = world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() != this;
                boolean brokenM5 = world.getBlockState(pos.down()).getBlock() != this;
                
                IBlockState frontSupport = world.getBlockState(pos.offset(facing.getOpposite()));
                Block fsBlock = frontSupport.getBlock();
                boolean fsValid = false;
                
                if (fsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) fsBlock;
                    
                    fsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(fsBlock)) {
                    
                    fsValid = true;
                }
                
                IBlockState backSupport = world.getBlockState(pos.offset(facing));
                Block bsBlock = backSupport.getBlock();
                boolean bsValid = false;
                
                if (bsBlock instanceof IBuildingBlock) {
                    
                    IBuildingBlock building = (IBuildingBlock) bsBlock;
                    bsValid = building.isDouble() && building.isHeavy();
                    
                } else if (ModBlocks.HEAVY.contains(bsBlock)) {
                    
                    bsValid = true;
                }
                
                if (brokenT4 || brokenM5 || !fsValid || !bsValid) {
                    
                    world.setBlockToAir(pos);
                    System.out.println("breaking on update at " + pos);
                }
                
                break;
            }
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {

        return FULL_BLOCK_AABB;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return this.getActualState(state, world, pos).getValue(PART).isPassable() ? NULL_AABB : FULL_BLOCK_AABB;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TECraftingSawpit) {
            
            TECraftingSawpit tileSawpit = (TECraftingSawpit) tileEntity;
            
            state = state.withProperty(PART, tileSawpit.getPart());
            state = state.withProperty(FACING, tileSawpit.getFacing());
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
        
        player.openGui(Main.instance, GuiList.SAWPIT.ordinal(), world, x, y, z);
    }
}
