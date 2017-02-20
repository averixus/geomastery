package com.jj.jjmod.items;

import java.util.function.Supplier;
import javax.annotation.Nullable;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/** Bucket item for tar and water. */
public class ItemBucket extends ItemJj {

    /** This bucket's contents. */
    private final Block contents;
    /** Supplier for the empty version of this bucket. */
    private Supplier<Item> empty;
    /** Supplier for the water filled version of this bucket. */
    private Supplier<Item> water;
    /** Supplier for the tar filled version of this bucket. */
    private Supplier<Item> tar;

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

        System.out.println("on item right click");
        ItemStack stack = player.getHeldItem(hand);
        System.out.println("item held in " + hand + " " + stack);
        boolean empty = this.contents == Blocks.AIR;
        RayTraceResult rayTrace = this.rayTrace(world, player, empty);

        if (world.isRemote || rayTrace == null ||
                rayTrace.typeOfHit != RayTraceResult.Type.BLOCK) {
            System.out.println("remote or no target");
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }

        BlockPos posTarget = rayTrace.getBlockPos();
        IBlockState state = world.getBlockState(posTarget);
        Material material = state.getMaterial();

        // Try to fill with targeted liquid
        if (empty) {
            System.out.println("this bucket empty");
            if ((material == Material.WATER || material == BlockMaterial.TAR) &&
                    (state.getValue(BlockLiquid.LEVEL)) == 0) {
                System.out.println("targeted tar or water");
                ItemStack full = new ItemStack(material == Material.WATER ?
                        this.water.get() : this.tar.get());
                System.out.println("full version item " + full);
                
                if (hand == EnumHand.OFF_HAND) {
                    
                 //   player.setHeldItem(EnumHand.OFF_HAND, full);
                    world.setBlockState(posTarget, Blocks.AIR.getDefaultState(), 11);
                    player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1, 1);
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, full);
                }
                 if (ContainerInventory.add(player, full).isEmpty()) {
                     System.out.println("added to inventory, setting state");
                     world.setBlockState(posTarget,
                             Blocks.AIR.getDefaultState(), 11);
                     player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1, 1);
                     return new ActionResult<ItemStack>
                             (EnumActionResult.SUCCESS, ItemStack.EMPTY);
                 }
            }

        } else {
            System.out.println("this bucket full");
            boolean replaceable = state.getBlock().isReplaceable(world, posTarget);
            BlockPos posPlace = replaceable && rayTrace.sideHit == EnumFacing.UP
                    ? posTarget : posTarget.offset(rayTrace.sideHit);
            replaceable = world.getBlockState(posPlace).getBlock().isReplaceable(world, posPlace);
            System.out.println(" replaceable? " + replaceable + " material not liquid? " + !material.isLiquid());
            if (replaceable && !material.isLiquid()) {
                System.out.println("can place at location");
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