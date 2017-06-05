package jayavery.geomastery.blocks;

import java.util.Map;
import com.google.common.collect.Maps;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.ToolType;
import jayavery.geomastery.utilities.UnlistedPropertyBool;
import jayavery.geomastery.utilities.UnlistedPropertyWall;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public abstract class BlockWall extends BlockBuilding
        implements IDoublingBlock, Comparable<BlockWall> {
    
    public static final PropertyBool TOP = PropertyBool.create("top");
    public static final PropertyBool BOTTOM = PropertyBool.create("bottom");
    
    public static final UnlistedPropertyWall NORTH =
            new UnlistedPropertyWall("north");
    public static final UnlistedPropertyBool N_TOP =
            new UnlistedPropertyBool("n_top");
    public static final UnlistedPropertyBool N_BOTTOM = 
            new UnlistedPropertyBool("n_bottom");
    public static final UnlistedPropertyWall EAST =
            new UnlistedPropertyWall("east");
    public static final UnlistedPropertyBool E_TOP =
            new UnlistedPropertyBool("e_top");
    public static final UnlistedPropertyBool E_BOTTOM =
            new UnlistedPropertyBool("e_bottom");
    public static final UnlistedPropertyWall SOUTH =
            new UnlistedPropertyWall("south");
    public static final UnlistedPropertyBool S_TOP =
            new UnlistedPropertyBool("s_top");
    public static final UnlistedPropertyBool S_BOTTOM =
            new UnlistedPropertyBool("s_bottom");
    public static final UnlistedPropertyWall WEST =
            new UnlistedPropertyWall("west");
    public static final UnlistedPropertyBool W_TOP =
            new UnlistedPropertyBool("w_top");
    public static final UnlistedPropertyBool W_BOTTOM =
            new UnlistedPropertyBool("w_bottom");
    
    /** Convenience map of facing to walltype properties. */
    private static final Map<EnumFacing, UnlistedPropertyWall>
            blocks = Maps.newEnumMap(EnumFacing.class);
    /** Convenience map of facing to top properties. */
    private static final Map<EnumFacing, UnlistedPropertyBool> tops =
            Maps.newEnumMap(EnumFacing.class);
    /** Convenience map of facing to bottom propertyies. */
    private static final Map<EnumFacing, UnlistedPropertyBool> bottoms =
            Maps.newEnumMap(EnumFacing.class);
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
    }

    /** Counter for priority to ensure no repeats. */
    protected static int counter = 0;
    
    /** This block's model priority. */
    protected final int priority;
    
    /** This block's side model angle. */
    protected final int sideAngle;
    
    public BlockWall(Material material, String name, float hardness,
            ToolType harvestTool, int sideAngle) {
        
        super(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, harvestTool);
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
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state;
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
            
            if (offsetBlock instanceof BlockWall) {
                
                isTop = offsetState.getValue(TOP);
                isBottom = offsetState.getValue(BOTTOM);
                
            } else {
                
                offsetBlock = null;
            }
            
            exState = exState.withProperty(blocks.get(facing),
                    (BlockWall) offsetBlock);
            exState = exState.withProperty(tops.get(facing), isTop);
            exState = exState.withProperty(bottoms.get(facing), isBottom);
        }
        
        return exState;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new ExtendedBlockState(this, new IProperty[] {TOP, BOTTOM},
                new IUnlistedProperty[] {NORTH, EAST, SOUTH, WEST, N_TOP, E_TOP,
                S_TOP, W_TOP, N_BOTTOM, E_BOTTOM, S_BOTTOM, W_BOTTOM});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }
}
