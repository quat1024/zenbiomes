package quaternary.zenbiomes.layer;

import net.minecraft.world.gen.layer.GenLayer;

public class CompoundLayer extends AbstractLayer {
	public CompoundLayer(AbstractLayer... layers) {
		this.layers = layers;
		linkLayers();
	}
	
	private final AbstractLayer[] layers;
	
	private void linkLayers() {
		for(int i = 0; i < layers.length - 1; i++) {
			AbstractLayer hoh = layers[i + 1];
			if(hoh.parentCount() > 1) throw new IllegalArgumentException("can't chain layers with more than one parent");
			hoh.setParent(0, layers[i]);
		}
	}
	
	@Override
	public int parentCount() {
		return 1;
	}
	
	@Override
	public GenLayer bake(long seed) {
		verifyBake();
		return layers[layers.length - 1].bake(getSeed(seed));
	}
}
