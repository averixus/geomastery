package com.jayavery.jjmod.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.tileentities.TEBed;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
public abstract class BlockBed extends BlockBuilding {

    public static final PropertyEnum<EnumPartBed> PART =
            PropertyEnum.<EnumPartBed>create("part", EnumPartBed.class);
    public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    /** This bed's item. */
    protected Supplier<Item> itemRef;
    /** The amount to heal the player when sleeping in this bed. */
    protected float healAmount;

    public BlockBed(String name, float hardness, float healAmount,
            Supplier<Item> itemRef) {

        super(BlockMaterial.WOOD_HANDHARVESTABLE, name, null, hardness, null);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(PART, EnumPartBed.FOOT)
                .withProperty(OCCUPIED, false));
        this.itemRef = itemRef;
        this.healAmount = healAmount;
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
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
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
    
    @Override
    public abstract AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos);
    
    /** The player sleeps in this bed if possible. */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y,
            float z) {

        if (world.isRemote) {
            
            return true;
        }

        if (state.getValue(PART) != EnumPartBed.HEAD) {

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
                        "tile.bed.occupied", new Object[0]));
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
                    "tile.bed.noSleep", new Object[0]));
            return true;
        }

        if (sleepable == EntityPlayer.SleepResult.NOT_SAFE) {

            player.sendMessage(new TextComponentTranslation(
                    "tile.bed.notSafe", new Object[0]));
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

    /** Checks if this block is part of a whole
     * bed structure, removes it if not. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        EnumFacing facing = state.getValue(FACING);

        if (state.getValue(PART) == EnumPartBed.HEAD) {

            if (world.getBlockState(pos.offset(facing.getOpposite()))
                    .getBlock() != this) {

                world.setBlockToAir(pos);
            }
            
        } else {

            if (world.getBlockState(pos.offset(facing))
                    .getBlock() != this) {

                Item item = this.itemRef.get();
                ItemStack drop;
                TEBed bed = (TEBed) world.getTileEntity(pos);
                
                if (bed.isUndamaged()) {
                    
                    drop = new ItemStack(item);
                    
                } else {
                     
                    drop = new ItemStack(item, 1,
                            item.getMaxDamage() - bed.getUsesLeft());
                }
                
                spawnAsEntity(world, pos, drop);
                world.setBlockToAir(pos);
            }
        }
    }
    
    /** Drops are handled using TE. */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Collections.emptyList();
    }
    
    /** Breaks this block and drops its item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);
        
        if (state.getValue(PART) == EnumPartBed.FOOT) {
            
            Item item = this.itemRef.get();
            ItemStack drop;
            TEBed bed = (TEBed) te;
            
            if (bed.isUndamaged()) {
                
                drop = new ItemStack(item);
                
            } else {
                 
                drop = new ItemStack(item, 1,
                        item.getMaxDamage() - bed.getUsesLeft());
            }
            
            spawnAsEntity(world, pos, drop);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing facing = EnumFacing.getHorizontal(meta);
        IBlockState state = this.getDefaultState();

        if ((meta & 8) > 0) {

            state = state.withProperty(PART, EnumPartBed.HEAD);
            state = state.withProperty(FACING, facing);
            state = state.withProperty(OCCUPIED, (meta & 4) > 0);
            
        } else {

            state = state.withProperty(PART, EnumPartBed.FOOT);
            state = state.withProperty(FACING, facing);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        int i = 0;

        i = i | state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(PART) == EnumPartBed.HEAD) {

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

        if (state.getValue(PART) == EnumPartBed.FOOT) {

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
    
    public static class Simple extends BlockBed {

        public Simple() {
            
            super("bed_simple", 2F, 2F, () -> ModItems.bedSimple);
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
    
    public static class Leaf extends BlockBed {
        
        public Leaf() {
            
            super("bed_leaf", 0.2F, 0.33F, () -> Items.AIR);
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
    }
    
    public static class Wool extends BlockBed {
        
        public Wool() {
            
            super("bed_wool", 2F, 0.66F, () -> ModItems.bedWool);
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
    
    public static class Cotton extends BlockBed {
        
        public Cotton() {
            
            super("bed_cotton", 2F, 0.66F, () -> ModItems.bedCotton);
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
    public static enum EnumPartBed implements IStringSerializable {

        HEAD("head"),
        FOOT("foot");

        private final String name;

        private EnumPartBed(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
