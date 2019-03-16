package quaternary.zenbiomes.layer;

import net.minecraft.world.gen.layer.GenLayer;

import java.util.function.BiFunction;

public class OneParentLayer extends AbstractLayer {
	public OneParentLayer(BiFunction<Long, GenLayer, GenLayer> constructor) {
		this.constructor = constructor;
	}
	
	private final BiFunction<Long, GenLayer, GenLayer> constructor;
	
	@Override
	public int parentCount() {
		return 1;
	}
	
	@Override
	public GenLayer bake(long seed) {
		verifyBake();
		return constructor.apply(getSeed(seed), parents[0].bake(seed + 1));
	}
}
