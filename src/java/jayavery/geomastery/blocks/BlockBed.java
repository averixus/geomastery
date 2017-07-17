/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.tileentities.TEBed;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Bed blocks. */
public abstract class BlockBed
        extends BlockBuildingAbstract<ItemPlacing.Building> {

    public static final PropertyEnum<EPartBed> PART =
            PropertyEnum.<EPartBed>create("part", EPartBed.class);
    public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    /** The amount to heal the player when sleeping in this bed. */
    protected final float healAmount;

    public BlockBed(String name, float hardness, float healAmount) {

        super(BlockMaterial.WOOD_FURNITURE, name, null, hardness, 1);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(PART, EPartBed.FOOT)
                .withProperty(OCCUPIED, false));
        this.healAmount = healAmount;
    }
    
    @Override
    public abstract AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos);
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        ItemPlacing.Building item = new ItemPlacing.Building(this, stackSize);
        item.setMaxDamage(TEBed.MAX_USES);
        return item;
    }

    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
        
        BlockPos posFoot = targetPos.offset(targetSide);
        BlockPos posHead = posFoot.offset(placeFacing);
        IBlockState placeFoot = this.getDefaultState()
                .withProperty(BlockBed.FACING, placeFacing);
        IBlockState placeHead = placeFoot
                .withProperty(BlockBed.PART, EPartBed.HEAD);
        boolean validFoot = this.isValid(world, posFoot, stack, false,
                placeFoot, player);
        boolean validHead = this.isValid(world, posHead, stack, false,
                placeHead, player);
        
        if (!validFoot || !validHead) {
            
            return false;
        }
        
        world.setBlockState(posHead, placeHead);
        world.setBlockState(posFoot, placeFoot);
        
        int usesLeft = stack.getMaxDamage() - stack.getItemDamage();
        TEBed bedTE = (TEBed) world.getTileEntity(posFoot);
        bedTE.setUsesLeft(usesLeft);
        
        return true;
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (alreadyPresent) {
        
            IBlockState state = world.getBlockState(pos);
            EnumFacing facing = state.getValue(FACING);
            
            BlockPos otherPos = state.getValue(PART) == EPartBed.HEAD ?
                    pos.offset(facing.getOpposite()) : pos.offset(facing);
            IBlockState otherState = world.getBlockState(otherPos);
            
            if (otherState.getBlock() != this) {
    
                    return false;
            }
        }
        
        return super.isValid(world, pos, stack, alreadyPresent,
                setState, player);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {
        
        if (te instanceof TEBed && state.getValue(PART) == EPartBed.FOOT) {
            
            TEBed bed = (TEBed) te;
            
            if (bed.isUndamaged()) {
                
                return Lists.newArrayList(new ItemStack(this.item));
                
            } else {
                
                return Lists.newArrayList(new ItemStack(this.item, 1,
                        this.item.getMaxDamage() - bed.getUsesLeft()));
            }
        }
        
        return Collections.emptyList();
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TEBed();
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.NONE;
    }
    
    /** @return The amount the player heals sleeping in this Bed. */
    public float getHealAmount() {
        
        return this.healAmount;
    }
    
    /** Does nothing except special cases (leaf). */
    public void onMorning(World world, BlockPos pos) {}
    
    /** Adds durability by default. */
    public void onWakeup(World world, BlockPos pos, TEBed bed) {
        
        bed.addUse();

        if (bed.isBroken()) {

            world.setBlockToAir(pos);
        }
    }
    
    /** The player sleeps in this bed if possible. */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y,
            float z) {

        if (world.isRemote) {
            
            return true;
        }

        if (state.getValue(PART) != EPartBed.HEAD) {

            pos = pos.offset(state.getValue(FACING));
            state = world.getBlockState(pos);

            if (state.getBlock() != this) {

                return true;
            }
        }

        if (state.getValue(OCCUPIED)) {

            EntityPlayer sleeper = this.getPlayerInBed(world, pos);

            if (sleeper != null) {
                
                player.sendMessage(new TextComponentTranslation(
                        "tile.bed.occupied"));
                return true;
            }

            state = state.withProperty(OCCUPIED, false);
            world.setBlockState(pos, state, 4);
        }

        EntityPlayer.SleepResult sleepable = player.trySleep(pos);

        if (sleepable == EntityPlayer.SleepResult.OK) {

            state = state.withProperty(OCCUPIED, true);
            world.setBlockState(pos, state, 4);
            return true;
        }

        if (sleepable == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {

            player.sendMessage(new TextComponentTranslation(
                    "tile.bed.noSleep"));
            return true;
        }

        if (sleepable == EntityPlayer.SleepResult.NOT_SAFE) {

            player.sendMessage(new TextComponentTranslation(
                    "tile.bed.notSafe"));
            return true;
        }

        return false;
    }

    /** @return The player currently in this bed block, null if none. */
    private EntityPlayer getPlayerInBed(World world, BlockPos pos) {

        for (EntityPlayer player: world.playerEntities) {

            if (player.isPlayerSleeping() && player.bedLocation
                    .equals(pos)) {

                return player;
            }
        }

        return null;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing facing = EnumFacing.getHorizontal(meta);
        IBlockState state = this.getDefaultState();

        if ((meta & 8) > 0) {

            state = state.withProperty(PART, EPartBed.HEAD);
            state = state.withProperty(FACING, facing);
            state = state.withProperty(OCCUPIED, (meta & 4) > 0);
            
        } else {

            state = state.withProperty(PART, EPartBed.FOOT);
            state = state.withProperty(FACING, facing);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        int i = 0;

        i = i | state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(PART) == EPartBed.HEAD) {

            i |= 8;

            if (state.getValue(OCCUPIED)) {

                i |= 4;
            }
        }

        return i;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        if (state.getValue(PART) == EPartBed.FOOT) {

            IBlockState thisState = world.getBlockState(pos
                    .offset(state.getValue(FACING)));

            if (thisState.getBlock() == this) {

                state = state.withProperty(OCCUPIED,
                        thisState.getValue(OCCUPIED));
            }
        }

        return state;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, FACING, PART, OCCUPIED);
    }

    /** Needed for Forge bed implementations. */
    @Override
    public boolean isBed(IBlockState state, IBlockAccess world,
            BlockPos pos, Entity player) {

        return true;
    }
    
    /** Simple solid bed. */
    public static class Simple extends BlockBed {

        public Simple() {
            
            super("bed_simple", 2F, 2F);
        }

        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {

            return SIX;
        }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {

            return EIGHT;
        }
        
        @Override
        public void onWakeup(World world, BlockPos pos, TEBed bed) {}
    }
    
    /** Single-use leaf bed. */
    public static class Leaf extends BlockBed {
        
        public Leaf() {
            
            super("bed_leaf", 0.2F, 0.33F);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return FOUR;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return NULL_AABB;
        }
        
        @Override
        public void onMorning(World world, BlockPos pos) {
            
            world.setBlockToAir(pos);
        }
        
        @Override
        public void onWakeup(World world, BlockPos pos, TEBed bed) {
            
            world.setBlockToAir(pos);
        }
        
        @Override
        public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
                IBlockState state, int fortune, TileEntity te,
                ItemStack tool, EntityPlayer player) {
            
            return Collections.emptyList();
        }
    }
    
    /** Wool bedroll. */
    public static class Wool extends BlockBed {
        
        public Wool() {
            
            super("bed_wool", 2F, 0.66F);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return SIX;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return FOUR;
        }
    }
    
    /** Cotton bedroll. */
    public static class Cotton extends BlockBed {
        
        public Cotton() {
            
            super("bed_cotton", 2F, 0.66F);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return TWO;
        }
        
        @Override
        public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            return TWO;
        }
    }
    
    /** Enum defining parts of the whole Bed structure. */
    public static enum EPartBed implements IStringSerializable {

        HEAD("head"), FOOT("foot");

        private final String name;

        private EPartBed(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
