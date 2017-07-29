/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.tileentities.TEStump;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TEST
public class BlockStumpTest extends BlockBuildingAbstract<ItemPlacing.Building> {

    public static final PropertyBool FALLING = PropertyBool.create("falling");
    public static final PropertyEnum<ETreeType> TYPE = PropertyEnum.create("type", ETreeType.class);
    public static final PropertyBool LEAVES = PropertyBool.create("leaves");
    
    public BlockStumpTest(String name) {
        
        super(Material.WOOD, name, CreativeTabs.BUILDING_BLOCKS, 5F, 1);
//        this.setDefaultState(this.blockState.getBaseState().withProperty(LEAVES, false));
    }
    
    public void fall(World world, BlockPos pos, IBlockState state) {
        
        world.setBlockState(pos, state.withProperty(FALLING, true));
        ((TEStump) world.getTileEntity(pos)).fall(EnumFacing.NORTH);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.LIGHT;
    }
    
    @Override @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        
        return Blocks.LEAVES.getBlockLayer();
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FALLING, TYPE, LEAVES);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        if (Blocks.LEAVES.isOpaqueCube(Blocks.LEAVES.getDefaultState())) {
            
            return state.withProperty(LEAVES, false);
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            IBlockState found = world.getBlockState(pos.offset(facing));
            
            if (found.getBlock() instanceof BlockLeaves && found.getValue(BlockLeaves.TYPE) == state.getValue(TYPE)) {
                
                return state.withProperty(LEAVES, true);
            }
        }
        
        return state.withProperty(LEAVES, false);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = state.getValue(FALLING) ? 8 : 0;
        meta += state.getValue(TYPE).ordinal();
        return meta;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        state = state.withProperty(FALLING, (meta & 8) > 0);
        state = state.withProperty(TYPE, ETreeType.values()[meta % 8]);
        return state;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return state.getValue(FALLING);
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TEStump();
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        
        return this.hasTileEntity(state) ?
                EnumBlockRenderType.ENTITYBLOCK_ANIMATED :
                EnumBlockRenderType.MODEL;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return false;
    }
    
    public static class Small extends BlockStumpTest {

        public Small(String name) {
            
            super(name);
        }
        
        @Override
        public void fall(World world, BlockPos pos, IBlockState state) {
            
           // noop
        }
        
        @Override
        public BlockStateContainer createBlockState() {
            
            return new BlockStateContainer(this, TYPE, LEAVES);
        }
        
        @Override
        public int getMetaFromState(IBlockState state) {
            
            return state.getValue(TYPE).ordinal();
        }
        
        @Override
        public IBlockState getStateFromMeta(int meta) {
            
            return this.getDefaultState().withProperty(TYPE, ETreeType.values()[meta]);
        }
        
        @Override
        public boolean hasTileEntity(IBlockState state) {
            
            return false;
        }
    }
}
