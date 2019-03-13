package quaternary.zenbiomes.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerReplace extends GenLayer {
	public GenLayerReplace(int src, int dst, GenLayer parent) {
		super(0L);
		this.parent = parent;
		this.src = src;
		this.dst = dst;
	}
	
	private final int src;
	private final int dst;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		int[] parentData = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		
		for(int i = 0; i < data.length; i++) {
			int p = parentData[i];
			
			if(p == src) {
				data[i] = dst;
			} else data[i] = p;
		}
		
		return data;
	}
}
