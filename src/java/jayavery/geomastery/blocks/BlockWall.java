/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.UnlistedPropertyBool;
import jayavery.geomastery.utilities.UnlistedPropertyWall;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/** Complex wall for multipart state rendering. */
public abstract class BlockWall extends BlockBuildingAbstract<ItemPlacing.Building> implements IDoublingBlock, Comparable<BlockWall> {
    
    public static final PropertyBool TOP = PropertyBool.create("top");
    public static final PropertyBool BOTTOM = PropertyBool.create("bottom");
    public static final PropertyBool DOUBLE = PropertyBool.create("double");
    
    public static final UnlistedPropertyWall NORTH = new UnlistedPropertyWall("north");
    public static final UnlistedPropertyBool N_TOP = new UnlistedPropertyBool("n_top");
    public static final UnlistedPropertyBool N_BOTTOM = new UnlistedPropertyBool("n_bottom");
    public static final UnlistedPropertyBool N_DOUBLE = new UnlistedPropertyBool("n_double");
    public static final UnlistedPropertyWall EAST = new UnlistedPropertyWall("east");
    public static final UnlistedPropertyBool E_TOP = new UnlistedPropertyBool("e_top");
    public static final UnlistedPropertyBool E_BOTTOM = new UnlistedPropertyBool("e_bottom");
    public static final UnlistedPropertyBool E_DOUBLE = new UnlistedPropertyBool("e_double");
    public static final UnlistedPropertyWall SOUTH = new UnlistedPropertyWall("south");
    public static final UnlistedPropertyBool S_TOP = new UnlistedPropertyBool("s_top");
    public static final UnlistedPropertyBool S_BOTTOM = new UnlistedPropertyBool("s_bottom");
    public static final UnlistedPropertyBool S_DOUBLE = new UnlistedPropertyBool("s_double");
    public static final UnlistedPropertyWall WEST = new UnlistedPropertyWall("west");
    public static final UnlistedPropertyBool W_TOP = new UnlistedPropertyBool("w_top");
    public static final UnlistedPropertyBool W_BOTTOM = new UnlistedPropertyBool("w_bottom");
    public static final UnlistedPropertyBool W_DOUBLE = new UnlistedPropertyBool("w_double");
    
    /** Convenience map of facing to wall properties. */
    private static final Map<EnumFacing, UnlistedPropertyWall> blocks = Maps.newEnumMap(EnumFacing.class);
    /** Convenience map of facing to top properties. */
    private static final Map<EnumFacing, UnlistedPropertyBool> tops = Maps.newEnumMap(EnumFacing.class);
    /** Convenience map of facing to bottom properties. */
    private static final Map<EnumFacing, UnlistedPropertyBool> bottoms = Maps.newEnumMap(EnumFacing.class);
    /** Convenience map of facing to double properties. */
    private static final Map<EnumFacing, UnlistedPropertyBool> doubles = Maps.newEnumMap(EnumFacing.class);
    static {
        blocks.put(EnumFacing.NORTH, NORTH);
        blocks.put(EnumFacing.EAST, EAST);
        blocks.put(EnumFacing.SOUTH, SOUTH);
        blocks.put(EnumFacing.WEST, WEST);
        tops.put(EnumFacing.NORTH, N_TOP);
        tops.put(EnumFacing.EAST, E_TOP);
        tops.put(EnumFacing.SOUTH, S_TOP);
        tops.put(EnumFacing.WEST, W_TOP);
        bottoms.put(EnumFacing.NORTH, N_BOTTOM);
        bottoms.put(EnumFacing.EAST, E_BOTTOM);
        bottoms.put(EnumFacing.SOUTH, S_BOTTOM);
        bottoms.put(EnumFacing.WEST, W_BOTTOM);
        doubles.put(EnumFacing.NORTH, N_DOUBLE);
        doubles.put(EnumFacing.EAST, E_DOUBLE);
        doubles.put(EnumFacing.SOUTH, S_DOUBLE);
        doubles.put(EnumFacing.WEST, W_DOUBLE);
    }

    /** Counter for priority to ensure no repeats. */
    private static int counter = 0;
    
    /** This block's model priority. */
    protected final int priority;
    /** This block's side model angle. */
    protected final int sideAngle;
    
    public BlockWall(Material material, String name, float hardness,
            int sideAngle, int stackSize) {
        
        super(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, stackSize);
        this.priority = counter++;
        this.sideAngle = sideAngle;
    }
    
    public int getSideAngle() {
        
        return this.sideAngle;
    }
    
    @Override
    public int compareTo(BlockWall other) {
        
        return other == null ? 1 : this.priority - other.priority;
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack tool) {
            
        if (this.isDouble(state)) {
            
            world.setBlockState(pos, state.withProperty(DOUBLE, false));
            
        } else {
            
            world.setBlockToAir(pos);
        }
        
        this.doHarvest(world, pos, state, player, te, tool);
    }

    // Player is null when broken from neighbour changed, i.e. straight to air
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {
        
        return Lists.newArrayList(new ItemStack(this.item,
                this.isDouble(state) && player == null ? 2 : 1));
    }

    @Override
    public boolean isDouble(IBlockState state) {
        
        return state.getValue(DOUBLE);
    }
    
    @Override
    public IBlockState getExtendedState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return state;
        }
        
        IExtendedBlockState exState = (IExtendedBlockState) state;
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            BlockPos offsetPos = pos.offset(facing);
            IBlockState offsetState = world.getBlockState(offsetPos)
                    .getActualState(world, offsetPos);
            Block offsetBlock = offsetState.getBlock();
            
            boolean isTop = false;
            boolean isBottom = false;
            boolean isDouble = false;
            
            if (offsetBlock instanceof BlockWall) {
                
                isTop = offsetState.getValue(TOP);
                isBottom = offsetState.getValue(BOTTOM);
                isDouble = offsetState.getValue(DOUBLE);
                
            } else if (offsetBlock instanceof BlockBuildingAbstract &&
                    ((BlockBuildingAbstract<?>) offsetBlock).shouldConnect(
                    world, offsetState, offsetPos, facing.getOpposite())) {
                
                offsetBlock = state.getBlock();
                isTop = state.getValue(TOP);
                isBottom = state.getValue(BOTTOM);
                isDouble = state.getValue(DOUBLE);
                
            } else if (!(offsetBlock instanceof BlockBuildingAbstract) &&
                    EBlockWeight.getWeight(offsetState)
                    .canSupport(this.getWeight(state))) {
                
                offsetBlock = state.getBlock();
                isTop = state.getValue(TOP);
                isBottom = state.getValue(BOTTOM);
                isDouble = state.getValue(DOUBLE);
                
            } else {
                
                offsetBlock = null;
            }
            
            exState = exState.withProperty(blocks.get(facing),
                    (BlockWall) offsetBlock);
            exState = exState.withProperty(tops.get(facing), isTop);
            exState = exState.withProperty(bottoms.get(facing), isBottom);
            exState = exState.withProperty(doubles.get(facing), isDouble);
        }
        
        return exState;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new ExtendedBlockState(this,
                new IProperty[] {TOP, BOTTOM, DOUBLE},
                new IUnlistedProperty[] {NORTH, EAST, SOUTH, WEST,
                N_TOP, E_TOP, S_TOP, W_TOP, N_BOTTOM, E_BOTTOM, S_BOTTOM,
                W_BOTTOM, N_DOUBLE, E_DOUBLE, S_DOUBLE, W_DOUBLE});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(DOUBLE) ? 1 : 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(DOUBLE, meta > 0);
    }
}
