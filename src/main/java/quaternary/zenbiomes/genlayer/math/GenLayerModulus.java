package quaternary.zenbiomes.genlayer.math;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerModulus extends AbstractGenLayerBinaryMath {
	public GenLayerModulus(GenLayer a, GenLayer b) {
		super(a, b);
	}
	
	@Override
	protected int math(int one, int two) {
		if(two == 0) return 0;
		else return one % two;
	}
}
