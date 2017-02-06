package com.jj.jjmod.items;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jj.jjmod.utilities.BlockMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemBucket extends ItemNew {

    protected final Block CONTENTS;
    protected Supplier<Item> empty;
    protected Supplier<Item> water;
    protected Supplier<Item> tar;

    public ItemBucket(String name, Block contents, Supplier<Item> empty,
            Supplier<Item> water, Supplier<Item> tar) {

        super(name, 1, CreativeTabs.MISC);
        this.CONTENTS = contents;
        this.empty = empty;
        this.water = water;
        this.tar = tar;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        System.out.println("item right click bucket");
        boolean empty = this.CONTENTS == Blocks.AIR;
        ItemStack stack = player.getHeldItem(hand);
        RayTraceResult rayTrace = this.rayTrace(world, player, empty);
        ActionResult<ItemStack> result = net.minecraftforge.event
                .ForgeEventFactory.onBucketUse(player, world, stack, rayTrace);

        if (result != null) {
            System.out.println("result from event " + result);
            return result;
        }

        if (rayTrace == null ||
                rayTrace.typeOfHit != RayTraceResult.Type.BLOCK) {
            System.out.println("not a block " + rayTrace.typeOfHit);
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }

        BlockPos posTarget = rayTrace.getBlockPos();
        System.out.println("target pos " + posTarget);
        if (!world.isBlockModifiable(player, posTarget) ||
                !player.canPlayerEdit(posTarget.offset(rayTrace.sideHit),
                rayTrace.sideHit, stack)) {
            System.out.println("can't edit/modify position");
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }

        if (empty) {
            System.out.println("bucket is empty");
            IBlockState state = world.getBlockState(posTarget);
            Material material = state.getMaterial();

            if (material == Material.WATER &&
                    (state.getValue(BlockLiquid.LEVEL)) == 0) {
                System.out.println("target material is water");
                world.setBlockState(posTarget,
                        Blocks.AIR.getDefaultState(), 11);
                player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1, 1);
                System.out.println("filled with water");
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,
                        this.fillBucket(stack, player, this.getWater()));
            }

            if (material == BlockMaterial.TAR &&
                    (state.getValue(BlockLiquid.LEVEL)) == 0) {
                System.out.println("target material is tar");
                world.setBlockState(posTarget,
                        Blocks.AIR.getDefaultState(), 11);
                player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1, 1);
                System.out.println("filled with tar");
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,
                        this.fillBucket(stack, player, this.getTar()));
            }

        } else {
            System.out.println("bucket is not empty");
            boolean replaceable = world.getBlockState(posTarget).getBlock()
                    .isReplaceable(world, posTarget);
            BlockPos posPlace = replaceable && rayTrace.sideHit == EnumFacing.UP
                    ? posTarget : posTarget.offset(rayTrace.sideHit);
            
            if (!player.canPlayerEdit(posPlace,
                    rayTrace.sideHit, stack)) {
                System.out.println("can't edit position");
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
            }

            if (this.tryPlaceContainedLiquid(player, world, posPlace)) {
                System.out.println("succeeded placing liquid");
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,
                        new ItemStack(this.getEmpty()));
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }

    private ItemStack fillBucket(ItemStack emptyBuckets,
            EntityPlayer player, Item fullBucket) {

        if (emptyBuckets.getCount() <= 1) {

            return new ItemStack(fullBucket);

        } else {

            if (!player.inventory.addItemStackToInventory(
                    new ItemStack(fullBucket))) {

                player.dropItem(new ItemStack(fullBucket), false);
            }

            emptyBuckets.shrink(1);
            
            if (emptyBuckets.getCount() == 0) {
                
                emptyBuckets = null;                
            }
            
            return emptyBuckets;
        }
    }

    public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player,
            World world, BlockPos pos) {
        System.out.println("trying to place contents " + this.CONTENTS);
        if (this.CONTENTS == Blocks.AIR) {

            return false;
        }

        IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();
        boolean notSolid = !material.isSolid();
        boolean replaceable = state.getBlock().isReplaceable(world, pos);

        if (!world.isAirBlock(pos) && !notSolid && !replaceable) {
            System.out.println("not air or replaceable");
            return false;

        }

        if (world.provider.doesWaterVaporize() &&
                this.CONTENTS == Blocks.FLOWING_WATER) {

            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH,
                    SoundCategory.BLOCKS, 0.5F, 2.6F +
                    (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

            for (int i = 0; i < 8; i++) {

                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        x + Math.random(), y + Math.random(),
                        z + Math.random(), 0, 0, 0, 0);
            }

        } else {

            if (!world.isRemote && (notSolid || replaceable) && !material
                    .isLiquid()) {

                world.destroyBlock(pos, true);
            }

            SoundEvent sound = SoundEvents.ITEM_BUCKET_EMPTY;
            world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1, 1);
            System.out.println("setting state to contents " + this.CONTENTS);
            world.setBlockState(pos, this.CONTENTS.getDefaultState(), 11);
            System.out.println("finished setting state to contents " + world.getBlockState(pos));
        }

        return true;
    }

    public Item getEmpty() {

        return this.empty.get();
    }

    public Item getWater() {

        return this.water.get();
    }

    public Item getTar() {

        return this.tar.get();
    }
}