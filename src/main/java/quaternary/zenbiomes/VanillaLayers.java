package quaternary.zenbiomes;

import io.vavr.Function1;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerZoom;
import quaternary.zenbiomes.func.Layer;
import quaternary.zenbiomes.func.LayerFactory;

public class VanillaLayers {
	public static final Layer addIsland = (parent) -> (seed) -> new GenLayerAddIsland(seed, parent.apply(seed + 1));
	
	public static final Layer seedIslands = (parent) -> GenLayerIsland::new;
	
	public static final Layer zoom = (parent) -> (seed) -> new GenLayerZoom(seed, parent.apply(seed + 1));
	
	public static Layer riverMix(LayerFactory parentRiver) {
		return (parentBiome) -> (seed) -> new GenLayerRiverMix(seed, parentBiome.apply(seed + 1), parentRiver.apply(seed + 1));
	}
}
