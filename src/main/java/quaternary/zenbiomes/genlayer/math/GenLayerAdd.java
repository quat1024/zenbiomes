package quaternary.zenbiomes.genlayer.math;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerAdd extends AbstractGenLayerBinaryMath {
	public GenLayerAdd(GenLayer a, GenLayer b) {
		super(a, b);
	}
	
	@Override
	protected int math(int one, int two) {
		return one + two;
	}
}
