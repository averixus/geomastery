package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModPackets;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DefaultCapDecay implements ICapDecay {
    
    private static final int DAY_TICKS = 24000;
    private static final int MAX_STAGE = 10;
    
    private int maxAge;
    private int stage = 0;
    private int ageTimer = 0;
    
    public DefaultCapDecay(int maxDays) {
        
        this.maxAge = maxDays * DAY_TICKS;
    }
    
    @Override
    public boolean updateAndRot() {

        this.stage = (int) (((float) this.ageTimer / this.maxAge) * MAX_STAGE);

        if (this.stage >= MAX_STAGE) {
            
            return true;
            
        } else {

            this.ageTimer++;
            return false;
        }
    }
    
    @Override
    public float getRenderFraction() {

        return 1F - ((float) this.stage / MAX_STAGE);
    }
    
    @Override
    public int getAge() {
        
        return this.ageTimer;
    }
    
    @Override
    public void setAge(int age) {
        
        this.ageTimer = age;
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("stage", this.stage);
        nbt.setInteger("maxage", this.maxAge);
        return nbt;
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        
        this.stage = nbt.getInteger("stage");
        this.maxAge = nbt.getInteger("maxage");
        this.ageTimer = this.stage / this.maxAge;
    }
}
