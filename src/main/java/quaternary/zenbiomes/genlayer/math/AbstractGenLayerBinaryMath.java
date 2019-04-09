package quaternary.zenbiomes.genlayer.math;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public abstract class AbstractGenLayerBinaryMath extends GenLayer {
	public AbstractGenLayerBinaryMath(GenLayer a, GenLayer b) {
		this(0, a, b);
	}
	
	public AbstractGenLayerBinaryMath(long seed, GenLayer a, GenLayer b) {
		super(seed);
		this.parent = a;
		this.b = b;
	}
	
	private final GenLayer b;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		int[] aData = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] bData = b.getInts(areaX, areaY, areaWidth, areaHeight);
		
		for(int i = 0; i < data.length; i++) {
			data[i] = math(aData[i], bData[i]);
		}
		
		return data;
	}
	
	protected abstract int math(int one, int two);
}
