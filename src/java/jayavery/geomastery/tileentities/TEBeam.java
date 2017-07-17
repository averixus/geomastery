/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import java.util.function.Supplier;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.CPacketFloor;
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
    private EPartBeam part;
    /** The floor on this beam block. */
    private ETypeFloor floor;
    
    /** Sets the given state information. */
    public void setState(EnumFacing facing, EPartBeam part) {
        
        this.facing = facing;
        this.part = part;
        this.floor = ETypeFloor.NONE;
    }
    
    /** Attempts to apply the given floor to this beam block.
     * @return Whether the floor is successfully applied. */
    public boolean applyFloor(ETypeFloor floor) {
        
        if (this.floor == ETypeFloor.NONE && floor != ETypeFloor.NONE) {
            
            this.floor = floor;
            this.sendFloorUpdate();
            return true;
        }
        
        return false;
    }
    
    /** Sets the floor of this Beam block to NONE. */
    public void removeFloor() {
        
        this.floor = ETypeFloor.NONE;
        this.sendFloorUpdate();
    }
    
    /** @return The EnumFacing state of this beam block. */
    public EnumFacing getFacing() {
        
        return this.facing;
    }
    
    /** @return the EPartBeam state of this beam block. */
    public EPartBeam getPart() {
        
        return this.part;
    }
    
    /** @return The ETypeFloor state of this beam block. */
    public ETypeFloor getFloor() {
        
        return this.floor;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing == null ?
                0 : this.facing.getHorizontalIndex());
        compound.setInteger("part", this.part == null ?
                0 : this.part.ordinal());
        compound.setInteger("floor", this.floor == null ?
                0 : this.floor.ordinal());
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        this.facing = EnumFacing.getHorizontal(compound.getInteger("facing"));
        this.part = EPartBeam.values()[compound.getInteger("part")];
        this.floor = ETypeFloor.values()[compound.getInteger("floor")];
    }
    
    // Required to update rendering on the client. 
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    // Required to update rendering on the client.
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    // Required to update rendering on the client. 
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }
    
    /** Sends a packet to update the floor of this beam block on the client. */
    private void sendFloorUpdate() {
        
        if (!this.world.isRemote) {
            
            Geomastery.NETWORK.sendToAll(new CPacketFloor(this.floor,this.pos));
        }
    }
    
    /** Enum defining parts of the whole Beam structure. */
    public enum EPartBeam implements IStringSerializable {
        
        BACK("start"), MIDDLE("middle"), FRONT("end");
        
        private String name;
        
        private EPartBeam(String name) {
            
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
    public enum ETypeFloor implements IStringSerializable {
        
        NONE("none", () -> Items.AIR),
        WOOD("wood", () -> GeoItems.FLOOR_WOOD);
        
        private String name;
        /** Supplier for this floor's item. */
        private Supplier<Item> item;
        
        private ETypeFloor(String name, Supplier<Item> item) {
            
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
