package quaternary.zenbiomes.layer;

import net.minecraft.world.gen.layer.GenLayer;

public class LayerGenLayer extends AbstractLayer {
	public LayerGenLayer(GenLayer layer) {
		this.layer = layer;
	}
	
	private final GenLayer layer;
	
	@Override
	public int parentCount() {
		return 0; //this is where the abstraction breaks down
	}
	
	@Override
	public GenLayer bake(long seed) {
		verifyBake();
		return layer;
	}
}
