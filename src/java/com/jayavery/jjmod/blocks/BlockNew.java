package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** General superclass for all new blocks, with utilities
 * and default implementation for simple blocks. */
public class BlockNew extends Block {
    
    // Bounding box references
    
    // Rotationally symmetrical boxes
    
    public static final AxisAlignedBB TOP_HALF =
            new AxisAlignedBB(0,0.5,0,1,1,1);
    public static final AxisAlignedBB TOP_FIFTH =
            new AxisAlignedBB(0,0.8,0,1,1,1);
    public static final AxisAlignedBB FOURTEEN =
            new AxisAlignedBB(0,0,0,1,0.87,1);
    public static final AxisAlignedBB TWELVE =
            new AxisAlignedBB(0,0,0,1,0.75,1);
    public static final AxisAlignedBB TEN = new AxisAlignedBB(0,0,0,1,0.62,1);
    public static final AxisAlignedBB EIGHT = new AxisAlignedBB(0,0,0,1,0.5,1);
    public static final AxisAlignedBB SIX = new AxisAlignedBB(0,0,0,1,0.37,1);
    public static final AxisAlignedBB FOUR = new AxisAlignedBB(0,0,0,1,0.25,1);
    public static final AxisAlignedBB TWO = new AxisAlignedBB(0,0,0,1,0.12,1);
    public static final AxisAlignedBB CENTRE_SIXTEEN =
            new AxisAlignedBB(0.25,0,0.25,0.75,1,0.75);
    public static final AxisAlignedBB CENTRE_FOURTEEN =
            new AxisAlignedBB(0.25,0,0.25,0.75,0.87,0.75);
    public static final AxisAlignedBB CENTRE_TEN =
            new AxisAlignedBB(0.25,0,0.25,0.75,0.62,0.75);
    public static final AxisAlignedBB CENTRE_EIGHT =
            new AxisAlignedBB(0.25,0,0.25,0.75,0.5,0.75);
    public static final AxisAlignedBB CENTRE_FOUR =
            new AxisAlignedBB(0.25,0,0.25,0.75,0.25,0.75);
    public static final AxisAlignedBB CENTRE_TWO =
            new AxisAlignedBB(0.25,0,0.25,0.75,0.12,0.75);
    public static final AxisAlignedBB BEAM_FLOOR =
            new AxisAlignedBB(0,0.94,0,1,1,1);

    // General rotating boxes
    
    /** Half blocks, indexed by facing. */
    public static final AxisAlignedBB[] HALF =
            {new AxisAlignedBB(0,0,0.5,1,1,1), new AxisAlignedBB(0,0,0,0.5,1,1),
            new AxisAlignedBB(0,0,0,1,1,0.5), new AxisAlignedBB(0.5,0,0,1,1,1)};
    /** Blips, indexed by facing. */
    public static final AxisAlignedBB[] BLIP =
            {new AxisAlignedBB(0.37,0.37,0.75,0.62,0.62,1),
            new AxisAlignedBB(0,0.37,0.37,0.25,0.62,0.62),
            new AxisAlignedBB(0.37,0.37,0,0.62,0.62,0.25),
            new AxisAlignedBB(0.75,0.37,0.37,1,0.62,0.62)};
    /** Corners, indexed by facing. */
    public static final AxisAlignedBB[] CORNER =
            {new AxisAlignedBB(0,0,0,0.5,1,0.5),
            new AxisAlignedBB(0.5,0,0,1,1,0.5),
            new AxisAlignedBB(0.5,0,0.5,1,1,1),
            new AxisAlignedBB(0,0,0.5,0.5,1,1)};
    /** Whole sides, indexed by facing. */
    public static final AxisAlignedBB[] SIDE = 
            {new AxisAlignedBB(0,0,0,1,1,0.1),
            new AxisAlignedBB(0.9,0,0,1,1,1),
            new AxisAlignedBB(0,0,0.9,1,1,1),
            new AxisAlignedBB(0,0,0,0.1,1,1)};
    /** Centre halves, indexed by axis. */
    public static final AxisAlignedBB[] CENTRE_HALF =
        {new AxisAlignedBB(0,0,0.25,1,1,0.75),
        new AxisAlignedBB(0.25,0,0,0.75,1,1)};
    /** Low centre halves, indexed by axis. */
    public static final AxisAlignedBB[] CENTRE_HALF_LOW =
            {new AxisAlignedBB(0,0,0.25,1,0.75,0.75),
            new AxisAlignedBB(0.25,0,0,0.75,0.75,1)};
    /** Beams, indexed by axis. */
    public static final AxisAlignedBB[] BEAM =
            {new AxisAlignedBB(0.37,0.5,0,0.63,1,1),
            new AxisAlignedBB(0,0.5,0.37,1,0.9,0.63)};
    public static final AxisAlignedBB[] BEAM_THIN =
            {new AxisAlignedBB(0.43,0.75,0,0.56,1,1),
            new AxisAlignedBB(0,0.75,0.43,1,1,0.56)};
            
