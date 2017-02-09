package com.jj.jjmod.capabilities;

import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.Maps;
import com.jj.jjmod.init.ModBiomes;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.items.ItemEdible;
import com.jj.jjmod.packets.FoodPacketClient;
import com.jj.jjmod.packets.SpeedPacketClient;
import com.jj.jjmod.packets.TempPacketClient;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import com.jj.jjmod.utilities.EquipMaterial;
import com.jj.jjmod.utilities.FoodStatsPartial;
import com.jj.jjmod.utilities.FoodType;
import com.jj.jjmod.utilities.TempStage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class DefaultCapPlayer implements ICapPlayer {
    
    // Constants
    private static final int WATER_MAX = 3600;
    private static final int DAMAGE_MAX = 200;
    
    private static final float SPEED_MODIFIER = 43F;
    private static final float DEFAULT_SPEED = 4.3F;
    
    private static final int BACKPACK_I = 0;
    private static final int YOKE_I = 1;
    private static final int ROW_LENGTH = 9;
    
    public EntityPlayer player;
    
    // Temperature
    private int damageTimer = 0;
    private int wetTimer = 0;
    private TempStage tempStage = TempStage.OK;
    
    // Inventory
    private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
    
    // Food
    private FoodStatsPartial carbs;
    private FoodStatsPartial protein;
    private FoodStatsPartial fruitveg;
    private final Map<FoodType, FoodStatsPartial> typesMap = Maps.newHashMap();
    
    public DefaultCapPlayer(EntityPlayer player) {
        
        this.player = player;
        this.carbs = new FoodStatsPartial(this.player);
        this.protein = new FoodStatsPartial(this.player);
        this.fruitveg = new FoodStatsPartial(this.player);
        this.typesMap.put(FoodType.CARBS, this.carbs);
        this.typesMap.put(FoodType.PROTEIN, this.protein);
        this.typesMap.put(FoodType.FRUITVEG, this.fruitveg);
    }
    
    @Override
    public int getInventoryRows() {
        
        int rows = 0;

        if (this.stacks.get(BACKPACK_I).getItem() == ModItems.backpack) {

            rows += 1;
        }

        if (this.stacks.get(YOKE_I).getItem() == ModItems.yoke) {

            rows += 2;
        }

        return rows;
    }
    
    @Override
    public int getInventorySize() {

        return ROW_LENGTH + (ROW_LENGTH * this.getInventoryRows());
    }
    
    @Override
    public ItemStack getBackpack() {
        
        return this.stacks.get(BACKPACK_I);
    }
    
    @Override
    public ItemStack getYoke() {
        
        return this.stacks.get(YOKE_I);
    }
    
    @Override
    public ItemStack removeBackpack() {
        
        ItemStack backpack = this.getBackpack();
        this.putBackpack(ItemStack.EMPTY);
        return backpack;
    }
    
    @Override
    public ItemStack removeYoke() {
        
        ItemStack yoke = this.getYoke();
        this.putYoke(ItemStack.EMPTY);
        return yoke;
    }
    
    @Override
    public void putBackpack(ItemStack stack) {
        
        this.stacks.set(BACKPACK_I, stack);
    }
    
    @Override
    public void putYoke(ItemStack stack) {
        
        this.stacks.set(YOKE_I, stack);
    }
    
    @Override
    public boolean canSprint() {
        
        return !ModBlocks.OFFHAND_ONLY.contains(this.player.getHeldItemOffhand().getItem()) && this.stacks.get(YOKE_I).getItem() != ModItems.yoke;
    }
    
    @Override
    public ResourceLocation getTempIcon() {
        
        return this.tempStage.toResourceLocation();
    }
    
    @Override
    public int foodLevel(FoodType type) {
        
        return this.typesMap.get(type).getFoodLevel();
    }
    
    @Override
    public boolean canEat(FoodType type) {
        
        return this.typesMap.get(type).needFood();
    }
    
    @Override
    public int getFoodLevel() {
        
        return Math.min(this.carbs.getFoodLevel(),
                Math.min(this.protein.getFoodLevel(),
                this.fruitveg.getFoodLevel()));
    }
    
    @Override
    public void addExhaustion(float exhaustion) {
        
        this.carbs.addExhaustion(exhaustion);
        this.protein.addExhaustion(exhaustion);
        this.fruitveg.addExhaustion(exhaustion);
    }
    
    @Override
    public void addStats(ItemFood item, ItemStack stack) {
        
        FoodType type = item instanceof ItemEdible ?
                ((ItemEdible) item).getType() : FoodType.CARBS;
                
        this.typesMap.get(type).addStats(item, stack);
    }
    
    @Override
    public void tick() {
        
        if (this.player.world.isRemote) {
            
            return;
        }

        for (Entry<FoodType, FoodStatsPartial> entry : this.typesMap.entrySet()) {
            
            if (entry.getValue().tickHunger()) {
                System.out.println("sending tick packet for " + entry.getKey() + " hunger is " + entry.getValue().getFoodLevel());
                this.sendFoodPacket(entry.getKey(), entry.getValue().getFoodLevel());
            }
        }
        
        this.tickHeal();

        if (this.tickTemperature()) {
            System.out.println("sending temp tick packet " + this.tempStage);
            this.sendTempPacket(this.tempStage);
        }
        
        if (this.tickSpeed()) {
            System.out.println("sending speed tick packet " + this.player.capabilities.getWalkSpeed());
            this.sendSpeedPacket(this.player.capabilities.getWalkSpeed());
        }
    }
    
    private boolean tickTemperature() {
        
        TempStage oldStage = this.tempStage;
        float temp = 0;
        BlockPos playerPos = new BlockPos(this.player.posX,
                this.player.posY, this.player.posZ);
        World world = this.player.world;
        
        
        Biome biome = world.getBiomeForCoordsBody(playerPos);
        float biomeVar = ModBiomes.getTemp(biome);
        
        temp += biomeVar;

        float heightVar = 0;
        float belowSea = (float) (64 - this.player.posY);
        
        if (belowSea != 0) {

            heightVar += belowSea / 12F;
        }
        
        temp += heightVar;

        float timeVar;
        long time = world.getWorldTime();
        
        if ((time > 0 && time <= 3000) || (time > 12000 && time <= 18000)) {

            timeVar = 0;
            
        } else if (time > 6000 && time <= 1200) {

            timeVar = 1;
            
        } else {

            timeVar = -1;
            
        }
        
        if (biomeVar > 3 && time > 4000 && time <= 8000 &&
                !world.canSeeSky(playerPos)) {

            timeVar += -1;
        }
        
        temp += timeVar;
        
        boolean isCave = true;
        
        outer: 
        for (int x = -10; x <= 10; x++) {
            
            for (int y = -10; y <= 10; y++) {
                
                for (int z = -10; z <= 10; z++) {
                    
                    double xPos = this.player.posX + x;
                    double yPos = this.player.posY + y;
                    double zPos = this.player.posZ + z;
                    
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    
                    if (world.canSeeSky(pos)) {
                        
                        isCave = false;
                        break outer;
                    }
                }
            }
        }
        
        if (isCave) {
            
            temp = 0;
        }

        float waterVar = 0;
        
        if (this.player.isInWater() || this.player.isWet()) {
            
            waterVar = -3;
            this.wetTimer = WATER_MAX;
            
        } else if (this.wetTimer > 0) {
            
            waterVar = -1;
            this.wetTimer--;
        }

        temp += waterVar;  

        float clothesVar = 0;

        if (this.wetTimer == 0) {
        
            for (ItemStack stack : this.player.inventory.armorInventory) {

                if (stack.getItem() instanceof ItemArmor) {
                    
                    ItemArmor armor = (ItemArmor) stack.getItem();
                    
                    if (armor.getArmorMaterial() == EquipMaterial.WOOL_APPAREL ||
                            armor.getArmorMaterial() ==
                            EquipMaterial.FUR_APPAREL) {

                        clothesVar += 0.5;
                        
                    } else {

                        clothesVar += 0.25;
                    }
                }
            }
        }
        
        temp += clothesVar;

        double fireVar = 0;
        
        for (int x = -10; x <= 10; x++) {
            
            for (int y = -10; y <= 10; y++) {
                
                for (int z = -10; z <= 10; z++) {
                    
                    double xPos = this.player.posX + x;
                    double yPos = this.player.posY + y;
                    double zPos = this.player.posZ + z;
                    
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    
                    Block block = world
                            .getBlockState(pos).getBlock();
                    
                    boolean fireLit = false;
                    
                    if (block == ModBlocks.furnaceCampfire || 
                            block == ModBlocks.furnaceCookfire ||
                            block == ModBlocks.furnaceClay ||
                            block == ModBlocks.furnaceStone) {
                        
                        TEFurnaceAbstract furnace =
                                (TEFurnaceAbstract) world
                                .getTileEntity(pos);
                        
                        fireLit = furnace.isHeating();
                    }
                    
                    if (fireLit || block == Blocks.FIRE) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 4 - distance;
                        
                        fireVar = Math.max(fireVar, heat);
                        
                    } else if (block == ModBlocks.torchTallow ||
                            block == ModBlocks.torchTar) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 2 - distance;
                        
                        fireVar = Math.max(fireVar, heat);
                        
                    } else if (block == Blocks.LAVA ||
                            block == Blocks.FLOWING_LAVA) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 11 - distance;
                        
                        fireVar = Math.max(fireVar, heat);
                    }
                }
            }
        }
        
        temp += fireVar;

        if (temp < 0) {
            
            this.tempStage = TempStage.COLD;
            
        } else if (temp < 2.5) {
            
            this.tempStage = TempStage.COOL;
            
        } else if (temp < 5.5) {
            
            this.tempStage = TempStage.OK;
            
        } else if (temp < 8) {
            
            this.tempStage = TempStage.WARM;
            
        } else {
            
            this.tempStage = TempStage.HOT;
        }

        if ((this.tempStage == TempStage.HOT ||
                this.tempStage == TempStage.COLD) && this.damageTimer == 0) {

            this.player.attackEntityFrom(DamageSource.GENERIC, 1);
            this.damageTimer = DAMAGE_MAX;
            
        } else if (this.damageTimer > 0) {

            this.damageTimer--;
        }
           
        return oldStage != this.tempStage;
    }
    
    private void tickHeal() {
   
        if (!this.player.shouldHeal()) {
            
            return;
        }
            
        Map<Float, FoodStatsPartial> typesMap = Maps.newTreeMap();
        typesMap.put(-this.carbs.getFoodLevel() - this.carbs.getSaturationLevel() - this.carbs.getExhaustion(), this.carbs);
        typesMap.put(-this.protein.getFoodLevel() - this.protein.getSaturationLevel() - this.protein.getExhaustion(), this.protein);
        typesMap.put(-this.fruitveg.getFoodLevel() - this.fruitveg.getSaturationLevel() - this.fruitveg.getExhaustion(), this.fruitveg);

        for (Entry<Float, FoodStatsPartial> entry : typesMap.entrySet()) {
            
            entry.getValue().heal();
        }
    }
    
    private boolean tickSpeed() {
        
        double speed = DEFAULT_SPEED;
        double oldSpeed = this.player.capabilities.getWalkSpeed();
        
        for (ItemStack stack : this.player.inventory.armorInventory) {

            if (stack.getItem() instanceof ItemArmor) {
                
                ItemArmor armor = (ItemArmor) stack.getItem();
                
                if (armor.getArmorMaterial() == EquipMaterial.LEATHER_APPAREL) {

                    speed -= 0.2;
                    
                } else if (armor.getArmorMaterial() == EquipMaterial.STEELMAIL_APPAREL) {

                    speed -= 0.4;
                    
                } else if (armor.getArmorMaterial() == EquipMaterial.STEELPLATE_APPAREL) {
                    
                    speed -= 0.5;
                }
            }
        }
        
        if (ModBlocks.OFFHAND_ONLY.contains(this.player.getHeldItemOffhand().getItem())) {
            
            speed -= 2.0;
        }
        
        if (this.getBackpack().getItem() == ModItems.backpack) {
            
            speed -= 0.5;
        }
        
        if (this.getYoke().getItem() == ModItems.yoke) {
            
            speed -= 1.5;
        }
        
        if (speed < 2) {
            
            speed = 2;
        }
        
        float adjustedSpeed = (float) speed / SPEED_MODIFIER;
        this.player.capabilities.setPlayerWalkSpeed(adjustedSpeed);
        return adjustedSpeed != oldSpeed;
    }
    
    @Override
    public void syncAll() {
        
        if (this.player.world.isRemote) {
            
            return;
        }
        
        for (Entry<FoodType, FoodStatsPartial> entry : this.typesMap.entrySet()) {
            
            this.sendFoodPacket(entry.getKey(), entry.getValue().getFoodLevel());
        }
        
        this.sendTempPacket(this.tempStage);
        this.sendSpeedPacket(this.player.capabilities.getWalkSpeed());
    }
    
    @Override
    public void sendFoodPacket(FoodType type, int hunger) {
        
        ModPackets.INSTANCE.sendTo(new FoodPacketClient(type, hunger), (EntityPlayerMP) this.player);
    }
    
    @Override
    public void sendTempPacket(TempStage stage) {
        
        ModPackets.INSTANCE.sendTo(new TempPacketClient(stage), (EntityPlayerMP) this.player);
    }
    
    @Override
    public void sendSpeedPacket(float speed) {
        
        ModPackets.INSTANCE.sendTo(new SpeedPacketClient(speed), (EntityPlayerMP) this.player);
    }
    
    @Override
    public void processFoodMessage(FoodType type, int hunger) {
        
        this.typesMap.get(type).setFoodLevel(hunger);
    }
    
    @Override
    public void processTempMessage(TempStage stage) {
        
        this.tempStage = stage;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        NBTTagCompound nbt = new NBTTagCompound();
        
        for (int i = 0; i < this.stacks.size(); i++) {
            
            NBTTagCompound tag = new NBTTagCompound();
            this.stacks.get(i).writeToNBT(tag);
            nbt.setTag(Integer.toString(i), tag);
        }
        
        nbt.setInteger("damageTimer", this.damageTimer);
        nbt.setInteger("wetTimer", this.wetTimer);
        nbt.setInteger("tempStage", this.tempStage.ordinal());
        
        NBTTagCompound carbs = new NBTTagCompound();
        NBTTagCompound protein = new NBTTagCompound();
        NBTTagCompound fruitveg = new NBTTagCompound();
        this.carbs.writeNBT(carbs);
        this.protein.writeNBT(protein);
        this.fruitveg.writeNBT(fruitveg);
        nbt.setTag("carbs", carbs);
        nbt.setTag("protein", protein);
        nbt.setTag("fruitveg", fruitveg);
        
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

        for (int i = 0; i < this.stacks.size(); i++) {
            
            NBTTagCompound tag = nbt.getCompoundTag(Integer.toString(i));
            this.stacks.set(i, new ItemStack(tag));
        }
        
        this.damageTimer = nbt.getInteger("damageTimer");
        this.wetTimer = nbt.getInteger("wetTimer");
        this.tempStage = TempStage.values()[nbt.getInteger("tempStage")];
        
        this.carbs.readNBT(nbt.getCompoundTag("carbs"));
        this.protein.readNBT(nbt.getCompoundTag("protein"));
        this.fruitveg.readNBT(nbt.getCompoundTag("fruitveg"));
    }
}
