package com.jayavery.jjmod.entities;

import java.util.List;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

/** Moving block entity for falling trees. */
public abstract class FallingTreeBlock extends Entity
        implements IEntityAdditionalSpawnData {
    
    /** Maximum damage done when landing on an entity. */
    protected static final int MAX_DAMAGE = 40;
    /** Damage per block fallen when landing on an entity. */
    protected static final float DAMAGE = 2.0F;
    
    /** State of the falling block. */
    protected IBlockState blockState;
    /** Number of ticks the block has been falling. */
    protected int fallTime;
    /** Height of this block above the falling start position. */
    protected int treeHeight;
    /** Horiztonal direction this block falls in. */
    protected EnumFacing direction;

    public FallingTreeBlock(World world) {

        super(world);
    }

    public FallingTreeBlock(World world, BlockPos pos, EnumFacing direction,
            IBlockState blockState, int treeHeight) {
        
        super(world);
        this.blockState = blockState;
        this.preventEntitySpawning = true;
        this.treeHeight = treeHeight;
        this.setSize(0.98F, 0.98F);
        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        this.setPosition(x, y + (1.0F - this.height) / 2.0F, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        
        // Chance of randomising direction
        if (world.rand.nextInt(10) == 0) {
            
            this.direction = EnumFacing.HORIZONTALS[world.rand.nextInt(4)];
            
        } else {
        
            this.direction = direction;
        }
    }
    
    /** @return The blockstate of this falling block. */
    public IBlockState getBlockState() {

        return this.blockState;
    }
    
    @Override
    public void onUpdate() {

        Block block = this.blockState.getBlock();
            
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.fallTime++ == 0) {
            
            BlockPos blockpos = new BlockPos(this);

            if (this.world.getBlockState(blockpos).getBlock() == block) {
                
                this.world.setBlockToAir(blockpos);
                
            } else if (!this.world.isRemote) {
                
                this.setDead();
                return;
            }
        }
            
        this.motionY -= (0.04D);
        this.setHorizontalMotion();
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        
        this.motionX *= 0.98;
        this.motionY *= 0.98;
        this.motionZ *= 0.98;

        if (!this.world.isRemote) {
            
            BlockPos pos = new BlockPos(this);

            if (this.onGround) {
                
                if (this.canFallThrough(pos.down())) {
                    
                    this.onGround = false;
                    return;
                }
                    
                this.setDead();

                BlockPos landing = this.findLanding(pos);
                    
                if (landing != null) {

                    this.world.setBlockState(landing,
                            this.blockState, 3);

                } else {

                    block.dropBlockAsItem(this.world, pos,
                            this.blockState, 0);
                }
                
            } else if (this.fallTime > 100 && !this.world.isRemote
                    && (pos.getY() < 1 || pos.getY() > 256) ||
                    this.fallTime > 600) {

                block.dropBlockAsItem(this.world, pos,
                        this.blockState, 0);
                this.setDead();
            }
        }
    }
    
    /** Sets this entity's horizontal motion according to direction. */
    @SuppressWarnings("incomplete-switch")
    private void setHorizontalMotion() {
        
        double magnitude = ((this.world.rand.nextDouble() / 2) + 0.75) *
                (0.07D * this.treeHeight);
        
        switch (this.direction) {
            
            case NORTH :
                this.motionZ = -magnitude;
                break;
            case EAST :
                this.motionX = magnitude;
                break;
            case SOUTH :
                this.motionZ = magnitude;
                break;
            case WEST :
                this.motionX = -magnitude;
                break;
        }
    }

    /** @return A valid neabry landing position for this block. */
    private BlockPos findLanding(BlockPos pos) {

        BlockPos test1 = pos.offset(this.direction);
        BlockPos test2 = pos.offset(this.direction.getOpposite());
        BlockPos test3 = pos.up();
        
        if (this.canFallThrough(pos)) {
            
            return pos;
            
        } else if (this.canFallThrough(test1)) {

            return test1;

        } else if (this.canFallThrough(test2)) {

            return test2;

        } else if (this.canFallThrough(test3)) {
            
            return test3;
            
        } else {

            return null;
        }
    }
    
    /** @return Whether this falling block can pass through the given pos. */
    protected abstract boolean canFallThrough(BlockPos pos);
    
    /** Does damage to other entities when fallen on. */
    @Override public abstract void fall(float distance, float damageMultiplier);
    
    /** @return Whether other entities can collide with this. */
    @Override public abstract boolean canBeCollidedWith();
    
    /** Falling block entity for trunk blocks. */
    public static class Trunk extends FallingTreeBlock {
        
        public Trunk(World world) {
            
            super(world);
        }
        
        @SuppressWarnings("incomplete-switch")
        public Trunk(World world, BlockPos pos, EnumFacing direction,
                IBlockState blockState, int treeHeight) {
            
            super(world, pos, direction, blockState, treeHeight);
            
            switch (this.direction) {
                
                case NORTH :
                case SOUTH :
                    this.blockState = this.blockState
                            .withProperty(BlockLog.LOG_AXIS, EnumAxis.Z);
                    break;
                case EAST :
                case WEST :
                    this.blockState = this.blockState
                            .withProperty(BlockLog.LOG_AXIS, EnumAxis.X);
            }
        }

        @Override
        protected boolean canFallThrough(BlockPos pos) {
            
            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            return block.isReplaceable(this.world, pos) ||
                    block instanceof BlockLeaves;
        }
        
        @Override
        public void fall(float distance, float damageMultiplier) {
            
            int i = MathHelper.ceil(distance - 1.0F);

            if (i > 0) {
                
                List<Entity> list = Lists.newArrayList(this.world
                        .getEntitiesWithinAABBExcludingEntity(this, this
                        .getEntityBoundingBox()));

                for (Entity entity : list) {
                    
                    entity.attackEntityFrom(DamageSource.FALLING_BLOCK,
                            Math.min(MathHelper.floor(i * DAMAGE), MAX_DAMAGE));
                }
            }
        }
        
        @Override
        public boolean canBeCollidedWith() {
            
            return !this.isDead;
        }
        
        /** Gets collision boxes excluding leaf blocks.
         * Almost exact copy from vanilla. */
        public void getCollisionBoxes(World world, AxisAlignedBB entityBox,
                List<AxisAlignedBB> list) {
            
            list.clear();
            
            // Get block bounding boxes excluding leaves
            int minX = MathHelper.floor(entityBox.minX) - 1;
            int maxX = MathHelper.ceil(entityBox.maxX) + 1;
            int minY = MathHelper.floor(entityBox.minY) - 1;
            int maxY = MathHelper.ceil(entityBox.maxY) + 1;
            int minZ = MathHelper.floor(entityBox.minZ) - 1;
            int maxZ = MathHelper.ceil(entityBox.maxZ) + 1;
            WorldBorder border = world.getWorldBorder();
            boolean borderCheck = world.func_191503_g(this);
            PooledMutableBlockPos pos = PooledMutableBlockPos.retain();

            try {
                
                for (int x = minX; x < maxX; ++x) {
                    
                    for (int z = minZ; z < maxZ; ++z) {
                        
                        boolean withinX = x == minX || x == maxX - 1;
                        boolean withinZ = z == minZ || z == maxZ - 1;

                        if ((!withinX || !withinZ) &&
                                world.isBlockLoaded(pos.setPos(x, 64, z))) {
                            
                            for (int y = minY; y < maxY; ++y) {
                                
                                if (!withinX && !withinZ || y != maxY - 1) {
                                    
                                    if (this.isOutsideBorder() == borderCheck) {
                                        
                                        this.setOutsideBorder(!borderCheck);
                                    }

                                    pos.setPos(x, y, z);
                                    IBlockState state =
                                            Blocks.STONE.getDefaultState();

                                    if (border.contains(pos) || !borderCheck) {

                                        state = world.getBlockState(pos);
                                    }

                                    if (!(state.getBlock() instanceof
                                            BlockLeaves)) {
                                    
                                        state.addCollisionBoxToList(world, pos,
                                                entityBox, list, this, false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            finally {
                
                pos.release();
            }
            
            // Get entity bounding boxes
            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(
                    this, entityBox.expandXyz(0.25D));

            for (int i = 0; i < entities.size(); ++i) {
                
                Entity entity = entities.get(i);

                if (!this.isRidingSameEntity(entity)) {
                    
                    AxisAlignedBB box = entity.getCollisionBoundingBox();

                    if (box != null && box.intersectsWith(entityBox)) {
                        
                        list.add(box);
                    }

                    box = this.getCollisionBox(entity);

                    if (box != null && box.intersectsWith(entityBox)) {
                        
                        list.add(box);
                    }
                }
            }
        }
    }
    
    /** Falling block entity for leaves blocks. */
    public static class Leaves extends FallingTreeBlock {
        
        public Leaves(World world) {
            
            super(world);
        }
        
        public Leaves(World world, BlockPos pos, EnumFacing direction,
                IBlockState blockState, int treeHeight) {
            
            super(world, pos, direction, blockState, treeHeight);
        }
        
        @Override
        protected boolean canFallThrough(BlockPos pos) {
            
            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            return block.isReplaceable(this.world, pos);
        }
        
        @Override
        public void fall(float distance, float damageMultiplier) {}
        
        @Override
        public boolean canBeCollidedWith() {
            
            return false;
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {

        return false;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

        Block block = this.blockState != null ? this.blockState.getBlock()
                : Blocks.AIR;
        ResourceLocation blockName = Block.REGISTRY.getNameForObject(
                block);
        compound.setString("Block", blockName == null ? ""
                : blockName.toString());
        compound.setByte("Data", (byte) block.getMetaFromState(
                this.blockState));
        compound.setInteger("Time", this.fallTime);
        compound.setInteger("Direction", this.direction.getHorizontalIndex());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

        int i = compound.getByte("Data") & 255;

        this.blockState = Block.getBlockFromName(compound.getString(
                    "Block")).getStateFromMeta(i);
            
        this.fallTime = compound.getInteger("Time");        
        this.direction = EnumFacing
                .getHorizontal(compound.getInteger("Direction"));
    }

    @Override
    protected void entityInit() {}

    @Override
    public void writeSpawnData(ByteBuf buf) {

        Block block = this.blockState.getBlock();
        ByteBufUtils.writeUTF8String(buf, Block.REGISTRY.getNameForObject(
                block).toString());
        buf.writeByte(block.getMetaFromState(this.blockState));
        buf.writeInt(this.direction.getHorizontalIndex());
    }

    @Override
    public void readSpawnData(ByteBuf buf) {

        String name = ByteBufUtils.readUTF8String(buf);
        byte meta = buf.readByte();
        this.blockState = Block.getBlockFromName(name).getStateFromMeta(meta);
        this.direction = EnumFacing.getHorizontal(buf.readInt());
    }
}