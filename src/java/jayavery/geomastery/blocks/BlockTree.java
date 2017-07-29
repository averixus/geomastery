/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TEStump;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTree extends BlockNew {
    
    public static final PropertyEnum<ETreeType> TYPE = PropertyEnum.create("type", ETreeType.class);
    public static final PropertyBool LEAVES = PropertyBool.create("leaves");
    public static final PropertyBool FALLING = PropertyBool.create("falling");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    
    private final boolean leaves;
    private final boolean facing;
    private final boolean fallableStump;
    
    private final Supplier<BlockTrunkTest> trunk;
    
    private final BlockStateContainer stateContainer;
    
    public BlockTree(String name, boolean leaves, boolean facing, boolean fallableStump, Supplier<BlockTrunkTest> trunk) {
        
        super(BlockMaterial.TREES, name, null, 2F, EToolType.AXE);
        this.leaves = leaves;
        this.facing = facing;
        this.fallableStump = fallableStump;
        this.trunk = trunk;
        this.stateContainer = this.createBlockState();
        this.setDefaultState(this.stateContainer.getBaseState());
    }
    
    public BlockTrunkTest getFallen() {
        
        return this.trunk.get();
    }
    
    public boolean hasFallen() {
        
        return this.trunk != null;
    }
    
    public void serialiseActualState(IBlockState state, ByteBuf buf) {
                
        buf.writeInt(state.getValue(TYPE).ordinal());
        
        if (this.leaves) {
            
            buf.writeBoolean(state.getValue(LEAVES));
        }
        
        if (this.fallableStump) {
            
            buf.writeBoolean(state.getValue(FALLING));
        }
        
        if (this.facing) {
            
            buf.writeInt(state.getValue(FACING).getHorizontalIndex());
        }
    }
    
    public IBlockState deserialiseActualState(ByteBuf buf) {
        
        IBlockState state = this.getDefaultState();
        state = state.withProperty(TYPE, ETreeType.values()[buf.readInt()]);
        
        if (this.leaves) {
            
            state = state.withProperty(LEAVES, buf.readBoolean());
        }
        
        if (this.fallableStump) {
            
            state = state.withProperty(FALLING, buf.readBoolean());
        }
        
        if (this.facing) {
            
            state = state.withProperty(FACING, EnumFacing.getHorizontal(buf.readInt()));
        }
        
        return state;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        List<IProperty<?>> props = Lists.newArrayList(TYPE);
        
        if (this.leaves) {
            
            props.add(LEAVES);
        }
        
        if (this.facing) {
            
            props.add(FACING);
        }
        
        if (this.fallableStump) {
            
            props.add(FALLING);
        }
        
        return new BlockStateContainer(this,
                props.toArray(new IProperty<?>[] {}));
    }
    
    @Override
    public BlockStateContainer getBlockState() {
        
        return this.stateContainer == null ? this.blockState : this.stateContainer;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
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
        
        if (this.facing) {
            
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                
                if (world.getBlockState(pos.offset(facing)).getBlock() == this &&
                        world.getBlockState(pos.offset(facing.rotateY())).getBlock() == this) {
                    
                    state = state.withProperty(FACING, facing);
                }
            }
        }
        
        return state;
    }
    
    
    @Override @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = state.getValue(TYPE).ordinal();
        
        if (this.fallableStump && state.getValue(FALLING)) {
            
            meta = meta + 8;
        }
        
        return meta;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState().withProperty(TYPE, ETreeType.values()[meta % 8]);
        
        if (this.fallableStump) {
            
            state = state.withProperty(FALLING, (meta & 8) > 0);
        }
        
        return state;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return this.fallableStump && state.getValue(FALLING);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return this.fallableStump && state.getValue(FALLING) ?
                new TEStump() : null;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        
        return this.hasTileEntity(state) ?
                EnumBlockRenderType.ENTITYBLOCK_ANIMATED :
                EnumBlockRenderType.MODEL;
    }
    
    @Override
    public final boolean removedByPlayer(IBlockState state, World world,
            BlockPos pos, EntityPlayer player, boolean willHarvest) {
        
        if (willHarvest && this.trunk != null) {
            
            return true;
        }
        
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        
        if (this.trunk != null) {
            
            IBlockState stateBelow = world.getBlockState(pos.down());
            Block blockBelow = stateBelow.getBlock();
            
            if (blockBelow instanceof BlockTree &&
                    ((BlockTree) blockBelow).fallableStump &&
                    stateBelow.getValue(FALLING)) {
                
                world.setBlockState(pos.down(),
                        stateBelow.withProperty(FALLING, true));
                ((TEStump) world.getTileEntity(pos.down()))
                        .fall(player.getHorizontalFacing().rotateY());
            }
            
            player.addExhaustion(0.005F);
            
        } else {
            
            super.harvestBlock(world, player, pos, state, te, tool);
        }
    }

    @Override
    public final List<ItemStack> getDrops(IBlockAccess world,
            BlockPos pos, IBlockState state, int fortune) {
        
        if (this.trunk == null) {
            
            return Lists.newArrayList(new ItemStack(GeoItems.TIMBER)); //drops
        }
        
        else return Collections.emptyList();
    }
}
