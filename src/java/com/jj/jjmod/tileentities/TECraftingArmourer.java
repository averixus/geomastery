package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;
import com.jj.jjmod.utilities.IMultipart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

/** TileEntity for armourer crafting block. */
public class TECraftingArmourer extends TileEntity {
    
    /** EnumFacing of this armourer block. */
    private EnumFacing facing;
    /** Part of this armourer block. */
    private EnumPartArmourer part;
    
    /** Sets the given state information. */
    public void setState(EnumFacing facing, EnumPartArmourer part) {
        
        this.facing = facing;
        this.part = part;
    }
    
    /** @return The EnumFacing state of this armourer block. */
    public EnumFacing getFacing() {
        
        return this.facing;
    }
    
    /** @return The EnumPartArmourer state of this armourer block. */
    public EnumPartArmourer getPart() {
        
        return this.part;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing.getHorizontalIndex());
        compound.setInteger("part", this.part.ordinal());
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        this.facing = EnumFacing.getHorizontal(compound.getInteger("facing"));
        this.part = EnumPartArmourer.values()[compound.getInteger("part")];
    }
    
    /** Required to update rendering on the Client. */
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    /** Required to update rendering on the Client. */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    /** Required to update rendering on the Client. */
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }

    /** Enum defining parts of the whole Armourer structure. */
    public enum EnumPartArmourer implements IStringSerializable, IMultipart {
        
        T("t"), L("l"), M("m"), R("r");
        
        private final String name;
        
        private EnumPartArmourer(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }

        @Override
        public boolean shouldDrop() {

            return this == T;
        }
    }
}
