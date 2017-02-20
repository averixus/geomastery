package com.jj.jjmod.blocks;

import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.blocks.BlockBedAbstract.EnumPartBed;
import com.jj.jjmod.tileentities.TEBed;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Abstract superclass for Bed blocks. */
public abstract class BlockBedAbstract extends BlockHorizontal {

    public static final PropertyEnum<EnumPartBed> PART =
            PropertyEnum.<EnumPartBed>create("part", EnumPartBed.class);
    public static final PropertyBool OCCUPIED =
            PropertyBool.create("occupied");

    protected static final AxisAlignedBB BED_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);
    protected static final AxisAlignedBB FLAT_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.18D, 1.0D);

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
    protected abstract void drop(World world, BlockPos pos, TEBed te);
    
    /** @return The amount the player heals sleeping in this Bed. */
    public float getHealAmount() {
        
        return this.healAmount;
    }

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

    /** @return The Player currently in this Bed block, null if none. */
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

                this.drop(world, pos, (TEBed) world.getTileEntity(pos));
                world.setBlockToAir(pos);
            }
        }
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);
        
        if (state.getValue(PART) == EnumPartBed.FOOT) {
            
            this.drop(world, pos, (TEBed) te);
        }
    }

    @Nullable
    public static BlockPos getSafeExitLocation(World world,
            BlockPos pos, int tries) {

        EnumFacing facing = world.getBlockState(pos).getValue(FACING);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int i = 0; i <= 1; ++i) {

            int x1 = x - facing.getFrontOffsetX() * i - 1;
            int z1 = z - facing.getFrontOffsetZ() * i - 1;
            int x2 = x1 + 2;
            int z2 = z1 + 2;

            for (int j = x1; j <= x2; ++j) {

                for (int k = z1; k <= z2; ++k) {

                    BlockPos newPos = new BlockPos(j, y, k);

                    if (hasRoomForPlayer(world, newPos)) {

                        if (tries <= 0) {

                            return newPos;
                        }

                        tries--;
                    }
                }
            }
        }

        return null;
    }

    /** @return Whether the given pos has space around it for a player. */
    private static boolean hasRoomForPlayer(World world, BlockPos pos) {

        boolean hasFloorSpace = world.getBlockState(pos.down())
                .isSideSolid(world, pos, EnumFacing.UP);
        boolean hasFeetSpace = !world.getBlockState(pos)
                .getMaterial().isSolid();
        boolean hasHeadSpace = !world.getBlockState(pos.up())
                .getMaterial().isSolid();

        return hasFloorSpace && hasFeetSpace && hasHeadSpace;
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
    public IBlockState withRotation(IBlockState state, Rotation rot) {

        EnumFacing facing = rot.rotate(state.getValue(FACING));
        return state.withProperty(FACING, facing);
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {

        Rotation rot = mirror.toRotation(state.getValue(FACING));
        return state.withRotation(rot);
    }

    @Override
    protected BlockStateContainer createBlockState() {

        return new BlockStateContainer(this,
                new IProperty[] {FACING, PART, OCCUPIED});
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world,
            BlockPos pos, Entity player) {

        return true;
    }

    @Override
    public BlockPos getBedSpawnPosition(IBlockState state,
            IBlockAccess world, BlockPos pos, EntityPlayer player) {

        if (world instanceof World) {

            return getSafeExitLocation((World) world, pos, 0);
        }

        return null;
    }

    @Override
    public void setBedOccupied(IBlockAccess world, BlockPos pos,
            EntityPlayer player, boolean occupied) {

        if (world instanceof World) {

            IBlockState state = world.getBlockState(pos);
            state = state.getBlock().getActualState(state, world, pos);
            state = state.withProperty(OCCUPIED, true);
            ((World) world).setBlockState(pos, state, 4);
        }
    }

    @Override
    public EnumFacing getBedDirection(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return getActualState(state, world, pos).getValue(FACING);
    }

    @Override
    public boolean isBedFoot(IBlockAccess world, BlockPos pos) {

        EnumPartBed part = getActualState(world.getBlockState(pos),
                world, pos).getValue(PART);
        return part == EnumPartBed.FOOT;
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
        public String toString() {

            return this.name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
