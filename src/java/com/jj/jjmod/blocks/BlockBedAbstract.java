package com.jj.jjmod.blocks;

import java.util.function.Supplier;
import com.jj.jjmod.tileentities.TEBed;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import com.sun.istack.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for Bed blocks. */
public abstract class BlockBedAbstract extends BlockHorizontal {

    public static final PropertyEnum<EnumPartBed> PART =
            PropertyEnum.<EnumPartBed>create("part", EnumPartBed.class);
    public static final PropertyBool OCCUPIED = BlockBed.OCCUPIED;

    /** This bed's item. */
    protected Supplier<Item> itemRef;
    /** The amount to heal the player when sleeping in this bed. */
    protected float healAmount;

    public BlockBedAbstract(String name, float hardness, float healAmount,
            Supplier<Item> itemRef, ToolType harvestTool) {

        super(BlockMaterial.WOOD_HANDHARVESTABLE);
        BlockNew.setupBlock(this, name, null, hardness, harvestTool);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(PART, EnumPartBed.FOOT)
                .withProperty(OCCUPIED, false));
        this.itemRef = itemRef;
        this.healAmount = healAmount;
    }
    
    /** Drops this bed's item with damage if applicable. */
    protected abstract void dropItem(World world, BlockPos pos, TEBed te);
    
    /** @return The amount the player heals sleeping in this Bed. */
    public float getHealAmount() {
        
        return this.healAmount;
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

                this.dropItem(world, pos, (TEBed) world.getTileEntity(pos));
                world.setBlockToAir(pos);
            }
        }
    }
    
    /** Breaks this block and drops its item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);
        
        if (state.getValue(PART) == EnumPartBed.FOOT) {
            
            this.dropItem(world, pos, (TEBed) te);
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
    protected BlockStateContainer createBlockState() {

        return new BlockStateContainer(this,
                new IProperty[] {FACING, PART, OCCUPIED});
    }

    /** Needed for Forge bed implementations. */
    @Override
    public boolean isBed(IBlockState state, IBlockAccess world,
            BlockPos pos, Entity player) {

        return true;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return false;
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
