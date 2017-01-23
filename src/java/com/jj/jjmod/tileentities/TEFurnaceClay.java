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

public class TEFurnaceClay extends TEFurnaceAbstract {

    private int facing;
    private int part;

    public TEFurnaceClay() {

        super(ModRecipes.CLAY);
    }

    public void setState(int facing, int part) {

        this.facing = facing;
        this.part = part;
    }

    public EnumFacing getFacing() {

        return EnumFacing.getHorizontal(this.facing);
    }

    public EnumPartClay getPart() {

        return EnumPartClay.values()[this.part];
    }

    public BlockPos getMaster() {

        EnumFacing enumFacing = EnumFacing.getHorizontal(this.facing);

        switch (this.part) {

            case 0: {

                return this.pos;
            }
            
            case 1: {

                return this.pos.offset(enumFacing.rotateY().getOpposite());
            }
            
            case 2: {

                return this.pos.down();
            }
            
            case 3: {

                return this.pos.offset(enumFacing.rotateY()
                        .getOpposite()).down();
            }
            
            default: {

                return null;
            }
        }
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

    @Override
    public int getFuelTime(ItemStack stack) {

        // CONFIG furnaceClay fuel times

        if (stack == null) {

            return 0;
        }

        Item item = stack.getItem();

        if (item == Items.STICK) {

            return 200;
        }

        if (item == ModItems.pole) {

            return 500;
        }

        if (item == ModItems.log) {

            return 2000;
        }

        if (item == ModItems.thicklog) {

            return 2000;
        }

        if (item == ModItems.peatDry) {

            return 2400;
        }

        if (item == Items.COAL && stack.getMetadata() == 1) {

            return 3000;
        }

        return 0;
    }

    @Override
    public int getCookTime(ItemStack stack) {

        // CONFIG furnaceClay item cook times

        return 300;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player) {

        return new ContainerFurnace(player, this.worldObj, this);
    }

    public enum EnumPartClay implements IStringSerializable {

        BL("bl"),
        BR("br"),
        TL("tl"),
        TR("tr");

        private final String NAME;

        private EnumPartClay(String name) {

            this.NAME = name;
        }

        @Override
        public String toString() {

            return this.NAME;
        }

        @Override
        public String getName() {

            return this.NAME;
        }
    }
}
