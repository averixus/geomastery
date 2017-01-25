package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public class TECraftingSawpit extends TileEntity {
    
    protected int facing;
    protected int part;
    
    public void setState(int facing, EnumPartSawpit part) {
        
        this.facing = facing;
        this.part = part.ordinal();
    }
    
    public EnumFacing getFacing() {
        
        return EnumFacing.getHorizontal(this.facing);
    }
    
    public EnumPartSawpit getPart() {
        
        return EnumPartSawpit.values()[this.part];
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing);
        compound.setInteger("part", this.part);
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        this.facing = compound.getInteger("facing");
        this.part = compound.getInteger("part");
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
        
        return this.writeToNBT(new NBTTagCompound());
    }
    
    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        
        return new SPacketUpdateTileEntity(this.getPos(), 0, this.writeToNBT(new NBTTagCompound()));
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        
        this.readFromNBT(packet.getNbtCompound());
    }
    
    public enum EnumPartSawpit implements IStringSerializable {
        
        B1 ("b1", true), B2("b2", true), B3("b3", true), B4("b4", true), B5("b5", true),
        M1 ("m1", true), M2("m2", true), M3("m3", true), M4("m4", true), M5("m5", true),
        T1 ("t1", false), T2("t2", false), T3("t3", false), T4("t4", false), T5("t5", false);
        
        private final String name;
        private final boolean isPassable;
        
        private EnumPartSawpit(String name, boolean isPassable) {
            
            this.name = name;
            this.isPassable = isPassable;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        public boolean isPassable() {
            
            return this.isPassable;
        }
    }
}
