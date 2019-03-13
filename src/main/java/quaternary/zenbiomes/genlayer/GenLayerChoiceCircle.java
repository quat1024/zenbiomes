package quaternary.zenbiomes.genlayer;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerChoiceCircle extends AbstractGenLayerChoice {
	public GenLayerChoiceCircle(int centerX, int centerY, int radius, GenLayer inside, GenLayer outside) {
		super(inside, outside);
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.radiusSq = radius * radius;
	}
	
	private final int centerX;
	private final int centerY;
	private final int radiusSq;
	
	@Override
	protected boolean choose(int x, int y) {
		return distSq(centerX, centerY, x, y) <= radiusSq;
	}
	
	private int distSq(int x1, int y1, int x2, int y2) {
		return ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2));
	}
	
	@Override
	protected boolean probablyAllTheSame(int areaX, int areaY, int areaWidth, int areaHeight) {
		//is this a small circle
		if(radiusSq >= areaWidth * 2 || radiusSq >= areaHeight * 2) {
			//and we're kind of close to it?
			return distSq(areaX + areaWidth / 2, areaY + areaHeight / 2, centerX, centerY) <= radiusSq * 3;
		} else return false;
	}
}
