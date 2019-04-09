package quaternary.zenbiomes.genlayer.math;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerNegate extends GenLayer {
	public GenLayerNegate(GenLayer parent) {
		this(0, parent);
	}
	
	public GenLayerNegate(long seed, GenLayer parent) {
		super(seed);
		this.parent = parent;
	}
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		int[] parentData = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		
		for(int i = 0; i < data.length; i++) {
			data[i] = -parentData[i];
		}
		
		return data;
	}
}
