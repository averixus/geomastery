/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.capabilities;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jayavery.geomastery.items.ItemEdible;
import jayavery.geomastery.main.GeoBiomes;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.CPacketDebug;
import jayavery.geomastery.packets.CPacketHunger;
import jayavery.geomastery.packets.CPacketTemp;
import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import jayavery.geomastery.utilities.EFoodType;
import jayavery.geomastery.utilities.ESpeedStage;
import jayavery.geomastery.utilities.ETempStage;
import jayavery.geomastery.utilities.EquipMaterial;
import jayavery.geomastery.utilities.FoodStatsPartial;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** ICapPlayer implementation. */
public class DefaultCapPlayer implements ICapPlayer {
    
    /** Ticks the player stays wet for after being in water. */
    private static final int WATER_MAX = 3600;
    /** Ticks between taking damage from temperature. */
    private static final int DAMAGE_MAX = 200;
    /** Tick count for hit of temperature damage. */
    private static final int DAMAGE_TIME = 160;
    /** Decimal format for temperatures. */
    private static DecimalFormat TEMP = new DecimalFormat("+0.00;-0.00");
    /** Default walk speed actual value. */
    private static final float DEFAULT_SPEED = 4.3F;
    /** Inventory slots in a row. */
    private static final int ROW_LENGTH = 9;
    /** Hunger cost of sleeping. */
    private static final int SLEEP_COST = 6;
    /** Delay before the same item can be picked up. */
    private static final int PICKUP_DELAY = 100;
    
    /** The player this capability belongs to. */
    public EntityPlayer player;
    
    /** Ticks since temperature damage. */
    private int damageTimer = 0;
    /** Ticks since wet. */
    private int wetTimer = 0;
    /** Temperature stage. */
    private ETempStage tempStage = ETempStage.OK;
    /** Temperature debug text. */
    private List<String> debug = Lists.newArrayList();

    /** Current walk speed. */
    private ESpeedStage speedStage = null;
    
    /** Backpack slot. */
    private ItemStack backpack = ItemStack.EMPTY;
    /** Yoke slot. */
    private ItemStack yoke = ItemStack.EMPTY;
    
    /** Carbs food values. */
    private FoodStatsPartial carbs;
    /** Protein food values. */
    private FoodStatsPartial protein;
    /** Fruit/veg food values. */
    private FoodStatsPartial fruitveg;
    /** Convenience map of food types onto associated food stats. */
    private final Map<EFoodType, FoodStatsPartial> typesMap = Maps.newHashMap();
    /** Convenience list of food stats. */
    private final List<FoodStatsPartial> typesList = Lists.newArrayList();
    /** Comparator to sort foodstats in order of total fullness. */
    private final Comparator<FoodStatsPartial> comparator = (a, b) -> Math.round(b.getFullness() - a.getFullness());
            
    /** Set of item pickup delays. */
    private final Map<Item, Long> delays = Maps.newHashMap();
    
    public DefaultCapPlayer(EntityPlayer player) {

        this.player = player;
        this.carbs = new FoodStatsPartial(this.player);
        this.protein = new FoodStatsPartial(this.player);
        this.fruitveg = new FoodStatsPartial(this.player);
        this.typesMap.put(EFoodType.CARBS, this.carbs);
        this.typesMap.put(EFoodType.PROTEIN, this.protein);
        this.typesMap.put(EFoodType.FRUITVEG, this.fruitveg);
        this.typesList.add(this.carbs);
        this.typesList.add(this.protein);
        this.typesList.add(this.fruitveg);
    }
    
    @Override
    public int getInventoryRows() {
        
        int rows = 0;
        rows = this.backpack.getItem() == GeoItems.BACKPACK ? rows + 1 : rows;
        rows = this.yoke.getItem() == GeoItems.YOKE ? rows + 2 : rows;
        return rows;
    }
    
    @Override
    public int getInventorySize() {

        return ROW_LENGTH + (ROW_LENGTH * this.getInventoryRows());
    }
    
    @Override
    public ItemStack getBackpack() {
        
        return this.backpack;
    }
    
    @Override
    public ItemStack getYoke() {
        
        return this.yoke;
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

        this.backpack = stack;
    }
    
    @Override
    public void putYoke(ItemStack stack) {
        
        this.yoke = stack;
    }
    
