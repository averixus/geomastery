package com.jj.jjmod.tileentities;

import javax.annotation.Nullable;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.container.ContainerFurnaceSingle;

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

/** TileEntity for Stone Furnace blocks. */
public class TEFurnaceStone extends TEFurnaceAbstract {

    /** This block's horizontal facing. */
    private EnumFacing facing;
    /** This block's part in the structure. */
    private EnumPartStone part;

    public TEFurnaceStone() {

        super(ModRecipes.STONE, 6);
    }

    /** Sets this Stone Furnace block to the given information. */
    public void setState(EnumFacing facing, EnumPartStone part) {

        this.facing = facing;
        this.part = part;
    }

    /** @return The EnumFacing state of this Stone Furnace block. */
    public EnumFacing getFacing() {

        return this.facing;
    }

    /** @return The EnumPartStone state of this Stone Furnace block. */
    public EnumPartStone getPart() {

        return this.part;
    }

    /** @return The position of the master
     * block for this Stone Furnace structure. */
    public BlockPos getMaster() {

        switch (this.part) {

            case BL: {

                return this.pos;
            }
            
            case BM: {

                return this.pos.offset(this.facing.rotateY().getOpposite());
            }
            
            case BR: {

                return this.pos.offset(this.facing.rotateY().getOpposite(), 2);
            }
            
            case TL: {

                return this.pos.down();
            }
            
            case TM: {

                return this.pos.offset(this.facing.rotateY()
                        .getOpposite()).down();
            }
            
            case TR: {

                return this.pos.offset(this.facing.rotateY()
                        .getOpposite(), 2).down();
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
        this.part = EnumPartStone.values()[compound.getInteger("part")];
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

    /** Enum defining parts of the whole Stone Furnace structure. */
    public enum EnumPartStone implements IStringSerializable {

        BL("bl"),
        BM("bm"),
        BR("br"),
        TL("tl"),
        TM("tm"),
        TR("tr");

        private final String name;

        private EnumPartStone(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
