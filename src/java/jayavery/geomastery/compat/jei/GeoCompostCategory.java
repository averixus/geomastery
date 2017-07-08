/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.util.List;
import java.util.Set;
import com.google.common.collect.Lists;
import jayavery.geomastery.compat.jei.GeoCompostCategory.Wrapper;
import jayavery.geomastery.crafting.CompostManager.CompostType;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.main.GuiHandler.GuiList;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

/** Category for Geomastery compost recipes. */
public class GeoCompostCategory extends BlankRecipeCategory<Wrapper> {
    
    /** Location of background image. */
    static final ResourceLocation BG_RES = new ResourceLocation(
            Geomastery.MODID, "textures/gui/compost_0.png");
    /** X position of background image. */
    private static final int BG_X = 24;
    /** Y position of background image. */
    private static final int BG_Y = 16;
    /** Width of background image. */
    private static final int BG_WIDTH = 131;
    /** Height of background image. */
    private static final int BG_HEIGHT = 47;
    
    /** X position of animation images origin. */
    private static final int ANIM_ORIG_X = 176;
    /** Y position of fill image origin. */
    private static final int FILL_ORIG_Y = 25;
    /** Y position of arrow image origin. */
    private static final int ARROW_ORIG_Y = 8;
    /** Width of fill image. */
    private static final int FILL_WIDTH = 13;
    /** Height of fill image. */
    private static final int FILL_HEIGHT = 40;
    /** Width of arrow image. */
    private static final int ARROW_WIDTH = 24;
    /** Height of arrow image. */
    private static final int ARROW_HEIGHT = 17;
    
    /** X position of fill animation. */
    private static final int FILL_X = 57;
    /** Y position of fill animation. */
    private static final int FILL_Y = 1;
    /** X position of input arrow animation. */
    private static final int ARROW_A_X = 25;
    /** X position of output arrow animation. */
    private static final int ARROW_B_X = 76;
    /** Y position of arrow animations. */
    private static final int ARROW_Y = 10;
    /** Ticks for the fill animation to last. */
    private static final int FILL_TICKS = 200;
    /** Ticks for progress animation to last. */
    private static final int ARROW_TICKS = 400;
    
    /** Index of input slot. */
    private static final int IN_I = 0;
    /** Index of output slot. */
    private static final int OUT_I = 1;
    /** X position of input slot. */
    private static final int IN_X = 0;
    /** X position of output slot. */
    private static final int OUT_X = 109;
    /** Y position of slots. */
    private static final int SLOT_Y = 10;

    /** Recipe tab name. */
    private final String name = GuiList.COMPOST.name;
    /** Internal unique id. */
    private final String uid = Geomastery.MODID + ":compost";
    /** Background image. */
    private final IDrawable background;
    /** Animation for compost fullness. */
    final IDrawableAnimated fill;
    /** Animation for composting progress. */
    final IDrawableAnimated arrow;
    
    public GeoCompostCategory() {
        
        this.background = GeoJei.guiHelper.createDrawable(BG_RES,
                BG_X, BG_Y, BG_WIDTH, BG_HEIGHT);
        IDrawableStatic fill = GeoJei.guiHelper.createDrawable(BG_RES,
                ANIM_ORIG_X, FILL_ORIG_Y, FILL_WIDTH, FILL_HEIGHT);
        this.fill = GeoJei.guiHelper.createAnimatedDrawable(fill,
                FILL_TICKS, IDrawableAnimated.StartDirection.BOTTOM, false);
        IDrawableStatic arrow = GeoJei.guiHelper.createDrawable(BG_RES,
                ANIM_ORIG_X, ARROW_ORIG_Y, ARROW_WIDTH, ARROW_HEIGHT);
        this.arrow = GeoJei.guiHelper.createAnimatedDrawable(arrow,
                ARROW_TICKS, IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, Wrapper wrapper,
            IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(IN_I, true, IN_X, SLOT_Y);
        stacks.init(OUT_I, false, OUT_X, SLOT_Y);
        stacks.set(ingredients);
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public void drawExtras(Minecraft minecraft) {

        this.fill.draw(minecraft, FILL_X, FILL_Y);
        this.arrow.draw(minecraft, ARROW_A_X, ARROW_Y);
        this.arrow.draw(minecraft, ARROW_B_X, ARROW_Y);
    }
    
    @Override
    public String getUid() {
        
        return this.uid;
    }
    
    @Override
    public String getTitle() {
        
        return this.name;
    }
    
    @Override
    public String getModName() {
        
        return Geomastery.NAME;
    }
    
    /** Recipe containing one compost type. */
    public static class Recipe {
                
        /** List containing one list of all possible inputs. */
        final List<List<ItemStack>> inputs = Lists.newArrayList();
        /** The compost type this recipe adds to. */
        final CompostType type;

        public Recipe(Set<ItemStack> inputs, CompostType type) {
            
            this.inputs.add(Lists.newArrayList(inputs));
            this.type = type;
        }
    }

    /** Wrapper for one recipe. */
    public static class Wrapper extends BlankRecipeWrapper {
        
        /** X position of type string. */
        private static final int STRING_X = 2;
        /** Y position of type string. */
        private static final int String_Y = 33;
        /** List of all qualities of compost for output. */
        static final List<List<ItemStack>> outputs = Lists.newArrayList();
        static { 
            NonNullList<ItemStack> allCompost = NonNullList.create();
            GeoItems.COMPOST.getSubItems(GeoItems.COMPOST,
                    CreativeTabs.MATERIALS, allCompost);
            outputs.add(allCompost);
        }

        /** The wrapped recipe. */
        private final Recipe recipe;
        
        public Wrapper(Recipe recipe) {
            
            this.recipe = recipe;
        }
        
        @Override
        public void getIngredients(IIngredients ingredients) {
            
            ingredients.setInputLists(ItemStack.class, this.recipe.inputs);
            ingredients.setOutputLists(ItemStack.class, outputs);
        }
        
        @Override
        public void drawInfo(Minecraft minecraft, int width, int height,
                int mouseX, int mouseY) {
            
            minecraft.fontRendererObj.drawString("Type: " + this.recipe.type,
                    STRING_X, String_Y, this.recipe.type.getColour().getRGB());
        }
    }
}
