package quaternary.zenbiomes.genlayer.math;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerSubtract extends AbstractGenLayerBinaryMath {
	public GenLayerSubtract(GenLayer a, GenLayer b) {
		super(a, b);
	}
	
	@Override
	protected int math(int one, int two) {
		return one - two;
	}
}
