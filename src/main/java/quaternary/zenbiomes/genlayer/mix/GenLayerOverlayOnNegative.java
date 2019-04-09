package quaternary.zenbiomes.genlayer.mix;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerOverlayOnNegative extends GenLayer {
	public GenLayerOverlayOnNegative(GenLayer foreground, GenLayer background) {
		super(0);
		this.parent = background;
		this.foreground = foreground;
	}
	
	private final GenLayer foreground;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		System.arraycopy(parent.getInts(areaX, areaY, areaWidth, areaHeight), 0, data, 0, areaWidth * areaHeight);
		
		int[] fgData = null;
		
		for(int i = 0; i < data.length; i++) {
			if(data[i] < 0) {
				if(fgData == null) fgData = foreground.getInts(areaX, areaY, areaWidth, areaHeight);
				data[i] = fgData[i];
			}
		}
		
		return data;
	}
}
