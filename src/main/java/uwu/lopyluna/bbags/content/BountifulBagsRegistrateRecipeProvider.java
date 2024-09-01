package uwu.lopyluna.bbags.content;

import com.google.gson.JsonObject;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BountifulBagsRegistrateRecipeProvider extends RegistrateRecipeProvider {
    public BountifulBagsRegistrateRecipeProvider(AbstractRegistrate<?> owner, PackOutput output) {
        super(owner, output);
    }
    public <T extends ItemLike> void cooking(DataIngredient pIngredient, RecipeCategory pCategory, Supplier<? extends T> pResult, float pExperience) {
        cooking(pIngredient, pCategory, pResult, pExperience, DEFAULT_SMELT_TIME);
    }
    public <T extends ItemLike> void alloying(DataIngredient pIngredientA, DataIngredient pIngredientB, RecipeCategory pCategory, Supplier<? extends T> pResult, float pExperience) {
        alloying(pIngredientA, pIngredientB, pCategory, pResult, pExperience, DEFAULT_SMELT_TIME);
    }
    public <T extends ItemLike> void cooking(DataIngredient pIngredient, RecipeCategory pCategory, Supplier<? extends T> pResult, float pExperience, int pCookingTime) {
        kiln(pIngredient, pIngredient, pCategory, pResult, pExperience, pCookingTime);
    }
    public <T extends ItemLike> void alloying(DataIngredient pIngredientA, DataIngredient pIngredientB, RecipeCategory pCategory, Supplier<? extends T> pResult, float pExperience, int pCookingTime) {
        kiln(pIngredientA, pIngredientB, pCategory, pResult, pExperience, pCookingTime);
    }
    public <T extends ItemLike> void kiln(DataIngredient pIngredientA, DataIngredient pIngredientB, RecipeCategory pCategory, Supplier<? extends T> pResult, float pExperience, int pCookingTime) {
        KilnCookingRecipeBuild.smelting(pIngredientA, pIngredientB,  pCategory, pResult.get(), pExperience, pCookingTime)
                .unlockedBy("has_" + safeName(pIngredientA) + "_or_" + safeName(pIngredientB), pIngredientA.getCritereon(this))
                .save(this, safeId(pResult.get()) + "_from_" + safeName(pIngredientA) + "_and_" + safeName(pIngredientB) + "_kiln");
    }

    public <T extends ItemLike> void sawmill(DataIngredient pIngredient, RecipeCategory pCategory, Supplier<? extends T> pResult) {
        sawmill(pIngredient, pCategory, pResult, 1);
    }

    public <T extends ItemLike> void sawmill(DataIngredient pIngredient, RecipeCategory pCategory, Supplier<? extends T> pResult, int resultAmount) {
        sawmill(pIngredient, pCategory, pResult.get(), resultAmount)
                .unlockedBy("has_" + safeName(pIngredient), pIngredient.getCritereon(this))
                .save(this, safeId(pResult.get()) + "_from_" + safeName(pIngredient) + "_sawmill");
    }
//REPLACE RECIPE WITH SAWMILL
    public static SingleItemRecipeBuilder sawmill(Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, int pCount) {
        return new SingleItemRecipeBuilder(pCategory, RecipeSerializer.STONECUTTER, pIngredient, pResult, pCount);
    }






    public static class KilnCookingRecipeBuild implements RecipeBuilder {
        private final RecipeCategory category;
        private final CookingBookCategory bookCategory;
        private final Item result;
        private final Ingredient inputA;
        private final Ingredient inputB;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
        @javax.annotation.Nullable
        private String group;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

        private KilnCookingRecipeBuild(RecipeCategory pCategory, CookingBookCategory pBookCategory, ItemLike pResult, Ingredient pIngredientA, Ingredient pIngredientB, float pExperience, int pCookingTime, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
            this.category = pCategory;
            this.bookCategory = pBookCategory;
            this.result = pResult.asItem();
            this.inputA = pIngredientA;
            this.inputB = pIngredientB;
            this.experience = pExperience;
            this.cookingTime = pCookingTime;
            this.serializer = pSerializer;
        }

        //REPLACE RECIPE WITH KILN RECIPE
        public static KilnCookingRecipeBuild smelting(Ingredient pIngredientA, Ingredient pIngredientB, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime) {
            return new KilnCookingRecipeBuild(pCategory, determineSmeltingRecipeCategory(pResult),
                    pResult, pIngredientA, pIngredientB, pExperience, pCookingTime, RecipeSerializer.SMELTING_RECIPE
            );
        }

        private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike pResult) {
            return pResult.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
        }


        public KilnCookingRecipeBuild unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
            this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
            return this;
        }

        public KilnCookingRecipeBuild group(@javax.annotation.Nullable String pGroupName) {
            this.group = pGroupName;
            return this;
        }

        public Item getResult() {
            return this.result;
        }

        public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
            this.ensureValid(pRecipeId);
            this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
            pFinishedRecipeConsumer.accept(new KilnCookingRecipeBuild.Result(pRecipeId, this.group == null ? "" : this.group, this.bookCategory, this.inputA, this.inputB, this.result, this.experience, this.cookingTime, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));
        }

        private void ensureValid(ResourceLocation pId) {
            if (this.advancement.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + pId);
            }
        }

        static class Result implements FinishedRecipe {
            private final ResourceLocation id;
            private final String group;
            private final CookingBookCategory category;
            private final Ingredient ingredientA;
            private final Ingredient ingredientB;
            private final Item result;
            private final float experience;
            private final int cookingTime;
            private final Advancement.Builder advancement;
            private final ResourceLocation advancementId;
            private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

            public Result(ResourceLocation pId, String pGroup, CookingBookCategory pCategory, Ingredient pIngredientA, Ingredient pIngredientB, Item pResult, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
                this.id = pId;
                this.group = pGroup;
                this.category = pCategory;
                this.ingredientA = pIngredientA;
                this.ingredientB = pIngredientB;
                this.result = pResult;
                this.experience = pExperience;
                this.cookingTime = pCookingTime;
                this.advancement = pAdvancement;
                this.advancementId = pAdvancementId;
                this.serializer = pSerializer;
            }

            public void serializeRecipeData(JsonObject pJson) {
                if (!this.group.isEmpty()) {
                    pJson.addProperty("group", this.group);
                }

                pJson.addProperty("category", this.category.getSerializedName());
                pJson.add("inputA", this.ingredientA.toJson());
                pJson.add("inputB", this.ingredientB.toJson());
                pJson.addProperty("result", BuiltInRegistries.ITEM.getKey(this.result).toString());
                pJson.addProperty("experience", this.experience);
                pJson.addProperty("cookingtime", this.cookingTime);
            }

            public RecipeSerializer<?> getType() {
                return this.serializer;
            }

            public ResourceLocation getId() {
                return this.id;
            }

            @javax.annotation.Nullable
            public JsonObject serializeAdvancement() {
                return this.advancement.serializeToJson();
            }

            @Nullable
            public ResourceLocation getAdvancementId() {
                return this.advancementId;
            }
        }
    }
}
