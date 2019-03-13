package quaternary.zenbiomes.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import quaternary.zenbiomes.etc.Etc;

public class GenLayerConstant extends GenLayer {
	public GenLayerConstant(int constant) {
		super(0L);
		this.constant = constant;
	}
	
	private final int constant;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		return Etc.fillArray(IntCache.getIntCache(areaWidth * areaHeight), constant);
	}
}
