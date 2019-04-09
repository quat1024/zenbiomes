package quaternary.zenbiomes.genlayer.choice;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerChoiceStripe extends AbstractGenLayerChoice {
	public GenLayerChoiceStripe(boolean xAxis, int stripeWidth, int stripeSlide, GenLayer a, GenLayer b) {
		super(a, b);
		
		this.xAxis = xAxis;
		this.stripeWidth = stripeWidth;
		this.stripeSlide = stripeSlide;
	}
	
	private final boolean xAxis;
	private final int stripeWidth;
	private final int stripeSlide;
	
	@Override
	protected boolean choose(int x, int y) {
		int check = xAxis ? x : y;
		return ((check + stripeSlide) / stripeWidth) % 2 == 0;
	}
	
	@Override
	protected boolean probablyAllTheSame(int areaX, int areaY, int areaWidth, int areaHeight) {
		return false; //TODO should be easy.
	}
}
