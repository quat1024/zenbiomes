package quaternary.zenbiomes.func;

import io.vavr.Function1;
import net.minecraft.world.gen.layer.GenLayer;

public interface LayerFactory extends Function1<Long, GenLayer> {
	static LayerFactory of(GenLayer layer) {
		return (seed) -> layer;
	}
}
