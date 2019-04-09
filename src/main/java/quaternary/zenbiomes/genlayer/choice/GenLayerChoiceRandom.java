package quaternary.zenbiomes.genlayer.choice;

import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerChoiceRandom extends AbstractGenLayerChoice {
	public GenLayerChoiceRandom(long seed, int chance, GenLayer pass, GenLayer fail) {
		super(seed, pass, fail);
		this.chance = chance;
	}
	
	private final int chance;
	
	@Override
	protected boolean choose(int x, int y) {
		initChunkSeed(x, y);
		return nextInt(100) + 1 <= chance;
	}
	
	@Override
	protected boolean probablyAllTheSame(int areaX, int areaY, int areaWidth, int areaHeight) {
		return false;
	}
}
