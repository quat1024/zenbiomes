package quaternary.zenbiomes.layer;

import net.minecraft.world.gen.layer.GenLayer;

public abstract class AbstractLayer {
	public abstract int parentCount();
	public abstract GenLayer bake(long seed);
	
	public AbstractLayer[] parents = new AbstractLayer[parentCount()];
	
	public boolean usesCustomSeed;
	long customSeed;
	
	public AbstractLayer seed(long seed) {
		usesCustomSeed = true;
		customSeed = seed;
		
		return this;
	}
	
	public AbstractLayer setParent(int id, AbstractLayer parent) {
		checkParentID(id);
		parents[id] = parent;
		
		return this;
	}
	
	public AbstractLayer getParent(int id) {
		checkParentID(id);
		return parents[id];
	}
	
	private void checkParentID(int id) {
		if(id < 0) throw new IllegalArgumentException("negative parent ID?");
		if(id >= parents.length) throw new IllegalArgumentException("out of range parent index, found " + id + " only have " + parents.length);
	}
	
	protected long getSeed(long seed) {
		if(usesCustomSeed) return customSeed;
		else return seed;
	}
	
	protected void verifyBake() {
		for(int i = 0; i < parents.length; i++) {
			AbstractLayer parent = parents[i];
			if(parent == null) {
				throw new IllegalStateException("tried to bake " + getClass().getName() + " but parent " + i + " is null");
			}
		}
	}
}