    // Wall part boxes
    
    public static final AxisAlignedBB CENTRE_POST =
            new AxisAlignedBB(0.25, 0, 0.25,0.75,1,0.75);
    public static final AxisAlignedBB BRANCH_WEST =
            new AxisAlignedBB(0,0,0.25,0.25,1,0.75);
    public static final AxisAlignedBB BRANCH_SOUTH =
            new AxisAlignedBB(0.25,0,0.75,0.75,1,1);
    public static final AxisAlignedBB BRANCH_EAST =
            new AxisAlignedBB(0.75,0,0.25,1,1,0.75);
    public static final AxisAlignedBB BRANCH_NORTH =
            new AxisAlignedBB(0.25,0,0,0.75,1,0.25);
    public static final AxisAlignedBB CENTRE_POST_LOW =
            new AxisAlignedBB(0.25,0,0.25,0.75,0.75,0.75);
    public static final AxisAlignedBB BRANCH_WEST_LOW =
            new AxisAlignedBB(0,0,0.25,0.25,0.75,0.75);
    public static final AxisAlignedBB BRANCH_SOUTH_LOW =
            new AxisAlignedBB(0.25,0,0.75,0.75,0.75,1);
    public static final AxisAlignedBB BRANCH_EAST_LOW =
            new AxisAlignedBB(0.75,0,0.25,1,0.75,0.75);
    public static final AxisAlignedBB BRANCH_NORTH_LOW =
            new AxisAlignedBB(0.25,0,0,0.75,0.75,0.25);
    public static final AxisAlignedBB CENTRE_POST_THIN =
            new AxisAlignedBB(0.43,0,0.43,0.57,1,0.57);
    public static final AxisAlignedBB BRANCH_WEST_THIN =
            new AxisAlignedBB(0,0,0.43,0.43,1,0.57);
    public static final AxisAlignedBB BRANCH_NORTH_THIN =
            new AxisAlignedBB(0.43,0,0.57,0.57,1,1);
    public static final AxisAlignedBB BRANCH_EAST_THIN =
            new AxisAlignedBB(0.57,0,0.43,1,1,0.57);
    public static final AxisAlignedBB BRANCH_SOUTH_THIN =
            new AxisAlignedBB(0.43,0,0,0.57,1,0.43);

    // Rotating stairs in groups
    
