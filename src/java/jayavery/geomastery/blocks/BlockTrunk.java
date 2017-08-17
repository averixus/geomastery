/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockTrunk extends BlockNew {
    
    public static final PropertyEnum<ETreeType> TYPE = BlockTree.TYPE;
    public static final PropertyBool LEAVES = BlockTree.LEAVES;
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EGiantPart> GIANT_PART = PropertyEnum.create("giant_part", EGiantPart.class);

    private final boolean leaves;
    private final boolean giant;
    
    private final BlockStateContainer stateContainer;
    
    public BlockTrunk(String name, boolean leaves, boolean giant) {
        
        super(BlockMaterial.TREES, name, null, 2F, EToolType.AXE);
        this.leaves = leaves;
        this.giant = giant;
        this.stateContainer = this.createBlockState();
        this.setDefaultState(this.stateContainer.getBaseState());
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        List<IProperty<?>> props = Lists.newArrayList(TYPE, FACING);
        
        if (this.leaves) {
            
            props.add(LEAVES);
        }
        
        if (this.giant) {
            
            props.add(GIANT_PART);
        }
        
        return new BlockStateContainer(this, props.toArray(new IProperty<?>[] {}));
    }
    
    @Override
    public BlockStateContainer getBlockState() {
        
        return this.stateContainer == null ? this.blockState : this.stateContainer;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        TileEntity te = world.getTileEntity(pos);
        
        if (this.leaves) {
            
            if (Blocks.LEAVES.isOpaqueCube(Blocks.LEAVES.getDefaultState())) {
                
                state = state.withProperty(LEAVES, false);
                
            } else {
                
                state = state.withProperty(LEAVES, false);
                
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    
                    IBlockState found = world.getBlockState(pos.offset(facing));
                    
                    if (found.getBlock() instanceof BlockLeaves && found.getValue(BlockLeaves.TYPE) == state.getValue(TYPE)) {
                        
                        state = state.withProperty(LEAVES, true);
                    }
                }
            }
        }
        
        if (this.giant) {
            
            boolean bottom = world.getBlockState(pos.up()).getBlock() == this;
          //  boolean top = world.getBlockState(pos.down()).getBlock() == this;
            boolean left = world.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock() == this;
         //   boolean right = world.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock() == this;
            
            EGiantPart part = bottom ? left ? EGiantPart.BL : EGiantPart.BR : left ? EGiantPart.TL : EGiantPart.TR;
            
            state = state.withProperty(GIANT_PART, part);
        }
        
        return state;
    }
    
    public static enum EGiantPart implements IStringSerializable {
        
        BL("bl"), BR("br"), TL("tl"), TR("tr");
        
        private final String name;
        
        private EGiantPart(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
    }
}
