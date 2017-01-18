package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModPackets;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DefaultCapDecay implements ICapDecay {
    
    protected int shelfLife;
    public DecayLevel level;
    protected int timer = 0;
    
    public DefaultCapDecay(int shelfLife) {
        
        this.shelfLife = shelfLife;
        this.level = DecayLevel.FRESH;
    }
    
    @Override
    public boolean updateAndRot() {
        
        if (this.timer >= (this.shelfLife / 4)) {
            
            if (this.level == DecayLevel.SPOILED) {

                return true;
                
            } else if (this.level == DecayLevel.OLD) {

                this.level = DecayLevel.SPOILED;
                this.timer = 0;
                
            } else if (this.level == DecayLevel.STALE) {

                this.level = DecayLevel.OLD;
                this.timer = 0;
                
            } else if (this.level == DecayLevel.FRESH) {

                this.level = DecayLevel.STALE;
                this.timer = 0;
            }
                        
        } else {
            
            this.timer++;
        }
        
        return false;
    }
    
    @Override
    public float getRenderFraction() {
        
        return this.level.getRenderFraction();
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("level", this.level.ordinal());
        nbt.setInteger("shelfLife", this.shelfLife);
        return nbt;
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        
        this.level = DecayLevel.values()[nbt.getInteger("level")];
        this.shelfLife = nbt.getInteger("shelfLife");
    }

    public enum DecayLevel {
        
        FRESH(0), STALE(0.25F), OLD(0.5F), SPOILED(0.75F);
        
        private float renderFraction;
        
        private DecayLevel(float renderFraction) {
            
            this.renderFraction = renderFraction;
        }
        
        public float getRenderFraction() {
            
            return this.renderFraction;
        }
    }
}