    @Override
    public boolean canSprint() {
        
        return !GeoBlocks.OFFHAND_ONLY.contains(this.player.getHeldItemOffhand()
                .getItem()) && this.yoke.getItem() != GeoItems.YOKE;
    }
    
    @Override
    public ResourceLocation getTempIcon() {
        
        return this.tempStage.toResourceLocation();
    }
    
    @Override
    public int foodLevel(EFoodType type) {
        
        return this.typesMap.get(type).getFoodLevel();
    }
    
    @Override
    public boolean canEat(EFoodType type) {
        
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
        
        for (FoodStatsPartial food : this.typesList) {
            
            food.addExhaustion(exhaustion);
        }
    }
    
    @Override
    public void addStats(ItemEdible item, ItemStack stack) {
        
        EFoodType type = item.getType();
        this.typesMap.get(type).addStats(item, stack);
    }
    
    @Override
    public void sleep(float healAmount) {

        for (Entry<EFoodType, FoodStatsPartial> entry :
                this.typesMap.entrySet()) {
            
            FoodStatsPartial food = entry.getValue();
            
            if (food.getFoodLevel() > 10) {
                
                this.player.heal(healAmount);
            }
            
            food.setFoodLevel(Math.max(food.getFoodLevel() - SLEEP_COST, 0));
            food.setSaturationLevel(0);
            this.sendFoodPacket(entry.getKey());
        }
    }
    
    @Override
    public void addDelay(Item item, long time) {
        
        this.delays.put(item, time);
    }
    
    @Override
    public boolean canPickup(Item item) {
        
        return !this.delays.containsKey(item);
    }
    
    @Override
    public void tick() {
        
        if (this.player.world.isRemote) {
            
            return;
        }
            
        for (Entry<EFoodType, FoodStatsPartial> entry :
                this.typesMap.entrySet()) {
            
            if (entry.getValue().tickHunger()) {

                this.sendFoodPacket(entry.getKey());
            }
        }
        
        this.tickHeal();
        
        if (this.tickTemperature()) {
    
            this.sendTempPacket(this.tempStage);
        }
        
        this.tickSpeed();
        this.tickPickup();
    }
    
    /** Calculate the player's temperature.
     * @return Whether the ETempStage has changed. */
    private boolean tickTemperature() {
        
        this.debug.clear();
                
        ETempStage oldStage = this.tempStage;
        float temp = 0;
        BlockPos playerPos = new BlockPos(this.player.posX,
                this.player.posY, this.player.posZ);
        World world = this.player.world;
        
        // Biome
        Biome biome = world.getBiomeForCoordsBody(playerPos);
        float biomeVar = GeoBiomes.getTemp(biome);
        temp += biomeVar;
        this.debug.add("Biome base temp: " + TEMP.format(biomeVar));
        
        // Altitude
        float heightVar = 0;
        float belowSea = (float) (64 - this.player.posY);
        
        if (belowSea != 0) {

            heightVar += belowSea / 20F;
        }
        
        temp += heightVar;
        this.debug.add("Altitude var: " + TEMP.format(heightVar));
        
        // Time of day
        float timeVar = 0;
        long time = world.getWorldTime() % 24000;
        
        if (time > 3000 && time <= 6000) { // 9 til 12 am
            
            timeVar = 0.5F; 
            
        } else if (time > 6000 && time <= 9000) { // 12 til 3 pm
            
            timeVar = 1;
            
        } else if (time > 9000 && time <= 12000) { // 3 til 6 pm
            
            timeVar = 0.5F;
            
        } else if (time > 15000 && time <= 18000) { // 9 til 12 pm
            
            timeVar = -0.5F;
            
        } else if (time > 18000 && time <= 21000) { // 12 til 3 am
            
            timeVar = -1;
            
        } else if (time > 21000) { // 3 til 6 am
            
            timeVar = -0.5F;
        }
        
        if (biomeVar > 3 && time > 4000 && time <= 8000 &&
                !world.canSeeSky(playerPos)) {

            // Shade when in hottest biomes
            timeVar += -1;
        }
        
        temp += timeVar;
        this.debug.add("Time var: " + TEMP.format(timeVar));
                
        // Cave climate
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
            this.debug.clear();
            this.debug.add("Cave base temp: " + 0);
        }

        // Wetness
        float waterVar = 0;
        
