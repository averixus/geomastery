package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** Item for placing multipart structures. */
public class ItemMultipart<E extends Enum<E> & IMultipart> extends ItemJj {

    /** Part of the enum structure placed at the target block. */
    private final E placePart;
    /** Sound of placing the structure. */
    private final SoundType sound;
    
    public ItemMultipart(String name, E placePart, SoundType sound) {
        
        this(name, 1, CreativeTabs.DECORATIONS, placePart, sound);
    }
    
    public ItemMultipart(String name, int stackSize, CreativeTabs tab,
            E placePart, SoundType sound) {
        
        super(name, stackSize, tab);
        this.placePart = placePart;
        this.sound = sound;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {

        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        
        int intFacing = MathHelper.floor(player.rotationYaw* 4.0F /
                360.0F + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
        BlockPos placePos = pos.offset(side);
        
        if (this.placePart.buildStructure(world, placePos, enumFacing)) {
            
            // Use item
            world.playSound(null, placePos, this.sound.getPlaceSound(),
                    SoundCategory.BLOCKS, this.sound.getVolume() + 1.0F
                    / 2.0F, this.sound.getPitch());
            
            if (!player.capabilities.isCreativeMode) {
                
                stack.shrink(1);
                ContainerInventory.updateHand(player, hand);
            }
            
            return EnumActionResult.SUCCESS;
        }
        
        return EnumActionResult.FAIL;
    }
}
