/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** ICapDecay implementation. */
public class DefaultCapDecay implements ICapDecay {
    
    /** Ticks in one game day. */
    private static final int DAY_TICKS = 24000;
    /** Number of stages decay is divided into. */
    private static final int MAX_STAGE = 10;
        
    /** Number of ticks in a single decay stage. */
    private int stageSize;
    /** World time when this item was created. */
    private long birthTime;
    
    public DefaultCapDecay(int maxDays) {
        
        int maxAge = maxDays * DAY_TICKS;
        this.stageSize = maxAge / MAX_STAGE;
    }
    
    @Override
    public void updateFromNBT(NBTTagCompound nbt) {
        
        if (nbt == null) {
            
            return;
        }
        
        if (nbt.hasKey("stageSize")) {
            
            this.stageSize = Math.max(this.stageSize,
                    nbt.getInteger("stageSize"));
        }
        
        if (nbt.hasKey("birthTime")) {
            
            this.birthTime = Math.max(this.birthTime, nbt.getLong("birthTime"));
        }
    }
    
    @Override
    public float getFraction(World world) {
        
        if (world == null) {
            
            return 1;
        }
        
        long currentTime = world.getTotalWorldTime();
        long timeDiff = currentTime - this.birthTime;
        long stage = timeDiff / this.stageSize;
        return MathHelper.clamp(1F - ((float) stage / MAX_STAGE), 0, 1);
    }
    
    @Override
    public long getBirthTime() {
        
        return this.birthTime;
    }
    
    @Override
    public int getStageSize() {
        
        return this.stageSize;
    }
    
    @Override
    public void setBirthTime(long birthTime) {
        
        this.birthTime = (birthTime / this.stageSize) * this.stageSize;
    }
    
    @Override
    public void setStageSize(int stageSize) {
        
        this.stageSize = stageSize;
    }
    
    @Override
    public boolean isRot(World world) {
        
        if (world == null) {
            
            return false;
        }
        
        long currentTime = world.getTotalWorldTime();
        long timeDiff = currentTime - this.birthTime;
        return timeDiff >= (this.stageSize * MAX_STAGE);
    }
    
    @Override
    public NBTTagCompound serializeNBT() {
        
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("birthTime", this.birthTime);
        nbt.setInteger("stageSize", this.stageSize);
        return nbt;
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        this.birthTime = nbt.getLong("birthTime");
        this.stageSize = nbt.getInteger("stageSize");
    }
}
