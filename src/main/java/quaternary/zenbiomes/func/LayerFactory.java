package quaternary.zenbiomes.func;

import io.vavr.Function2;
import net.minecraft.world.World;
import net.minecraft.world.gen.layer.GenLayer;

public interface LayerFactory extends Function2<Long, World, GenLayer> {
	static LayerFactory of(GenLayer layer) {
		return (seed, world) -> layer;
	}
}
