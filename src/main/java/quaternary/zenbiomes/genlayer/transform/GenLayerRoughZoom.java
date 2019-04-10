package quaternary.zenbiomes.genlayer.transform;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRoughZoom extends GenLayer {
	public GenLayerRoughZoom(long seed, GenLayer parent) {
		super(seed);
		this.parent = parent;
	}
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		int[] parentData = parent.getInts(areaX / 2, areaY / 2, areaWidth / 2, areaHeight / 2);
		
		int halfWidth = areaWidth / 2;
		
		for(int xOff = 0; xOff < areaWidth; xOff++) {
			for(int yOff = 0; yOff < areaHeight; yOff++) {
				int dstIndex = xOff + yOff * areaWidth;
				int srcIndex = ((xOff / 2) + (yOff / 2)) * halfWidth;
				
				data[dstIndex] = parentData[srcIndex];
				dstIndex++;
			}
		}
		
		return data;
	}
}
