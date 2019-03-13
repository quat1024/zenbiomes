package quaternary.zenbiomes.genlayer;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerThrowError extends GenLayer {
	public GenLayerThrowError(String err) {
		super(0L);
		this.err = err;
	}
	
	public GenLayerThrowError() {
		this("Your exception, as requested!");
	}
	
	private final String err;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		throw new RuntimeException(err);
	}
}
