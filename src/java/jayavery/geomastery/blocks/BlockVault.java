/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Single vault block. */
public class BlockVault extends BlockFacing {
    
    public static final PropertyEnum<EVaultShape> SHAPE = PropertyEnum.<EVaultShape>create("shape", EVaultShape.class);
    
    public BlockVault(String name, Material material, EBlockWeight weight,
            int stackSize) {
        
        super(name, material, 2F, stackSize, weight);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direcion) {
        
        return false;
    }

    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (!alreadyPresent && !world.getBlockState(pos).getBlock()
                .isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        EnumFacing thisFacing = setState.getValue(FACING);
        EBlockWeight thisWeight = this.getWeight(setState);
        IBlockState stateSupport = world.getBlockState(pos.offset(thisFacing));
        EBlockWeight weightSupport = EBlockWeight.getWeight(stateSupport);
        Block blockSupport = stateSupport.getBlock();
        
        if (!weightSupport.canSupport(thisWeight)) {
            
            message(player, Lang.BUILDFAIL_VAULT);
            return false;
        }
        
        if (blockSupport instanceof BlockVault) {
        
            IBlockState stateLeft = world.getBlockState(pos
                    .offset(thisFacing.rotateYCCW()));
            EBlockWeight weightLeft = EBlockWeight.getWeight(stateLeft);
            IBlockState stateRight = world.getBlockState(pos
                    .offset(thisFacing.rotateY()));
            EBlockWeight weightRight = EBlockWeight.getWeight(stateRight);
            
            if (!weightLeft.canSupport(thisWeight) &&
                    !weightRight.canSupport(thisWeight)) {
                
                message(player, Lang.BUILDFAIL_VAULT);
                return false;
            }
        }
        
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        EnumFacing facing = state.getValue(FACING);
        IBlockState stateFront = world
                .getBlockState(pos.offset(facing));
        IBlockState stateLeft = world
                .getBlockState(pos.offset(facing.rotateYCCW()));
        IBlockState stateRight = world
                .getBlockState(pos.offset(facing.rotateY()));
        IBlockState stateBack = world
                .getBlockState(pos.offset(facing.getOpposite()));
        
        Block blockFront = stateFront.getBlock();
        Block blockLeft = stateLeft.getBlock();
        Block blockRight = stateRight.getBlock();
        
        boolean supFront = this.isSupport(stateFront);
        boolean supLeft = this.isSupport(stateLeft);
        boolean supRight = this.isSupport(stateRight);
        boolean supBack = this.isSupport(stateBack);
        
        if (supFront && supBack) {
            
            return state.withProperty(SHAPE, EVaultShape.LINTEL);
        }
        
        if (blockFront instanceof BlockVault) {
                        
            if (blockLeft instanceof BlockVault &&
                    !(blockRight instanceof BlockVault)) {
                
                return state.withProperty(SHAPE, EVaultShape.INTERNAL_LEFT);
            }
            
            if (blockRight instanceof BlockVault &&
                    !(blockLeft instanceof BlockVault)) {
                
                return state.withProperty(SHAPE, EVaultShape.INTERNAL_RIGHT);
            }
        }
        
        if (supFront && supLeft && !supRight) {
            
            return state.withProperty(SHAPE, EVaultShape.EXTERNAL_LEFT);
        }
        
        if (supFront && !supLeft && supRight) {
            
            return state.withProperty(SHAPE, EVaultShape.EXTERNAL_RIGHT);
        }
        
        return state.withProperty(SHAPE, EVaultShape.SINGLE);
    
    }

    /** @return Whether the given blockstate can support a vault. */
    private boolean isSupport(IBlockState state) {
        
        Block block = state.getBlock();
        return EBlockWeight.getWeight(state).canSupport(this.weight) &&
                !(block instanceof BlockVault) && !(block instanceof BlockBeam);
    }

    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, SHAPE, FACING);
    }

    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.textVisual.buildTooltips) {
            
            tooltip.add(I18n.format(Lang.BUILDTIP_VAULT));
            tooltip.add(I18n.format(this.weight.requires()));
            tooltip.add(I18n.format(this.weight.supports()));
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        return state.getValue(SHAPE) == EVaultShape.LINTEL ?
                FULL_BLOCK_AABB : TOP_HALF;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        int facing = state.getValue(FACING).getHorizontalIndex();
        EVaultShape shape = state.getValue(SHAPE);
        
        AxisAlignedBB[] boxes;
        
        switch (shape) {
            
            case SINGLE:
                boxes = VAULT_STRAIGHT[facing];
                break;
            case INTERNAL_RIGHT:
                boxes = VAULT_INTERNAL[facing];
                break;
            case INTERNAL_LEFT:
                boxes = VAULT_INTERNAL[(facing + 3) % 4];
                break;
            case EXTERNAL_RIGHT:
                boxes = VAULT_EXTERNAL[(facing + 1) % 4];
                break;
            case EXTERNAL_LEFT:
                boxes = VAULT_EXTERNAL[facing];
                break;
            case LINTEL: default:
                boxes = new AxisAlignedBB[] {FULL_BLOCK_AABB};
                break;
        }
        
        for (AxisAlignedBB box : boxes) {
            
            addCollisionBoxToList(pos, entityBox, list, box);
        }
    }
    
    /** Enum defining possible shapes for the vault. */
    public enum EVaultShape implements IStringSerializable {
        
        SINGLE("single"), LINTEL("lintel"),
        INTERNAL_LEFT("internalleft"), INTERNAL_RIGHT("internalright"),
        EXTERNAL_LEFT("externalleft"), EXTERNAL_RIGHT("externalright");
        
        private String name;
        
        private EVaultShape(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
    }
}