        if (this.player.isInWater() || this.player.isWet()) {
            
            waterVar = -3;
            this.wetTimer = WATER_MAX;
            
        } else if (this.wetTimer > 0) {
            
            waterVar = -1;
            this.wetTimer--;
        }

        temp += waterVar;
        this.debug.add("Water var: " + TEMP.format(waterVar));

        // Clothing
        float clothesVar = 0;

        if (this.wetTimer == 0) {
        
            for (ItemStack stack : this.player.inventory.armorInventory) {

                if (stack.getItem() instanceof ItemArmor) {
                    
                    ItemArmor armor = (ItemArmor) stack.getItem();
                    
                    if (armor.getArmorMaterial() == EquipMaterial.WOOL_APPAREL
                            || armor.getArmorMaterial() ==
                            EquipMaterial.FUR_APPAREL) {

                        clothesVar += 0.7;
                        
                    } else {

                        clothesVar += 0.4;
                    }
                }
            }
        }
        
        temp += clothesVar;
        this.debug.add("Clothing var: " + TEMP.format(clothesVar));

        // Heating blocks
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
                    
                    if (world.getTileEntity(pos) instanceof TEFurnaceAbstract) {
                        
                        TEFurnaceAbstract<?> furnace =
                                (TEFurnaceAbstract<?>) world.getTileEntity(pos);
                        fireLit = furnace.isHeating();
                    }
                    
                    if (fireLit || block == Blocks.FIRE) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 4 * (1 - (distance / 8));
                        
