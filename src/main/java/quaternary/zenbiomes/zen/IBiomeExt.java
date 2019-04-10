package quaternary.zenbiomes.zen;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBiome;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import quaternary.zenbiomes.genlayer.GenLayerConstant;
import quaternary.zenbiomes.layer.Layer;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.world.IBiome")
@ZenRegister
public class IBiomeExt {
	@ZenMethod
	@ZenGetter("numericId")
	public static int getNumericId(IBiome biome) {
		return Biome.getIdForBiome(CraftTweakerMC.getBiome(biome));
	}
	
	@ZenCaster
	public static int asInt(IBiome biome) {
		return getNumericId(biome);
	}
	
	@ZenCaster
	public static Layer asLayer(IBiome biome) {
		return (parent) -> (seed, wtype) -> new GenLayerConstant(asInt(biome));
	}
}
