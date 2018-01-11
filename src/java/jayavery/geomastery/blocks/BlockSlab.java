/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Paving slab block. */
public class BlockSlab extends BlockBuildingAbstract<ItemPlacing.Building>
        implements IDoublingBlock {
    
    public static final PropertyBool DOUBLE = PropertyBool.create("double");

    public BlockSlab(String name) {
        
        super(BlockMaterial.STONE_FURNITURE, name,
                CreativeTabs.BUILDING_BLOCKS, 2F, 4);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }

    @Override
    public EBlockWeight getWeight(IBlockState state) {

        return this.isDouble(state) ? EBlockWeight.LIGHT : EBlockWeight.NONE;
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return !this.isDouble(state) && side == EnumFacing.UP;
    }

    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (!alreadyPresent && !world.getBlockState(pos).getBlock()
                .isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
        
        if (!weightBelow.canSupport(EBlockWeight.MEDIUM)) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        return true;
    }

    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
        
        IBlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();
    
        if (targetBlock == this && this.shouldDouble(targetState, targetSide)) {
            
            world.setBlockState(targetPos,
                    targetState.withProperty(DOUBLE, true));
    
        } else {
            
            targetPos = targetPos.offset(targetSide);
            targetState = world.getBlockState(targetPos);
            targetBlock = targetState.getBlock();
            
            IBlockState state = this.getDefaultState()
                    .withProperty(DOUBLE, false);
            
            if (!this.isValid(world, targetPos, stack, false, state, player)) {
                
                return false;
            }
            
            world.setBlockState(targetPos, state);
        }
        
        return true;
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
    public BlockStateContainer createBlockState() {
    
        return new BlockStateContainer(this, DOUBLE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(DOUBLE) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(DOUBLE, meta > 0);
    }

    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.textVisual.buildTooltips) {
            
            tooltip.add(I18n.format(Lang.BUILDTIP_DOUBLING));                
            tooltip.add(I18n.format(EBlockWeight.MEDIUM.requires()));
            tooltip.add(I18n.format(Lang.BUILDTIP_SLAB));
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return state.getValue(DOUBLE) ? FULL_BLOCK_AABB : EIGHT;
    }
}
