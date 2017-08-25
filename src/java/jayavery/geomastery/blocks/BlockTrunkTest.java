/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TEST
public class BlockTrunkTest extends BlockBuildingAbstract<ItemPlacing.Building> {

    public static final PropertyEnum<ETrunkAxis> AXIS = PropertyEnum.create("axis", ETrunkAxis.class);
    public static final PropertyEnum<ETreeType> TYPE = PropertyEnum.create("type", ETreeType.class);
    public static final PropertyBool LEAVES = PropertyBool.create("leaves");
    
    public BlockTrunkTest(String name) {
        
        super(Material.WOOD, name, CreativeTabs.BUILDING_BLOCKS, 2F, 1);
    }
    
    @Override
    protected ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.MEDIUM;
    }
    
    @Override @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        
        return Blocks.LEAVES.getBlockLayer();
    }
 
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, AXIS, TYPE, LEAVES);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        if (Blocks.LEAVES.isOpaqueCube(Blocks.LEAVES.getDefaultState())) {
            
            return state.withProperty(LEAVES, false);
        }
        
        ETrunkAxis axis = state.getValue(AXIS);
        
        for (EnumFacing facing : axis.getSurrounds()) {
            
            IBlockState found = world.getBlockState(pos.offset(facing));
            
            if (found.getBlock() instanceof BlockLeaves && found.getValue(BlockLeaves.TYPE) == state.getValue(TYPE)) {
                
                return state.withProperty(LEAVES, true);
            }
        }
        
        return state.withProperty(LEAVES, false);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = state.getValue(AXIS) == ETrunkAxis.X_AXIS ? 8 : 0;
      // TODO  meta += state.getValue(TYPE).ordinal();
        return meta;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        state = state.withProperty(AXIS, (meta & 8) > 0 ? ETrunkAxis.X_AXIS : ETrunkAxis.Z_AXIS);
    // TODO    state = state.withProperty(TYPE, ETreeType.values()[meta % 8]);
        return state;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return false;
    }
    
    public static enum ETrunkAxis implements IStringSerializable {
        
        X_AXIS("x_axis"), Z_AXIS("z_axis");
        
        private final String name;
        
        private ETrunkAxis(String name) {
            
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        public EnumFacing[] getSurrounds() {
            
            switch(this) {
                case X_AXIS:
                    return new EnumFacing[] {EnumFacing.EAST, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN};
                case Z_AXIS: default:
                    return new EnumFacing[] {EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.DOWN};
            }
        }
        
        public static ETrunkAxis getAxis(EnumFacing facing) {
            
            if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                
                return X_AXIS;
                
            } else {
                
                return Z_AXIS;
            }
        }
    }
}
