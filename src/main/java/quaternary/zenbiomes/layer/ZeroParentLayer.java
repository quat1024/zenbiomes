package quaternary.zenbiomes.layer;

import net.minecraft.world.gen.layer.GenLayer;

import java.util.function.Function;

public class ZeroParentLayer extends AbstractLayer {
	public ZeroParentLayer(Function<Long, GenLayer> constructor) {
		this.constructor = constructor;
	}
	
	private final Function<Long, GenLayer> constructor;
	
	@Override
	public int parentCount() {
		return 0;
	}
	
	@Override
	public GenLayer bake(long seed) {
		verifyBake();
		return constructor.apply(getSeed(seed));
	}
}
