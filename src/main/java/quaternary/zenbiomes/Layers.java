package quaternary.zenbiomes;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerZoom;
import quaternary.zenbiomes.layer.AbstractLayer;
import quaternary.zenbiomes.layer.CompoundLayer;
import quaternary.zenbiomes.layer.LayerGenLayer;
import quaternary.zenbiomes.layer.OneParentLayer;
import quaternary.zenbiomes.layer.ZeroParentLayer;

public class Layers {
	public static AbstractLayer zoom() {
		return new OneParentLayer(GenLayerZoom::new);
	}
	
	public static AbstractLayer initIslands() {
		return new ZeroParentLayer(GenLayerIsland::new);
	}
	
	public static AbstractLayer smooth() {
		return new OneParentLayer(GenLayerSmooth::new);
	}
	
	public static AbstractLayer compound(AbstractLayer... others) {
		return new CompoundLayer(others);
	}
	
	public static AbstractLayer ofVanilla(GenLayer vanilla) {
		return new LayerGenLayer(vanilla);
	}
}
