package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModItems.FoodType;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.packets.FoodUpdateClient;
import com.jj.jjmod.utilities.FoodStatsPartial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DefaultCapFoodstats implements ICapFoodstats {
    
    public FoodStatsPartial carbs = new FoodStatsPartial();
    public FoodStatsPartial protein = new FoodStatsPartial();
    public FoodStatsPartial fruitveg = new FoodStatsPartial();
    
    protected EntityPlayer player;
    
    public DefaultCapFoodstats(EntityPlayer player) {
        
        this.player = player;
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        
        System.out.println("serializing nbt foodinfo");
        
        NBTTagCompound carbs = new NBTTagCompound();
        NBTTagCompound protein = new NBTTagCompound();
        NBTTagCompound fruitveg = new NBTTagCompound();
        
        NBTTagCompound nbt = new NBTTagCompound();
        
        this.carbs.writeNBT(carbs);
        this.protein.writeNBT(protein);
        this.fruitveg.writeNBT(fruitveg);
        
        nbt.setTag("carbs", carbs);
        nbt.setTag("protein", protein);
        nbt.setTag("fruitveg", fruitveg);
        
        return nbt;
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        
        System.out.println("deserializing nbt foodinfo");
        
        this.carbs.readNBT(nbt.getCompoundTag("carbs"));
        this.protein.readNBT(nbt.getCompoundTag("protein"));
        this.fruitveg.readNBT(nbt.getCompoundTag("fruitveg"));
    }
    
    @Override
    public void update() {
        
        if (this.carbs.update(this.player)) {
            
            this.sendMessage(FoodType.FOOD_CARBS, this.carbs.getFoodLevel());
        }
        
        if (this.protein.update(this.player)) {
            
            this.sendMessage(FoodType.FOOD_PROTEIN, this.protein.getFoodLevel());
        }
        
        if (this.fruitveg.update(this.player)) {
            
            this.sendMessage(FoodType.FOOD_FRUITVEG, this.fruitveg.getFoodLevel());
        }
    }
    
    @Override
    public boolean needFood() {
        
        return this.carbs.needFood() || this.protein.needFood() ||
                this.fruitveg.needFood();
    }
    
    @Override
    public int getFoodLevel() {
        
        return Math.min(this.carbs.getFoodLevel(),
                Math.min(this.protein.getFoodLevel(),
                this.fruitveg.getFoodLevel()));
    }
    
    @Override
    public void addExhaustion(float exhaustion) {
        
        this.carbs.addExhaustion(exhaustion);
        this.protein.addExhaustion(exhaustion);
        this.fruitveg.addExhaustion(exhaustion);
    }
    
    @Override
    public void addStats(ItemFood item, ItemStack stack) {
        
        if (ModItems.CARBS.contains(item)) {
            
            this.carbs.addStats(item, stack);
            
        } else if (ModItems.PROTEIN.contains(item)) {
            
            this.protein.addStats(item, stack);
            
        } else if (ModItems.FRUITVEG.contains(item)) {
            
            this.fruitveg.addStats(item, stack);
        }
    }
    
    @Override
    public void addStats(int hunger, float saturation) {
        
        this.carbs.addStats(hunger, saturation);
    }
    
    @Override
    public void sendMessage() {
        
        if (this.player instanceof EntityPlayerMP) {
            
            this.sendMessage(FoodType.FOOD_CARBS, this.carbs.getFoodLevel());
            this.sendMessage(FoodType.FOOD_PROTEIN, this.protein.getFoodLevel());
            this.sendMessage(FoodType.FOOD_FRUITVEG, this.fruitveg.getFoodLevel());
        }
    }
    
    @Override
    public void sendMessage(FoodType type, int hunger) {
        
        if (this.player instanceof EntityPlayerMP) {
            
            ModPackets.INSTANCE.sendTo(new FoodUpdateClient(type, hunger),
                    (EntityPlayerMP) this.player);
        }
    }
    
    @Override
    public void processMessage(FoodType type, int hunger) {
        
        switch (type) {
            
            case FOOD_CARBS: {
                
                this.carbs.setFoodLevel(hunger);
                break;
            }
            
            case FOOD_PROTEIN: {
                
                this.protein.setFoodLevel(hunger);
                break;
            }
            
            case FOOD_FRUITVEG: {
                
                this.fruitveg.setFoodLevel(hunger);
                break;
            }
        }
    }
}
