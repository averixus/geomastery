package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.items.ItemEdible;
import com.jj.jjmod.items.ItemEdible.FoodType;
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
            
            this.sendMessage(FoodType.CARBS, this.carbs.getFoodLevel());
        }
        
        if (this.protein.update(this.player)) {
            
            this.sendMessage(FoodType.PROTEIN, this.protein.getFoodLevel());
        }
        
        if (this.fruitveg.update(this.player)) {
            
            this.sendMessage(FoodType.FRUITVEG, this.fruitveg.getFoodLevel());
        }
    }
    
    @Override
    public boolean canEat(FoodType type) {
        
        switch (type) {
            
            case CARBS: {
                
                return this.carbs.needFood();
            }
            
            case PROTEIN: {
                
                return this.protein.needFood();
            }
            
            case FRUITVEG: {
                
                return this.fruitveg.needFood();
            }
            
            default: {
                
                return false;
            }
        }
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
        
        FoodType type = item instanceof ItemEdible ?
                ((ItemEdible) item).getType() : FoodType.CARBS;
                
        switch (type) {
            
            case CARBS : {
                
                this.carbs.addStats(item, stack);
                break;
            }
            
            case PROTEIN : {
                
                this.protein.addStats(item, stack);
                break;
            }
            
            case FRUITVEG : {
                
                this.fruitveg.addStats(item, stack);
                break;
            }
        }
    }
    
    @Override
    public void addStats(int hunger, float saturation) {
        
        this.carbs.addStats(hunger, saturation);
    }
    
    @Override
    public void sendMessage() {
        
        if (this.player instanceof EntityPlayerMP) {
            
            this.sendMessage(FoodType.CARBS, this.carbs.getFoodLevel());
            this.sendMessage(FoodType.PROTEIN, this.protein.getFoodLevel());
            this.sendMessage(FoodType.FRUITVEG, this.fruitveg.getFoodLevel());
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
            
            case CARBS: {
                
                this.carbs.setFoodLevel(hunger);
                break;
            }
            
            case PROTEIN: {
                
                this.protein.setFoodLevel(hunger);
                break;
            }
            
            case FRUITVEG: {
                
                this.fruitveg.setFoodLevel(hunger);
                break;
            }
        }
    }
}
