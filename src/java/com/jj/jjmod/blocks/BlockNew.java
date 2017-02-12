package com.jj.jjmod.blocks;

import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** General superclass for all new blocks, with default
 * implementation for simple blocks. */
public class BlockNew extends Block {

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
    
    protected static void spawnItem(World world, BlockPos pos, Item item) {
        
        if (!world.isRemote) {
            
            world.spawnEntity(new EntityItem(world, pos.getX(),
                    pos.getY(), pos.getZ(), new ItemStack(item)));
        }
    }
    
    // From material
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return (!this.blockMaterial.isSolid() ||
                !this.blockMaterial.blocksLight() ||
                !this.blockMaterial.isOpaque()) ? false : true;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return this.isFullCube(state);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return this.blockMaterial.isSolid() ?
                this.getBoundingBox(state, world, pos) : NULL_AABB;
    }
}
