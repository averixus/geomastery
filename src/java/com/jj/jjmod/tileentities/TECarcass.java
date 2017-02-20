package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/** TileEntity for Carcass blocks. */
public class TECarcass extends TileEntity {
    
//    private static final int DAY_TICKS = 24000;
    
    private int stageSize;
    private long birthTime;
    
 //   private int maxAge;
 //   private int ageTimer = 0;
    
    public void setData(long birthTime, int stageSize) {
        
        this.birthTime = birthTime;
        this.stageSize = stageSize;
    }
    
    public long getBirthTime() {
        
        return this.birthTime;
    }
    
    public int getStageSize() {
        
        return this.stageSize;
    }
    
    /** @return The current age in ticks. */
 //   public int getAge() {
        
 //       return this.ageTimer;
 //   }
    
    /** Sets the age to the given ticks. */
 //   public void setAge(int age) {
        
  //      this.ageTimer = age;
//    }
    
    /** Sets the max age to the given days. */
 //   public void setShelfLife(int shelfLife) {
        
  //      this.maxAge = shelfLife * DAY_TICKS;
  //  }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        super.writeToNBT(nbt);
        nbt.setLong("birthTime", this.birthTime);
        nbt.setInteger("stageSize", this.stageSize);
     //   nbt.setInteger("ageTimer", this.ageTimer);
    //    nbt.setInteger("maxAge", this.maxAge);
        return nbt;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        this.birthTime = nbt.getLong("birthTime");
        this.stageSize = nbt.getInteger("stageSize");
      //  this.maxAge = nbt.getInteger("maxAge");
      //  this.ageTimer = nbt.getInteger("ageTimer");
    }
}
