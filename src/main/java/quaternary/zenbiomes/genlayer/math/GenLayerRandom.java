package quaternary.zenbiomes.genlayer.math;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRandom extends GenLayer {
	public GenLayerRandom(long seed, int low, int high) {
		super(seed);
		this.low = low;
		this.high = high;
	}
	
	private final int low;
	private final int high;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		
		int i = 0;
		for(int x = areaX; x < areaX + areaWidth; x++) {
			for(int y = areaY; y < areaY + areaHeight; y++) {
				initChunkSeed(x, y);
				int random = nextInt(high - low);
				data[i] = random + low;
				i++;
			}
		}
		
		return data;
	}
}
