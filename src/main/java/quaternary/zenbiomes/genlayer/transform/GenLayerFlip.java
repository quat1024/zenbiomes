package quaternary.zenbiomes.genlayer.transform;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerFlip extends GenLayer {
	public GenLayerFlip(long seed, GenLayer parent, boolean horizontal) {
		super(seed);
		this.parent = parent;
		
		this.horizontal = horizontal;
	}
	
	//flip horizontally (over the Y axis)
	private final boolean horizontal;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		int[] parentData = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		
		for(int xOff = 0; xOff < areaWidth; xOff++) {
			for(int yOff = 0; yOff < areaHeight; yOff++) {
				int srcIndex;
				if(horizontal) {
					srcIndex = (areaWidth - xOff) + (yOff * areaWidth);
				} else {
					srcIndex = xOff + ((areaHeight - yOff) * areaWidth);
				}
				
				int dstIndex = xOff + yOff * areaWidth;
				
				data[dstIndex] = parentData[srcIndex];
			}
		}
		
		return data;
	}
}