                        fireVar = Math.max(fireVar, heat);
                        
                    } else if (block == GeoBlocks.TORCH_TALLOW ||
                            block == GeoBlocks.TORCH_TAR) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 2 * (1 - (distance / 4));
                        
                        fireVar = Math.max(fireVar, heat);
                        
                    } else if (block == Blocks.LAVA ||
                            block == Blocks.FLOWING_LAVA) {

                        double distance = Math.ceil(Math.sqrt((x * x) +
                                (y * y) + (z * z)));
                        
                        double heat = 10 * (1 - (distance / 20));
                        
                        fireVar = Math.max(fireVar, heat);
                    }
                }
            }
        }
        
        temp += fireVar;
        this.debug.add("Heat block var: " + TEMP.format(fireVar));

        // Define stage
        this.tempStage = ETempStage.fromTemp(temp);

        if (GeoConfig.temperature && (this.tempStage == ETempStage.HOT ||
                this.tempStage == ETempStage.COLD)) {
            
            if (this.damageTimer == 0) {

                this.damageTimer = DAMAGE_MAX;
                
            } else if (this.damageTimer == DAMAGE_TIME) {
                
                this.player.attackEntityFrom(DamageSource.GENERIC, 1);  
            }
        } 
        
        if (this.damageTimer > 0) {

            this.damageTimer--;
        }
        
        this.debug.add("Final temp: " + TEMP.format(temp));
        this.sendDebugPacket();
        
        return oldStage != this.tempStage;
    }
    
    @Override
    public List<String> getDebug() {
        
        return this.debug;
    }
        
    /** Heals the player if possible, using up fullest FoodTypes first. */
    private void tickHeal() {
   
        if (!this.player.shouldHeal()) {
            
            return;
        }
        
        this.typesList.sort(this.comparator);
            
        for (FoodStatsPartial food : this.typesList) {

            food.heal();
        }
    }
    
    /** Recalculates the player's walk speed based on inventory. */
    private void tickSpeed() {
        
        double speed = DEFAULT_SPEED;
        
        for (ItemStack stack : this.player.inventory.armorInventory) {

            if (stack.getItem() instanceof ItemArmor) {
                
                ItemArmor armor = (ItemArmor) stack.getItem();
                
                if (armor.getArmorMaterial() == EquipMaterial.LEATHER_APPAREL ||
                        armor.getArmorMaterial() == EquipMaterial.FUR_APPAREL) {

                    speed -= 0.2;
                    
                } else if (armor.getArmorMaterial() ==
                        EquipMaterial.STEELMAIL_APPAREL) {

                    speed -= 0.4;
                    
                } else if (armor.getArmorMaterial() ==
                        EquipMaterial.STEELPLATE_APPAREL) {
                    
                    speed -= 0.5;
                }
            }
        }
        
        if (GeoBlocks.OFFHAND_ONLY.contains(this.player
                .getHeldItemOffhand().getItem())) {

            speed -= 2;
        }
        
        if (this.backpack.getItem() == GeoItems.BACKPACK) {
            
            speed -= 0.5;
        }
        
        if (this.yoke.getItem() == GeoItems.YOKE) {
            
            speed -= 1.5;
        }
                
        ESpeedStage oldStage = this.speedStage;
        
        if (GeoConfig.speed) {
        
            if (speed <= 2.3) {
                
                this.speedStage = ESpeedStage.SPEED_2_3;
                
            } else if (speed <= 2.8) {
                
                this.speedStage = ESpeedStage.SPEED_2_8;
                
            } else if (speed <= 3.3) {
                
                this.speedStage = ESpeedStage.SPEED_3_3;
                
            } else if (speed <= 3.8) {
                
                this.speedStage = ESpeedStage.SPEED_3_8;
                
            } else {
                
                this.speedStage = null;
            }
            
        } else {
            
            this.speedStage = null;
        }
        
        if (this.speedStage != oldStage) {
            
            ESpeedStage.apply(this.player.getEntityAttribute(
                    SharedMonsterAttributes.MOVEMENT_SPEED), this.speedStage);
        }
    }
    
    /** Removes pickup delays that are timed out. */
    private void tickPickup() {
        
        Iterator<Entry<Item, Long>> iterator =
                this.delays.entrySet().iterator();
        
        while (iterator.hasNext()) {
            
            Entry<Item, Long> entry = iterator.next();
            
            if (entry.getValue() + PICKUP_DELAY <
                    this.player.world.getWorldTime()) {
                
                iterator.remove();
            }
        }
    }

    @Override
    public void syncAll() {
        
        if (this.player.world.isRemote) {
            
            return;
        }
        
        for (EFoodType type : this.typesMap.keySet()) {
            
            this.sendFoodPacket(type);
        }
        
        this.sendTempPacket(this.tempStage);
    }
    
    /** Sends a packet to the client to update the EFoodType's hunger level. */
    private void sendFoodPacket(EFoodType type) {
        
        Geomastery.NETWORK.sendTo(new CPacketHunger(type,
                this.typesMap.get(type).getFoodLevel()),
                (EntityPlayerMP) this.player);
    }
    
    /** Sends a packet to the client to update the ETempStage. */
    private void sendTempPacket(ETempStage stage) {
        
        Geomastery.NETWORK.sendTo(new CPacketTemp(stage),
                (EntityPlayerMP) this.player);
    }
    
    /** Sends a packet to the client to update debug info. */
    private void sendDebugPacket() {
        
        Geomastery.NETWORK.sendTo(new CPacketDebug(this.debug),
                (EntityPlayerMP) this.player);
    }
    
    /** Receive a packet on the client to update the EFoodType hunger level. */
    public void processFoodPacket(EFoodType type, int hunger) {
        
        this.typesMap.get(type).setFoodLevel(hunger);
    }
    
    /** Receive a packet on the client to update the ETempStage. */
    public void processTempPacket(ETempStage stage) {
        
        this.tempStage = stage;
    }
    
    /** Receive a packet on the client to update the debug info. */
    public void processDebugPacket(List<String> debug) {
        
        this.debug = debug;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setTag("backpack", this.backpack.writeToNBT(new NBTTagCompound()));
        nbt.setTag("yoke", this.yoke.writeToNBT(new NBTTagCompound()));

        nbt.setInteger("speedStage", this.speedStage == null ?
                -1 : this.speedStage.ordinal());
        
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

        this.backpack = new ItemStack(nbt.getCompoundTag("backpack"));
        this.yoke = new ItemStack(nbt.getCompoundTag("yoke"));

        int speedStage = nbt.getInteger("speedStage");
        this.speedStage = speedStage == -1 ? null :
                ESpeedStage.values()[speedStage];
        
        this.damageTimer = nbt.getInteger("damageTimer");
        this.wetTimer = nbt.getInteger("wetTimer");
        this.tempStage = ETempStage.values()[nbt.getInteger("tempStage")];
        
        this.carbs.readNBT(nbt.getCompoundTag("carbs"));
        this.protein.readNBT(nbt.getCompoundTag("protein"));
        this.fruitveg.readNBT(nbt.getCompoundTag("fruitveg"));
    }
}
