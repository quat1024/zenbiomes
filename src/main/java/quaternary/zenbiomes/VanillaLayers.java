package quaternary.zenbiomes;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.*;
import quaternary.zenbiomes.func.Layer;
import quaternary.zenbiomes.func.LayerFactory;

import java.util.function.BiFunction;

public class VanillaLayers {
	//add island
	public static final Layer addIsland = fromConstructor(GenLayerAddIsland::new);
	
	//add mushroom island
	public static final Layer addMushroomIsland = fromConstructor(GenLayerAddMushroomIsland::new);
	
	//add snow
	public static final Layer addSnow = fromConstructor(GenLayerAddSnow::new);
	
	//biome
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
	
	public static final Layer biomeEdge = fromConstructor(GenLayerBiomeEdge::new);
	
	public static final Layer deepOcean = fromConstructor(GenLayerDeepOcean::new);
	
	//definitely look at these names...
	public static final Layer temperatureEdge = (parent) -> (seed, world) -> new GenLayerEdge(seed, parent.apply(seed + 1, world), GenLayerEdge.Mode.COOL_WARM);
	
	public static final Layer iceEdge = (parent) -> (seed, world) -> new GenLayerEdge(seed, parent.apply(seed + 1, world), GenLayerEdge.Mode.HEAT_ICE);
	
	public static final Layer specialEdge = (parent) -> (seed, world) -> new GenLayerEdge(seed, parent.apply(seed + 1, world), GenLayerEdge.Mode.SPECIAL);
	
	public static final Layer fuzzyZoom = fromConstructor(GenLayerFuzzyZoom::new);
	
	//hills(riverLayer)
	public static Layer hills(Layer parentRiver) {
		return (parentBiome) -> (seed, world) -> new GenLayerHills(seed, parentBiome.apply(seed + 1, world), parentRiver.flatten().apply(seed + 1, world));
	}
	
	//hills.river(riverLayer)
	public static DSL.Hills_0 hills = VanillaLayers::hills;
	
	public static final Layer island = (parent) -> (seed, world) -> new GenLayerIsland(seed);
	
	public static final Layer rareBiomes = fromConstructor(GenLayerRareBiome::new);
	
	public static final Layer breakOceans = fromConstructor(GenLayerRemoveTooMuchOcean::new);
	
	public static final Layer riverFilter = fromConstructor(GenLayerRiver::new);
	
	public static final Layer riverInit = fromConstructor(GenLayerRiverInit::new);
	
	//riverMix(riverLayer)
	public static Layer riverMix(Layer parentRiver) {
		return (parentBiome) -> (seed, world) -> new GenLayerRiverMix(seed, parentBiome.apply(seed + 1, world), parentRiver.flatten().apply(seed + 1, world));
	}
	
	//riverMix.river(riverLayer)
	public static DSL.River_0 riverMix = VanillaLayers::riverMix;
	
	public static final Layer shore = fromConstructor(GenLayerShore::new);
	
	public static final Layer smooth = fromConstructor(GenLayerSmooth::new);
	
	public static final Layer voronoiZoom = fromConstructor(GenLayerVoronoiZoom::new);
	
	public static final Layer zoom = fromConstructor(GenLayerZoom::new);
	
	public static class DSL {
		public interface Hills_0 {
			Layer river(Layer river);
		}
		
		public interface River_0 extends Hills_0 {}
	}
	
	private static Layer fromConstructor(BiFunction<Long, GenLayer, GenLayer> cons) {
		return (parent) -> (seed, world) -> cons.apply(seed, parent.apply(seed + 1, world));
	}
}