    /** Straight stairs arrays, indexed by facing. */
    public static final AxisAlignedBB[][] STAIRS_STRAIGHT =
            {{new AxisAlignedBB(0,0,0,0.25,0.25,1),
            new AxisAlignedBB(0.25,0,0,0.5,0.5,1),
            new AxisAlignedBB(0.5,0,0,0.75,0.75,1),
            new AxisAlignedBB(0.75,0,0,1,1,1)},
            {new AxisAlignedBB(0,0,0,1,0.25,0.25),
            new AxisAlignedBB(0.5,0,0.25,1,0.5,0.5),
            new AxisAlignedBB(0,0,0.5,1,0.75,0.75),
            new AxisAlignedBB(0,0,0.75,1,1,1)},
            {new AxisAlignedBB(0.75,0,0,1,0.25,1),
            new AxisAlignedBB(0.5,0,0.5,0.75,0.5,1),
            new AxisAlignedBB(0.25,0,0,0.5,0.75,1),
            new AxisAlignedBB(0,0,0,0.25,1,1)},
            {new AxisAlignedBB(0,0,0.75,1,0.25,1),
            new AxisAlignedBB(0,0,0.5,0.5,0.5,1),
            new AxisAlignedBB(0,0,0.25,1,0.75,0.5),
            new AxisAlignedBB(0,0,0,1,1,0.25)}};
    /** External stairs arrays, indexed by facing. */
    public static final AxisAlignedBB[][] STAIRS_EXTERNAL =
            {{new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0.25,0.25,0.25,1,0.5,1),
            new AxisAlignedBB(0.5,0.5,0.5,1,0.75,1),
            new AxisAlignedBB(0.75,0.75,0.75,1,1,1)},
            {new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0.25,0.25,0,1,0.5,0.75),
            new AxisAlignedBB(0.5,0.5,0,1,0.75,0.5),
            new AxisAlignedBB(0.75,0.75,0,1,1,0.25)},
            {new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0,0.25,0,0.75,0.5,0.75),
            new AxisAlignedBB(0,0.5,0,0.5,0.75,0.5),
            new AxisAlignedBB(0,0.75,0,0.25,1,0.25)},
            {new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0,0.25,0.25,0.75,0.5,1),
            new AxisAlignedBB(0,0.5,0.5,0.5,0.75,1),
            new AxisAlignedBB(0,0.75,0.75,0.25,1,1)}};
    /** Internal stairs arrays, indexed by facing. */
    public static final AxisAlignedBB[][] STAIRS_INTERNAL =
            {{new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0,0.25,0.25,1,0.5,1),
            new AxisAlignedBB(0.25,0.25,0,1,0.5,0.25),
            new AxisAlignedBB(0,0.5,0.5,1,0.75,1),
            new AxisAlignedBB(0.5,0.5,0,1,0.75,0.5),
            new AxisAlignedBB(0,0.75,0.75,1,1,1),
            new AxisAlignedBB(0.75,0.75,0,1,1,0.75)},
            {new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0,0.25,0,0.75,0.5,1),
            new AxisAlignedBB(0.75,0.25,0.25,1,0.5,1),
            new AxisAlignedBB(0,0.5,0,0.5,0.75,1),
            new AxisAlignedBB(0.5,0.5,0.5,1,0.75,1),
            new AxisAlignedBB(0,0.75,0,0.25,1,1),
            new AxisAlignedBB(0.25,0.75,0.75,1,1,1)},
            {new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0,0.25,0,1,0.5,0.75),
            new AxisAlignedBB(0,0.25,0.75,0.75,0.5,1),
            new AxisAlignedBB(0,0.5,0,1,0.75,0.5),
            new AxisAlignedBB(0,0.5,0.5,0.5,0.75,1),
            new AxisAlignedBB(0,0.75,0,1,1,0.25),
            new AxisAlignedBB(0,0.75,0.25,0.25,1,1)},
            {new AxisAlignedBB(0,0,0,1,0.25,1),
            new AxisAlignedBB(0.25,0.25,0,1,0.5,1),
            new AxisAlignedBB(0,0.25,0,0.25,0.5,0.75),
            new AxisAlignedBB(0.5,0.5,0,1,0.75,1),
            new AxisAlignedBB(0,0.5,0,0.5,0.75,0.5),
            new AxisAlignedBB(0.75,0.75,0,1,1,1),
            new AxisAlignedBB(0,0.75,0,0.75,1,0.25)}};
    
    // Rotating vaults in groups
    
    /** Straight vault arrays, indexed by facing. */
    public static final AxisAlignedBB[][] VAULT_STRAIGHT =
            {{new AxisAlignedBB(0,0.25,0.75,1,1,1),
            new AxisAlignedBB(0,0.8,0.25,1,1,0.75)},
            {new AxisAlignedBB(0,0.25,0,0.25,1,1),
            new AxisAlignedBB(0.25,0.8,0,0.75,1,1)},
            {new AxisAlignedBB(0,0.25,0,1,1,0.25),
            new AxisAlignedBB(0,0.8,0.25,1,1,0.75)},
            {new AxisAlignedBB(0.75,0.25,0,1,1,1),
            new AxisAlignedBB(0.25,0.8,0,0.75,1,1)}};
    /** External vault arrays, indexed by facing. */
    public static final AxisAlignedBB[][] VAULT_EXTERNAL =
            {{new AxisAlignedBB(0.75,0.25,0,1,1,0.25),
            new AxisAlignedBB(0.25,0.8,0,1,1,0.75)},
            {new AxisAlignedBB(0,0.25,0,0.25,1,0.25),
            new AxisAlignedBB(0,0.8,0,0.75,1,0.75)},
            {new AxisAlignedBB(0,0.25,0.75,0.25,1,1),
            new AxisAlignedBB(0,0.8,0.25,0.75,1,1)},
            {new AxisAlignedBB(0.75,0.25,0.75,1,1,1),
            new AxisAlignedBB(0.25,0.8,0.25,1,1,1)}};
    /** Internal vault arrays, indexed by facing. */
    public static final AxisAlignedBB[][] VAULT_INTERNAL =
            {{new AxisAlignedBB(0,0.25,0,1,1,0.25),
            new AxisAlignedBB(0,0.25,0.25,0.25,1,1),
            new AxisAlignedBB(0.25,0.8,0.25,1,0,0)},
            {new AxisAlignedBB(0,0.25,0,0.25,1,1),
            new AxisAlignedBB(0.25,0.25,0.75,1,1,1),
            new AxisAlignedBB(0.25,0.8,0,0,0,0.75)},
            {new AxisAlignedBB(0,0.25,0.75,1,1,1),
            new AxisAlignedBB(0.75,0.25,0,1,1,0.75),
            new AxisAlignedBB(0,0.8,1,0.75,0,0.75)},
            {new AxisAlignedBB(0.75,0.25,0,1,1,1),
            new AxisAlignedBB(0,0.25,0,0.75,1,0.25),
            new AxisAlignedBB(1,0.8,0.25,0.75,0,1)}};
    
    // Rotating doors
    
    /** Closed doors, indexed by facing. */
    public static final AxisAlignedBB[] DOOR_CLOSED =
            {new AxisAlignedBB(0,0,0.31,1,1,0.37),
            new AxisAlignedBB(0.63,0,0,0.69,1,1),
            new AxisAlignedBB(0,0,0.63,1,1,0.69),
            new AxisAlignedBB(0.31,0,0,0.37,1,1)};
    /** Left-side open doors, indexed by facing. */
    public static final AxisAlignedBB[] DOOR_OPEN_LEFT =
            {new AxisAlignedBB(0.88,0,0.37,0.94,1,1.37),
            new AxisAlignedBB(-0.37,0,0.88,0.63,1,0.94),
            new AxisAlignedBB(0.06,0,-0.37,0.12,1,0.63),
            new AxisAlignedBB(0.37,0,0.06,1.37,1,0.12)};
    /** Right-side open doors, indexed by facing. */
    public static final AxisAlignedBB[] DOOR_OPEN_RIGHT =
            {new AxisAlignedBB(0.06,0,0.37,0.12,1,1.37),
            new AxisAlignedBB(-0.37,0,0.06,0.63,1,0.12),
            new AxisAlignedBB(0.88,0,-0.37,0.94,1,0.63),
            new AxisAlignedBB(0.37,0,0.88,1.37,1,0.94)};
    
    public BlockNew(Material material, String name,
            CreativeTabs tab, float hardness, ToolType harvestTool) {
        
        super(material);
        setupBlock(this, name, tab, hardness, harvestTool);
    }
    
    public BlockNew(Material material, String name,
            float hardness, ToolType harvestTool) {
        
        this(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, harvestTool);
    }
    
    /** Assigns the given values to the block statically. */
    public static void setupBlock(Block block, String name, CreativeTabs tab,
            float hardness, ToolType harvestTool) {
        
        block.setRegistryName("block_" + name);
        block.setUnlocalizedName(block.getRegistryName().toString());
        block.setCreativeTab(tab);
        block.setHardness(hardness);
        
        if (harvestTool != null) {
            
            block.setHarvestLevel(harvestTool.toString(), 1);
        }
    }
    
    /** Basic material implementation. */
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return (!this.blockMaterial.isSolid() ||
                !this.blockMaterial.blocksLight() ||
                !this.blockMaterial.isOpaque()) ? false : true;
    }
    
    /** Basic full-opaque implementation. */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return this.isFullCube(state);
    }
    
    /** Basic material implementation. */
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return this.blockMaterial.isSolid() ?
                this.getBoundingBox(state, world, pos) : NULL_AABB;
    }
}
