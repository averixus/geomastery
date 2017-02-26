package com.jj.jjmod.items;

import java.util.function.Supplier;
import com.jj.jjmod.container.ContainerInventory;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/** Bucket item for tar and water. */
public class ItemBucket extends ItemJj {

    /** This bucket's contents. */
    private final Block contents;
    /** Supplier for the empty version of this bucket. */
    private final Supplier<Item> empty;
    /** Supplier for the water filled version of this bucket. */
    private final Supplier<Item> water;
    /** Supplier for the tar filled version of this bucket. */
    private final Supplier<Item> tar;

    public ItemBucket(String name, Block contents, Supplier<Item> empty,
            Supplier<Item> water, Supplier<Item> tar) {

        super(name, 1, CreativeTabs.MISC);
        this.contents = contents;
        this.empty = empty;
        this.water = water;
        this.tar = tar;
    }

    /** Fills or empties if possible. */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
            EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);
        boolean empty = this.contents == Blocks.AIR;
        RayTraceResult rayTrace = this.rayTrace(world, player, empty);

        if (world.isRemote || rayTrace == null ||
                rayTrace.typeOfHit != RayTraceResult.Type.BLOCK) {

            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }

        BlockPos posTarget = rayTrace.getBlockPos();
        IBlockState state = world.getBlockState(posTarget);
        Material material = state.getMaterial();

        // Try to fill with targeted liquid
        if (empty) {

            if ((material == Material.WATER || material == BlockMaterial.TAR) &&
                    (state.getValue(BlockLiquid.LEVEL)) == 0) {

                ItemStack full = new ItemStack(material == Material.WATER ?
                        this.water.get() : this.tar.get());
                
                if (hand == EnumHand.OFF_HAND) {
                    
                    world.setBlockState(posTarget,
                            Blocks.AIR.getDefaultState(), 11);
                    player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1, 1);
                    return new ActionResult<ItemStack>
                            (EnumActionResult.SUCCESS, full);
                }
                
                 if (ContainerInventory.add(player, full).isEmpty()) {

                     world.setBlockState(posTarget,
                             Blocks.AIR.getDefaultState(), 11);
                     player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1, 1);
                     return new ActionResult<ItemStack>
                             (EnumActionResult.SUCCESS, ItemStack.EMPTY);
                 }
            }

        } else {

            boolean replaceable = state.getBlock()
                    .isReplaceable(world, posTarget);
            BlockPos posPlace = replaceable && rayTrace.sideHit == EnumFacing.UP
                    ? posTarget : posTarget.offset(rayTrace.sideHit);
            replaceable = world.getBlockState(posPlace).getBlock()
                    .isReplaceable(world, posPlace);

            if (replaceable && !material.isLiquid()) {

                world.destroyBlock(posPlace, true);
                player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1, 1);
                world.setBlockState(posPlace,
                        this.contents.getDefaultState(), 11);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,
                        new ItemStack(this.empty.get()));
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
}