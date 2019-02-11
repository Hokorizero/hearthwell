package wolforce.registry;

import static wolforce.Main.asul_machine_case;
import static wolforce.Main.azurite;
import static wolforce.Main.citrinic_sand;
import static wolforce.Main.citrinic_stone;
import static wolforce.Main.compressed_clay;
import static wolforce.Main.crystal_block;
import static wolforce.Main.dust_block;
import static wolforce.Main.glowstone_ore;
import static wolforce.Main.heavy_ingot;
import static wolforce.Main.heavy_mesh;
import static wolforce.Main.leaf_mesh;
import static wolforce.Main.liquid_souls;
import static wolforce.Main.metaldiamond_block;
import static wolforce.Main.mystic_iron_ingot;
import static wolforce.Main.onyx;
import static wolforce.Main.producer;
import static wolforce.Main.quartz_ore;
import static wolforce.Main.raw_mystic_iron;
import static wolforce.Main.raw_repairing_paste;
import static wolforce.Main.raw_soulsteel;
import static wolforce.Main.repairing_paste;
import static wolforce.Main.scorch_glass;
import static wolforce.Main.scorch_grit;
import static wolforce.Main.smooth_azurite;
import static wolforce.Main.smooth_onyx;
import static wolforce.Main.soulsteel_ingot;
import static wolforce.Main.wheat_flour;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolforce.Hwell;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.items.ItemLoot;
import wolforce.items.ItemMystDust;
import wolforce.recipes.RecipeCoring;
import wolforce.recipes.RecipeCrushing;
import wolforce.recipes.RecipeFreezer;
import wolforce.recipes.RecipeGrinding;
import wolforce.recipes.RecipePuller;
import wolforce.recipes.RecipeRepairingPaste;
import wolforce.recipes.RecipeSeparator;
import wolforce.recipes.RecipeTube;

@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class RegisterRecipes {

	private static final String INNER_RECIPES_FILE = "recipes.json";

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		File recipesFile = new File(HwellConfig.recipeFileLocation);

		// if file does not exist, write default file
		if (!recipesFile.exists())
			try {
				writeRecipesFile(HwellConfig.recipeFileLocation);
			} catch (IOException e) {
				throw new RuntimeException("Could not initialise the Recipes File!! Game will crash.");
			}
		JsonObject recipes = null, defaultRecipes = null;
		boolean errored = false;
		try {
			recipes = Util.readJson(HwellConfig.recipeFileLocation).getAsJsonObject();
		} catch (Exception e) {
			System.err.println("Error while reading the Recipes File! Defaulting all recipes.");
			errored = true;
		}
		try {
			defaultRecipes = Util.readJson("/assets/hwell/" + INNER_RECIPES_FILE, true).getAsJsonObject();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (errored || recipes == null)
			recipes = defaultRecipes;

		String recipeName = "separator_recipes";
		RecipeSeparator.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "freezer_recipes";
		RecipeFreezer.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "puller_recipes";
		RecipePuller.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "crushing_recipes";
		// RecipeCrushing.initRecipes((recipes.has(recipeName) ? recipes :
		// defaultRecipes).get(recipeName).getAsJsonArray());
		RecipeCrushing.initRecipes();

		recipeName = "tube_recipes";
		RecipeTube.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "grinding_recipes";
		// RecipeGrinding.initRecipes((recipes.has(recipeName) ? recipes :
		// defaultRecipes).get(recipeName).getAsJsonArray());
		RecipeGrinding.initRecipes();

		recipeName = "coring_recipes";
		// RecipeCoring.initRecipes((recipes.has(recipeName) ? recipes :
		// defaultRecipes).get(recipeName).getAsJsonArray());
		RecipeCoring.initRecipes();

		recipeName = "myst_dust_recipes";
		// ItemMystDust.initRecipes((recipes.has(recipeName) ? recipes :
		// defaultRecipes).get(recipeName).getAsJsonArray());
		ItemMystDust.initRecipes();

		recipeName = "repairing_paste_recipes";
		// RecipeRepairingPaste.initRecipes((recipes.has(recipeName) ? recipes :
		// defaultRecipes).get(recipeName).getAsJsonArray());
		RecipeRepairingPaste.initRecipes();

		Main.initShards();
		ItemLoot.setLootTables();

		GameRegistry.addSmelting(glowstone_ore, new ItemStack(Items.GLOWSTONE_DUST), 0.5f);
		GameRegistry.addSmelting(quartz_ore, new ItemStack(Items.QUARTZ), 0.5f);
		GameRegistry.addSmelting(heavy_mesh, new ItemStack(heavy_ingot, 2), 0.5f);
		GameRegistry.addSmelting(dust_block, new ItemStack(Blocks.GLASS, 2), 0f);
		GameRegistry.addSmelting(leaf_mesh, new ItemStack(crystal_block, 1), .5f);
		GameRegistry.addSmelting(compressed_clay, new ItemStack(Blocks.HARDENED_CLAY, 9), 0f);
		GameRegistry.addSmelting(scorch_grit, new ItemStack(scorch_glass), 0f);
		GameRegistry.addSmelting(azurite, new ItemStack(smooth_azurite), 0f);
		GameRegistry.addSmelting(citrinic_stone, new ItemStack(citrinic_sand), 0f);
		GameRegistry.addSmelting(onyx, new ItemStack(smooth_onyx), 0f);
		GameRegistry.addSmelting(raw_mystic_iron, new ItemStack(mystic_iron_ingot), 1f);
		GameRegistry.addSmelting(raw_soulsteel, new ItemStack(soulsteel_ingot), 1f);
		GameRegistry.addSmelting(raw_repairing_paste, new ItemStack(repairing_paste), 1f);
		GameRegistry.addSmelting(wheat_flour, new ItemStack(Items.BREAD), 1f);

		GameRegistry.addShapedRecipe(Util.res("producer." + producer.getRegistryName().getResourcePath()), Util.res("hwell.producer"),
				new ItemStack(Main.producer), //
				"ABA", "MCM", "MYM", //
				'B', FluidUtil.getFilledBucket(new FluidStack(liquid_souls, Fluid.BUCKET_VOLUME)), //
				'A', smooth_azurite, //
				'M', metaldiamond_block, //
				'C', asul_machine_case, //
				'Y', crystal_block);
	}

	private static void writeRecipesFile(String destination) throws IOException {
		// ResourceLocation loc = new ResourceLocation("hwell:" + INNER_RECIPES_FILE);
		// String str = "assets/" + loc.toString().replace(":", "/");
		InputStream inStream = Hwell.class.getResourceAsStream("/assets/hwell/" + INNER_RECIPES_FILE);
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

		FileWriter outstream = new FileWriter(destination, false);
		BufferedWriter out = new BufferedWriter(outstream);

		String aLine = null;
		while ((aLine = in.readLine()) != null) {
			out.write(aLine);
			out.newLine();
		}
		in.close();
		out.close();
	}

	public static interface HwellRecipe {

	}
}
