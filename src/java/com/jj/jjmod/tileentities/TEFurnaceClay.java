package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

/** TileEntity for Clay Furnace blocks. */
public class TEFurnaceClay extends TEFurnaceAbstract {

    private EnumFacing facing;
    private EnumPartClay part;

    public TEFurnaceClay() {

        super(ModRecipes.CLAY);
    }
    
    @Override
    public int getCookTime(ItemStack stack) {

        return 300;
    }
    
    /** Sets this Clay Furnace to the given state information. */
    public void setState(EnumFacing facing, EnumPartClay part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this Clay Furnace block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartClay state of this Clay Furnace block. */
    public EnumPartClay getPart() {

        return this.part;
    }

    /** @return The position of the master block
     * of this Clay Furnace structure. */
    public BlockPos getMaster() {
        
        switch (this.part) {

            case BL: {

                return this.pos;
            }
            
            case BR: {

                return this.pos.offset(this.facing.rotateY().getOpposite());
            }
            
            case TL: {

                return this.pos.down();
            }
            
            case TR: {

                return this.pos.offset(this.facing.rotateY()
                        .getOpposite()).down();
            }
            
            default: {

                return this.pos;
            }
        }
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
        this.part = EnumPartClay.values()[compound.getInteger("part")];
    }
    
    /** Require to update rendering on the Client. */
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    /** Require to update rendering on the Client. */
   @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

   /** Require to update rendering on the Client. */
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }

    /** Enum defining parts of the Clay Furnace structure. */
    public enum EnumPartClay implements IStringSerializable {

        BL("bl"),
        BR("br"),
        TL("tl"),
        TR("tr");

        private final String name;

        private EnumPartClay(String name) {

            this.name = name;
        }

        @Override
        public String toString() {

            return this.name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
