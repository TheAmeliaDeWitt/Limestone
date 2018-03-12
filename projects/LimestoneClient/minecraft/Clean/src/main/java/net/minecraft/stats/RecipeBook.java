package net.minecraft.stats;

import java.util.BitSet;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RecipeBook
{
    protected final BitSet recipes = new BitSet();
    protected final BitSet newRecipes = new BitSet();
    protected boolean isGuiOpen;
    protected boolean isFilteringCraftable;

    public void copyFrom(RecipeBook that)
    {
        this.recipes.clear();
        this.newRecipes.clear();
        this.recipes.or(that.recipes);
        this.newRecipes.or(that.newRecipes);
    }

    public void unlock(IRecipe recipe)
    {
        if (!recipe.isDynamic())
        {
            this.recipes.set(getRecipeId(recipe));
        }
    }

    public boolean isUnlocked(@Nullable IRecipe recipe)
    {
        return this.recipes.get(getRecipeId(recipe));
    }

    public void lock(IRecipe recipe)
    {
        int i = getRecipeId(recipe);
        this.recipes.clear(i);
        this.newRecipes.clear(i);
    }

    protected static int getRecipeId(@Nullable IRecipe recipe)
    {
        return CraftingManager.REGISTRY.getIDForObject(recipe);
    }

    @SideOnly(Side.CLIENT)
    public boolean isNew(IRecipe recipe)
    {
        return this.newRecipes.get(getRecipeId(recipe));
    }

    public void markSeen(IRecipe recipe)
    {
        this.newRecipes.clear(getRecipeId(recipe));
    }

    public void markNew(IRecipe recipe)
    {
        this.newRecipes.set(getRecipeId(recipe));
    }

    @SideOnly(Side.CLIENT)
    public boolean isGuiOpen()
    {
        return this.isGuiOpen;
    }

    public void setGuiOpen(boolean open)
    {
        this.isGuiOpen = open;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFilteringCraftable()
    {
        return this.isFilteringCraftable;
    }

    public void setFilteringCraftable(boolean shouldFilter)
    {
        this.isFilteringCraftable = shouldFilter;
    }
}