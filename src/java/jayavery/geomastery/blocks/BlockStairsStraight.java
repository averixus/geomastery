/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Straight stairs block. */
public abstract class BlockStairsStraight extends BlockFacing {

    public BlockStairsStraight(String name, float hardness, int stackSize) {
        
        super(name, BlockMaterial.WOOD_FURNITURE,
                hardness, stackSize, EBlockWeight.NONE);
    }
    
    protected abstract boolean hasValidConnection(World world, BlockPos pos);
    
    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.buildTooltips) {
        
            tooltip.add(I18n.format(Lang.BUILDTIP_STAIRS));
            tooltip.add(I18n.format(EBlockWeight.NONE.supports()));
        }
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
                
        if (!this.hasValidSupport(world, pos)) {
            
            message(player, Lang.BUILDFAIL_STAIRS_SUPPORT);
            return false;
        }
        
        if (!this.hasValidConnection(world, pos)) {
            
            message(player, Lang.BUILDFAIL_STAIRS_WIDTH);
            return false;
        }

        return true;
    }
    
    /** @return Whether this block is supported from below or nearby stairs. */
    protected boolean hasValidSupport(World world, BlockPos pos) {
        
        boolean supported = false;
        
        if (EBlockWeight.getWeight(world.getBlockState(pos.down()))
                .canSupport(this.getWeight(this.getDefaultState()))) {
            
            supported = true;
            
        } else {
            
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                
                BlockPos posUp = pos.offset(facing).up();
                Block blockUp = world.getBlockState(posUp).getBlock();
                IBlockState foundationUp = world.getBlockState(posUp.down());
                BlockPos posDown = pos.offset(facing.getOpposite()).down();
                Block blockDown = world.getBlockState(posDown).getBlock();
                IBlockState foundationDown = world
                        .getBlockState(posDown.down());
                
                if ((blockUp instanceof BlockStairsStraight &&
                        EBlockWeight.getWeight(foundationUp)
                        .canSupport(this.getWeight(this.getDefaultState()))) ||
                        (blockDown instanceof BlockStairsStraight &&
                        EBlockWeight.getWeight(foundationDown)
                        .canSupport(this.getWeight(this.getDefaultState())))) {
                    
                    supported = true;
                    break;
                }
            }
        } 
         
        return supported;
    }
    
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox,
            List<AxisAlignedBB> list, Entity entity, boolean unused) {
                
        int facing = (state.getValue(FACING).getHorizontalIndex() + 1) % 4;
        
        for (AxisAlignedBB box : STAIRS_STRAIGHT[facing]) {
            
            addCollisionBoxToList(pos, entityBox, list, box);
        }
    }
    
    /** Single block width limited stairs.*/
    public static class Single extends BlockStairsStraight {
        
        public Single(String name, float hardness, int stackSize) {
            
            super(name, hardness, stackSize);
        }

        @Override
        public BlockStateContainer createBlockState() {

            return new BlockStateContainer(this, FACING);
        }

        @Override
        protected boolean hasValidConnection(World world, BlockPos pos) {

            for (EnumFacing direction : EnumFacing.HORIZONTALS) {
                
                Block connect = world.getBlockState(pos
                        .offset(direction)).getBlock();
                
                if (connect == this) {
                    
                    return false;
                }
            }
            
            return true;
        }
    }
    
    /** Two block width limited stairs. */
    public static class Joining extends BlockStairsStraight {
        
        public static final PropertyEnum<EStairsConnection> CONNECTION =
                PropertyEnum.<EStairsConnection>create("connection",
                EStairsConnection.class);
        
        public Joining(String name, float hardness, int stackSize) {
            
            super(name, hardness, stackSize);
        }
        
        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess world,
                BlockPos pos) {
            
            EnumFacing facing = state.getValue(FACING);
            EnumFacing left = facing.rotateYCCW();
            EnumFacing right = facing.rotateY();
            
            if (world.getBlockState(pos.offset(right)).getBlock() == this) {
                
                state = state.withProperty(CONNECTION, EStairsConnection.RIGHT);
                
            } else if (world.getBlockState(pos.offset(left))
                    .getBlock() == this) {
                
                state = state.withProperty(CONNECTION, EStairsConnection.LEFT);
                
            } else {
                
                state = state.withProperty(CONNECTION, EStairsConnection.NONE);
            }

            return state;
        }
        
        @Override
        public BlockStateContainer createBlockState() {
            
            return new BlockStateContainer(this, FACING, CONNECTION);
        }

        @Override
        protected boolean hasValidConnection(World world, BlockPos pos) {

            int count = 0;

            for (EnumFacing direction : EnumFacing.HORIZONTALS) {
                
                Block block = world.getBlockState(pos.offset(direction))
                        .getBlock();
                
                if (block == this) {
                    
                    count++;
                    
                    Block nextBlock = world.getBlockState(pos
                            .offset(direction, 2)).getBlock();
                    
                    if (nextBlock == this) {
                        
                        return false;
                    }
                }
            }
            
            if (count > 1) {
                
                return false;
            }
            
            return true;
        }
    }
    
    /** Enum defining connection type of this block. */
    public enum EStairsConnection implements IStringSerializable {
        
        LEFT("left"), RIGHT("right"), NONE("none");
        
        private String name;
        
        private EStairsConnection(String name) {
            
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
