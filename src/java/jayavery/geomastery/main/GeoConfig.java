/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

public class GeoConfig {
    
    /** Name of main config category. */
    public static final String CATEGORY = "General";
    /** Name key for config strings. */
    public static final String CONFIG = "geomastery.config.";
    /** Name key for food tooltips setting. */
    public static final String FOOD_TOOLTIPS = "foodTooltips";
    /** Name key for food durability setting. */
    public static final String FOOD_DURABILITY = "foodDurability";
    /** Name key for crop biomes setting. */
    public static final String CROP_BIOMES = "cropBiomes";
    /** Name key for hide vanilla setting. */
    public static final String HIDE_VANILLA = "hideVanilla";

    /** Config setting to show food type in tooltips. */
    public static boolean foodTooltips;
    /** Config setting to show food decay as durability bar fill. */
    public static boolean foodDurability;
    /** Config setting to show valid crop biomes in tooltips. */
    public static boolean cropBiomes;
    /** Config setting to hide inaccessible vanilla items in creative/JEI. */
    public static boolean hideVanilla;
    
    /** All vanilla items which are not used or accessible. */
    public static Set<Item> vanillaItems;
    /** All vanilla blocks which are not used or accessible. */
    public static Set<Block> vanillaBlocks;

    public static void syncConfig(Configuration config) {
        
        Geomastery.LOG.info("Reading config settings");
        
        foodTooltips = config.get(CATEGORY, FOOD_TOOLTIPS, true,
                I18n.translateToLocal(CONFIG + FOOD_TOOLTIPS)).getBoolean();
        foodDurability = config.get(CATEGORY, FOOD_DURABILITY, false,
                I18n.translateToLocal(CONFIG + FOOD_DURABILITY)).getBoolean();
        cropBiomes = config.get(CATEGORY, CROP_BIOMES, true,
                I18n.translateToLocal(CONFIG + CROP_BIOMES)).getBoolean();
        hideVanilla = config.get(CATEGORY, HIDE_VANILLA, true,
                I18n.translateToLocal(CONFIG + HIDE_VANILLA)).getBoolean();
        
        if (config.hasChanged()) {

            config.save();
        }
    }
    
