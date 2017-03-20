package com.jayavery.jjmod.tileentities;

import java.util.function.Supplier;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.init.ModPackets;
import com.jayavery.jjmod.packets.FloorUpdateClient;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

/** TileEntity for beam block. */
public class TEBeam extends TileEntity {
    
    /** Direction this beam is facing. */
    private EnumFacing facing;
    /** This block's part in the whole structure. */
    private EnumPartBeam part;
    /** The floor on this beam block. */
    private EnumFloor floor;
    /** The beam item associated with this block. */
    private Item item;
    
    /** Sets the given state information. */
    public void setState(EnumFacing facing, EnumPartBeam part, Item item) {
        
        this.facing = facing;
        this.part = part;
        this.item = item;
        this.floor = EnumFloor.NONE;
    }
    
    /** Attempts to apply the given floor to this beam block.
     * @return Whether the floor is successfully applied. */
    public boolean applyFloor(EnumFloor floor) {
        
        if (this.floor == EnumFloor.NONE && floor != EnumFloor.NONE) {
            
            this.floor = floor;
            this.sendFloorUpdate();
            return true;
        }
        
        return false;
    }
    
    /** Sets the floor of this Beam block to NONE. */
    public void removeFloor() {
        
        this.floor = EnumFloor.NONE;
        this.sendFloorUpdate();
    }
    
    /** @return The EnumFacing state of this beam block. */
    public EnumFacing getFacing() {
        
        return this.facing;
    }
    
    /** @return the EnumPartBeam state of this beam block. */
    public EnumPartBeam getPart() {
        
        return this.part;
    }
    
    /** @return The EnumFloor state of this beam block. */
    public EnumFloor getFloor() {
        
        return this.floor;
    }
    
    /** @return The item associated with this beam. */
    public Item getItem() {
        
        return this.item;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing.getHorizontalIndex());
        compound.setInteger("part", this.part.ordinal());
        compound.setInteger("floor", this.floor.ordinal());
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        this.facing = EnumFacing.getHorizontal(compound.getInteger("facing"));
        this.part = EnumPartBeam.values()[compound.getInteger("part")];
        this.floor = EnumFloor.values()[compound.getInteger("floor")];
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
    
    /** Sends a packet to update the floor of this beam block on the Client. */
    private void sendFloorUpdate() {
        
        if (!this.world.isRemote) {
            
            ModPackets.NETWORK
                    .sendToAll(new FloorUpdateClient(this.floor,this.pos));
        }
    }
    
    /** Enum defining parts of the whole Beam structure. */
    public enum EnumPartBeam implements IStringSerializable {
        
        BACK("start"), MIDDLE("middle"), FRONT("end");
        
        private String name;
        
        private EnumPartBeam(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        public boolean shouldDrop() {

            return this == BACK;
        }
    }
    
    /** Enum defining types of floor a beam block can have. */
    public enum EnumFloor implements IStringSerializable {
        
        NONE("none", () -> Items.AIR),
        POLE("pole", () -> ModItems.floorPole),
        WOOD("wood", () -> ModItems.floorWood);
        
        private String name;
        private Supplier<Item> item;
        
        private EnumFloor(String name, Supplier<Item> item) {
            
            this.name = name;
            this.item = item;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        /** @return The item form of this floor. */
        public Item getItem() {
            
            return this.item.get();
        }
    }
}
