/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import java.util.Random;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Flat breakable roof block. */
public class BlockFlatroof extends BlockFacing {

    public BlockFlatroof(String name, float hardness) {
        
        super(name, BlockMaterial.WOOD_FURNITURE, hardness,
                4, EBlockWeight.NONE);
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
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
        
        if (weightBelow.canSupport(this.getWeight(stateBelow))) {
            
            return true;
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            int distance = 1;
            
            while (distance <= 2) {
                
                BlockPos offset = pos.offset(facing, distance);
                
                if (this.isValidSupport(world, offset)) {
                    
                    return true;
                }
                
                if (!(world.getBlockState(offset).getBlock()
                        instanceof BlockFlatroof)) {
                    
                    break;
                }
                
                distance++;
            }
        }
        
        message(player, Lang.BUILDFAIL_FLATROOF);
        return false;
    }

    /** @return Whether this position can support adjacent roof. */
    protected boolean isValidSupport(World world, BlockPos pos) {
        
        boolean isRoof = world.getBlockState(pos).getBlock()
                instanceof BlockFlatroof;
        boolean isSupported = EBlockWeight.getWeight(world.getBlockState(pos
                .down())).canSupport(this.getWeight(this.getDefaultState()));
        return isRoof && isSupported;
    }

    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.buildTooltips) {
            
            tooltip.add(I18n.format(Lang.BUILDTIP_FLATROOF));
            tooltip.add(I18n.format(EBlockWeight.NONE.supports()));
        }
    }
    
    // Make drips when raining (as vanilla leaves)
    @SideOnly(Side.CLIENT) @Override
    public void randomDisplayTick(IBlockState stateIn, World world,
            BlockPos pos, Random rand) {
        
        if (world.isRainingAt(pos.up()) && !world.getBlockState(pos.down())
                .isSideSolid(world, pos.down(), EnumFacing.UP) &&
                rand.nextInt(15) == 1) {
            
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() - 0.05D;
            double d2 = pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2,
                    0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return TWO;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return NULL_AABB;
    }

    // Break when walked on
    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos,
            IBlockState state, Entity entity) {
        
        if (entity instanceof EntityLivingBase &&
                !(entity instanceof EntityAmbientCreature)) {
            
            EntityLivingBase living = (EntityLivingBase) entity;
            BlockPos feetPos = new BlockPos(living.posX,
                    living.getEntityBoundingBox().minY + 0.5D, living.posZ);

            if (feetPos.equals(pos) && !EBlockWeight.getWeight(world
                    .getBlockState(pos.down()))
                    .canSupport(this.getWeight(state))) {
            
                world.destroyBlock(pos, true);
            }
        }
    }
}
