package com.jayavery.jjmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCompost extends ItemJj {
    
    public ItemCompost() {
        
        super("compost", 12);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        
        int grade = stack.getMetadata();
        return super.getUnlocalizedName() + "_grade_" + grade;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn,
            BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        
        return EnumActionResult.SUCCESS; // TODO fertilise crops
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab,
            NonNullList<ItemStack> subItems) {
        
        for (int i = 1; i < 6; i++) {
            
            subItems.add(new ItemStack(item, 1, i));
        }
    }

}