    public static void preInit() {
        
        if (!hideVanilla) {
            
            return;
        }
        
        vanillaItems = Sets.newHashSet(Items.PAINTING, Items.BED,
                Items.ITEM_FRAME, Items.FLOWER_POT, Items.SKULL,
                Items.ARMOR_STAND, Items.BANNER, Items.END_CRYSTAL,
                Items.ACACIA_DOOR, Items.BIRCH_DOOR, Items.DARK_OAK_DOOR,
                Items.IRON_DOOR, Items.JUNGLE_DOOR, Items.OAK_DOOR,
                Items.SPRUCE_DOOR, Items.MINECART, Items.CHEST_MINECART,
                Items.COMMAND_BLOCK_MINECART, Items.FURNACE_MINECART,
                Items.HOPPER_MINECART, Items.TNT_MINECART,
                Items.CARROT_ON_A_STICK, Items.ELYTRA, Items.BUCKET,
                Items.LAVA_BUCKET, Items.MILK_BUCKET, Items.WATER_BUCKET,
                Items.SNOWBALL, Items.PAPER, Items.BOOK, Items.SLIME_BALL,
                Items.ENDER_EYE, Items.ENDER_PEARL, Items.SPAWN_EGG,
                Items.EXPERIENCE_BOTTLE, Items.FIRE_CHARGE, Items.WRITABLE_BOOK,
                Items.WRITTEN_BOOK, Items.ENCHANTED_BOOK, Items.FIREWORK_CHARGE,
                Items.MAP, Items.DIAMOND_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR,
                Items.IRON_HORSE_ARMOR, Items.RECORD_11, Items.RECORD_13,
                Items.RECORD_BLOCKS, Items.RECORD_CAT, Items.RECORD_CHIRP,
                Items.RECORD_FAR, Items.RECORD_MALL, Items.RECORD_MELLOHI,
                Items.RECORD_STAL, Items.RECORD_STRAD, Items.RECORD_WAIT,
                Items.RECORD_WARD, Items.APPLE, Items.MUSHROOM_STEW,
                Items.BREAD, Items.PORKCHOP, Items.COOKED_PORKCHOP,
                Items.GOLDEN_APPLE, Items.FISH, Items.COOKED_FISH, Items.CAKE,
                Items.COOKIE, Items.COMPARATOR, Items.REPEATER, Items.MELON,
                Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN,
                Items.COOKED_CHICKEN, Items.ROTTEN_FLESH, Items.SPIDER_EYE,
                Items.CARROT, Items.POTATO, Items.BAKED_POTATO,
                Items.POISONOUS_POTATO, Items.PUMPKIN_PIE, Items.RABBIT,
                Items.COOKED_RABBIT, Items.RABBIT_STEW, Items.MUTTON,
                Items.COOKED_MUTTON, Items.BEETROOT_SOUP, Items.DIAMOND_SHOVEL,
                Items.GOLDEN_SHOVEL, Items.IRON_SHOVEL, Items.STONE_SHOVEL,
                Items.WOODEN_SHOVEL, Items.DIAMOND_PICKAXE, Items.BEETROOT,
                Items.GOLDEN_PICKAXE, Items.IRON_PICKAXE, Items.STONE_PICKAXE,
                Items.WOODEN_PICKAXE, Items.DIAMOND_AXE, Items.GOLDEN_AXE,
                Items.IRON_AXE, Items.STONE_AXE, Items.WOODEN_AXE,
                Items.FLINT_AND_STEEL, Items.COMPASS, Items.DIAMOND_HOE,
                Items.GOLDEN_HOE, Items.IRON_HOE, Items.STONE_HOE,
                Items.WOODEN_HOE, Items.CLOCK, Items.SHEARS, Items.LEAD,
                Items.NAME_TAG, Items.BOW, Items.ARROW, Items.DIAMOND_SWORD,
                Items.GOLDEN_SWORD, Items.IRON_SWORD, Items.STONE_SWORD,
                Items.WOODEN_SWORD, Items.CHAINMAIL_HELMET,
                Items.DIAMOND_HELMET, Items.GOLDEN_HELMET, Items.IRON_HELMET,
                Items.LEATHER_HELMET, Items.CHAINMAIL_CHESTPLATE,
                Items.DIAMOND_CHESTPLATE, Items.GOLDEN_CHESTPLATE,
                Items.IRON_CHESTPLATE, Items.LEATHER_CHESTPLATE,
                Items.CHAINMAIL_LEGGINGS, Items.DIAMOND_LEGGINGS,
                Items.GOLDEN_LEGGINGS, Items.IRON_LEGGINGS,
                Items.LEATHER_LEGGINGS, Items.CHAINMAIL_BOOTS,
                Items.DIAMOND_BOOTS, Items.GOLDEN_BOOTS, Items.IRON_BOOTS,
                Items.LEATHER_BOOTS, Items.SPECTRAL_ARROW, Items.TIPPED_ARROW,
                Items.SHIELD, Items.TOTEM, Items.GHAST_TEAR, Items.GLASS_BOTTLE,
                Items.POTIONITEM, Items.LINGERING_POTION, Items.SPLASH_POTION,
                Items.FERMENTED_SPIDER_EYE, Items.BLAZE_POWDER,
                Items.MAGMA_CREAM, Items.BREWING_STAND, Items.CAULDRON,
                Items.SPECKLED_MELON, Items.GOLDEN_CARROT, Items.RABBIT_FOOT,
                Items.DRAGON_BREATH, Items.IRON_INGOT, Items.BOWL, Items.STRING,
                Items.GUNPOWDER, Items.WHEAT_SEEDS, Items.WHEAT,
                Items.CLAY_BALL, Items.GLOWSTONE_DUST, Items.DYE,
                Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS,
                Items.BLAZE_ROD, Items.GOLD_NUGGET, Items.NETHER_WART,
                Items.NETHER_STAR, Items.NETHERBRICK, Items.QUARTZ,
                Items.PRISMARINE_CRYSTALS, Items.PRISMARINE_SHARD,
                Items.CHORUS_FRUIT, Items.CHORUS_FRUIT_POPPED,
                Items.SHULKER_SHELL, /* Iron nugget */Items.field_191525_da);
        
        vanillaBlocks = Sets.newHashSet(Blocks.PLANKS, Blocks.SPONGE,
                Blocks.GLASS, Blocks.WOOL, Blocks.LAPIS_BLOCK,
                Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.STONE_SLAB,
                Blocks.STONE_SLAB2, Blocks.BRICK_BLOCK, Blocks.BOOKSHELF,
                Blocks.ACACIA_STAIRS, Blocks.BIRCH_STAIRS, Blocks.BRICK_STAIRS,
                Blocks.DARK_OAK_STAIRS, Blocks.JUNGLE_STAIRS,
                Blocks.NETHER_BRICK_STAIRS, Blocks.OAK_STAIRS,
                Blocks.PURPUR_STAIRS, Blocks.QUARTZ_STAIRS,
                Blocks.RED_SANDSTONE_STAIRS, Blocks.SANDSTONE_STAIRS,
                Blocks.SPRUCE_STAIRS, Blocks.STONE_BRICK_STAIRS,
                Blocks.STONE_STAIRS, Blocks.DIAMOND_BLOCK, Blocks.PUMPKIN,
                Blocks.LIT_PUMPKIN, Blocks.NETHER_BRICK,
                Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS,
                Blocks.NETHER_WART_BLOCK, Blocks.NETHERRACK,
                Blocks.RED_NETHER_BRICK, Blocks.SOUL_SAND, Blocks.GLOWSTONE,
                Blocks.STAINED_GLASS, Blocks.STONEBRICK, Blocks.MELON_BLOCK,
                Blocks.END_BRICKS, Blocks.END_STONE, Blocks.PURPUR_SLAB,
                Blocks.WOODEN_SLAB, Blocks.EMERALD_BLOCK, Blocks.QUARTZ_BLOCK,
                Blocks.QUARTZ_ORE, Blocks.QUARTZ_STAIRS, Blocks.COAL_BLOCK,
                Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.MAGMA,
                Blocks.CHEST, Blocks.CRAFTING_TABLE, Blocks.FURNACE,
                Blocks.JUKEBOX, Blocks.ACACIA_FENCE, Blocks.BIRCH_FENCE,
                Blocks.DARK_OAK_FENCE, Blocks.JUNGLE_FENCE,
                Blocks.NETHER_BRICK_FENCE, Blocks.OAK_FENCE,
                Blocks.SPRUCE_FENCE, Blocks.MONSTER_EGG, Blocks.IRON_BARS,
                Blocks.GLASS_PANE, Blocks.ENCHANTING_TABLE,
                Blocks.END_PORTAL_FRAME, Blocks.ENDER_CHEST,
                Blocks.COBBLESTONE_WALL, Blocks.ANVIL,
                Blocks.STAINED_GLASS_PANE, Blocks.CARPET, Blocks.SLIME_BLOCK,
                Blocks.END_ROD, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT,
                Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX,
                Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX,
                Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX,
                Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX,
                Blocks.MAGENTA_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX,
                Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,
                Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX,
                Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX,
                Blocks.YELLOW_SHULKER_BOX, Blocks.DISPENSER, Blocks.DROPPER,
                Blocks.NOTEBLOCK, Blocks.PISTON, Blocks.STICKY_PISTON,
                Blocks.TNT, Blocks.LEVER, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
                Blocks.STONE_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE,
                Blocks.REDSTONE_TORCH, Blocks.STONE_BUTTON,
                Blocks.WOODEN_BUTTON, Blocks.TRAPDOOR, Blocks.ACACIA_FENCE_GATE,
                Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE,
                Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE,
                Blocks.SPRUCE_FENCE_GATE, Blocks.REDSTONE_LAMP,
                Blocks.TRIPWIRE_HOOK, Blocks.TRAPPED_CHEST,
                Blocks.DAYLIGHT_DETECTOR, Blocks.REDSTONE_BLOCK,
                Blocks.HOPPER, Blocks.IRON_TRAPDOOR, Blocks.RAIL,
                Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL,
                Blocks.GOLDEN_RAIL, Blocks.BEACON, Blocks.OBSERVER);

        for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
           
            tab.setRelevantEnchantmentTypes((EnumEnchantmentType[]) null);
        }
       
        vanillaItems.forEach((i) -> i.setCreativeTab(null));
        vanillaBlocks.forEach((b) -> b.setCreativeTab(null));
    }
}
