package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public class TECraftingArmourer extends TileEntity {
    
    protected int facing;
    protected int part;
    
    public void setState(EnumFacing facing, EnumPartArmourer part) {
        
        this.facing = facing.getHorizontalIndex();
        this.part = part.ordinal();
    }
    
    public EnumFacing getFacing() {
        
        return EnumFacing.getHorizontal(this.facing);
    }
    
    public EnumPartArmourer getPart() {
        
        return EnumPartArmourer.values()[this.part];
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

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }

    public enum EnumPartArmourer implements IStringSerializable {
        
        T("t"), L("l"), M("m"), R("r");
        
        private final String name;
        
        private EnumPartArmourer(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
    }

}
