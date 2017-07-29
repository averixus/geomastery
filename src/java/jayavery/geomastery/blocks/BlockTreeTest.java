/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TEST
public class BlockTreeTest extends BlockBuildingAbstract<ItemPlacing.Building> {
    
    public static final PropertyEnum<ETreeType> TYPE = PropertyEnum.create("type", ETreeType.class);
    public static final PropertyBool LEAVES = PropertyBool.create("leaves");
    
    private final Supplier<BlockTrunkTest> fallen;

    public BlockTreeTest(String name, Supplier<BlockTrunkTest> fallen) {
        
        super(Material.WOOD, name, null, 2F, 1);
        this.fallen = fallen;
    }
    
    public BlockTrunkTest getFallen() {
        
        return this.fallen.get();
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        Block blockBelow = stateBelow.getBlock();
        
        if (blockBelow instanceof BlockStumpTest) {
            
            ((BlockStumpTest) blockBelow).fall(world, pos.down(), stateBelow);
        }
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
    // noop
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
        
        return new BlockStateContainer(this, TYPE, LEAVES);
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
        
        return state.getValue(TYPE).ordinal();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(TYPE, ETreeType.values()[meta]);
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return false;
    }
}
