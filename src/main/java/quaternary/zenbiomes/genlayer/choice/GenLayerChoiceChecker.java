package quaternary.zenbiomes.genlayer.choice;

import com.google.common.base.Preconditions;
import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerChoiceChecker extends AbstractGenLayerChoice {
	public GenLayerChoiceChecker(int checkerSize, int xSlide, int ySlide, GenLayer a, GenLayer b) {
		super(a, b);
		
		Preconditions.checkArgument(checkerSize > 0, "can't make a checkerboard with a nonpositive checker size!");
		
		this.checkerSize = checkerSize;
		this.xSlide = xSlide;
		this.ySlide = ySlide;
	}
	
	private final int checkerSize;
	private final int xSlide;
	private final int ySlide;
	
	@Override
	protected boolean choose(int x, int y) {
		return (((x + xSlide) / checkerSize) % 2 == 0) ^ (((y + ySlide) / checkerSize % 2) == 0);
	}
	
	@Override
	protected boolean probablyAllTheSame(int areaX, int areaY, int areaWidth, int areaHeight) {
		return false; //TODO this should be easy too.
	}
}
