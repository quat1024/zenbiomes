package quaternary.zenbiomes.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public abstract class AbstractGenLayerChoice extends GenLayer {
	public AbstractGenLayerChoice(GenLayer pass, GenLayer fail) {
		this(0, pass, fail);
	}
	
	public AbstractGenLayerChoice(long seed, GenLayer pass, GenLayer fail) {
		super(seed);
		this.pass = pass;
		this.parent = fail;
	}
	
	protected final GenLayer pass;
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		GenLayer fail = parent; //variable rename
		
		if(probablyAllTheSame(areaX, areaY, areaWidth, areaHeight)) {
			//Quick check: does the entire region fall inside or outside of the bounds?
			boolean pass1 = choose(areaX, areaY);
			boolean pass2 = choose(areaX + areaWidth - 1, areaY);
			boolean pass3 = choose(areaX, areaY + areaHeight - 1);
			boolean pass4 = choose(areaX + areaWidth - 1, areaY + areaHeight - 1);
			
			if(pass1 && pass2 && pass3 && pass4) return pass.getInts(areaX, areaY, areaWidth, areaHeight);
			if(!pass1 && !pass2 && !pass3 && !pass4) return fail.getInts(areaX, areaY, areaWidth, areaHeight);
		}
		
		//Quick check failed or was disabled, do it for real.
		
		int i = 0;
		int[] data = IntCache.getIntCache(areaWidth * areaHeight);
		int[] passData = pass.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] failData = fail.getInts(areaX, areaY, areaWidth, areaHeight);
		
		for(int xOff = 0; xOff < areaWidth; xOff++) {
			for(int yOff = 0; yOff < areaHeight; yOff++) {
				data[i] = choose(areaX + xOff, areaY + yOff) ? passData[i] : failData[i];
				i++;
			}
		}
		
		return data;
	}
	
	/**
	 * Choose between the two genlayers for this position.
	 */
	protected abstract boolean choose(int x, int y);
	
	/**
	 * Should this layer assume that if the four corners are in/outside the region, the whole thing is?
	 * Disable this in areas of high frequency patterns.
	 */
	protected abstract boolean probablyAllTheSame(int areaX, int areaY, int areaWidth, int areaHeight);
}
