package quaternary.zenbiomes;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.*;
import quaternary.zenbiomes.func.Layer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.function.BiFunction;

@ZenClass("mods.zenbiomes.VanillaLayers")
@ZenRegister
public class VanillaLayers {
	//add island
	@ZenProperty
	public static final Layer addIsland = fromConstructor(GenLayerAddIsland::new);
	
	//add mushroom island
	@ZenProperty
	public static final Layer addMushroomIsland = fromConstructor(GenLayerAddMushroomIsland::new);
	
	//add snow
	@ZenProperty
	public static final Layer addSnow = fromConstructor(GenLayerAddSnow::new);
	
	//biome
	@ZenProperty
	public static final Layer biome = (parent) -> (seed, world) -> {
		//So for customized worlds where you can set the biome to a fixed biome...
		//This is where that is applied.
		//However there doesn't seem to be a way to get the "real" customized world chunkgensettings.
		//It's turned into a string, and only saved as a string from places I can reach it from World.
		//No matter, though. I'll just make my own. The only prop that matters is fixed biome anyways.
		ChunkGeneratorSettings settings = null;
		if(world.getWorldType() == WorldType.CUSTOMIZED && world.getBiomeProvider().isFixedBiome()) {
			ChunkGeneratorSettings.Factory oof = new ChunkGeneratorSettings.Factory();
			oof.fixedBiome = Biome.getIdForBiome(world.getBiomeProvider().getFixedBiome());
			settings = oof.build();
		}
		
		return new GenLayerBiome(seed, parent.apply(seed + 1, world), world.getWorldType(), settings);
	};
	//genlayerbiome (needs worldtype, chunkgensettings...?)
	
	@ZenProperty
	public static final Layer biomeEdge = fromConstructor(GenLayerBiomeEdge::new);
	
	@ZenProperty
	public static final Layer deepOcean = fromConstructor(GenLayerDeepOcean::new);
	
	//definitely look at these names...
	@ZenProperty
	public static final Layer temperatureEdge = (parent) -> (seed, world) -> new GenLayerEdge(seed, parent.apply(seed + 1, world), GenLayerEdge.Mode.COOL_WARM);
	
	@ZenProperty
	public static final Layer iceEdge = (parent) -> (seed, world) -> new GenLayerEdge(seed, parent.apply(seed + 1, world), GenLayerEdge.Mode.HEAT_ICE);
	
	@ZenProperty
	public static final Layer specialEdge = (parent) -> (seed, world) -> new GenLayerEdge(seed, parent.apply(seed + 1, world), GenLayerEdge.Mode.SPECIAL);
	
	@ZenProperty
	public static final Layer fuzzyZoom = fromConstructor(GenLayerFuzzyZoom::new);
	
	//hills(riverLayer)
	@ZenMethod
	public static Layer hills(Layer parentRiver) {
		return (parentBiome) -> (seed, world) -> new GenLayerHills(seed, parentBiome.apply(seed + 1, world), parentRiver.flatten().apply(seed + 1, world));
	}
	
	//hills.river(riverLayer)
	@ZenProperty
	public static DSL.Hills hills = VanillaLayers::hills;
	
	@ZenProperty
	public static final Layer island = (parent) -> (seed, world) -> new GenLayerIsland(seed);
	
	@ZenProperty
	public static final Layer rareBiomes = fromConstructor(GenLayerRareBiome::new);
	
	@ZenProperty
	public static final Layer breakOceans = fromConstructor(GenLayerRemoveTooMuchOcean::new);
	
	@ZenProperty
	public static final Layer riverFilter = fromConstructor(GenLayerRiver::new);
	
	@ZenProperty
	public static final Layer riverInit = fromConstructor(GenLayerRiverInit::new);
	
	//riverMix(riverLayer)
	@ZenMethod
	public static Layer riverMix(Layer parentRiver) {
		return (parentBiome) -> (seed, world) -> new GenLayerRiverMix(seed, parentBiome.apply(seed + 1, world), parentRiver.flatten().apply(seed + 1, world));
	}
	
	//riverMix.river(riverLayer)
	@ZenProperty
	public static DSL.River riverMix = VanillaLayers::riverMix;
	
	@ZenProperty
	public static final Layer shore = fromConstructor(GenLayerShore::new);
	
	@ZenProperty
	public static final Layer smooth = fromConstructor(GenLayerSmooth::new);
	
	@ZenProperty
	public static final Layer voronoiZoom = fromConstructor(GenLayerVoronoiZoom::new);
	
	@ZenProperty
	public static final Layer zoom = fromConstructor(GenLayerZoom::new);
	
	public static class DSL {
		@ZenClass("mods.zenbiomes.VanillaLayersDSLHills")
		@ZenRegister
		public interface Hills {
			@ZenMethod
			Layer river(Layer river);
		}
		
		@ZenClass("mods.zenbiomes.VanillaLayersDSLRiver")
		@ZenRegister
		public interface River extends Hills {}
		
	}
	
	private static Layer fromConstructor(BiFunction<Long, GenLayer, GenLayer> cons) {
		return (parent) -> (seed, world) -> cons.apply(seed, parent.apply(seed + 1, world));
	}
}
