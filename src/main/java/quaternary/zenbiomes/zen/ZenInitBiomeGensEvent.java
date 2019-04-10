package quaternary.zenbiomes.zen;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import quaternary.zenbiomes.layer.Layer;
import quaternary.zenbiomes.zen.Layers;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.zenbiomes.InitBiomeGensEvent")
@ZenRegister
public class ZenInitBiomeGensEvent {
	public ZenInitBiomeGensEvent(WorldTypeEvent.InitBiomeGens event) {
		this.event = event;
	}
	
	private final WorldTypeEvent.InitBiomeGens event;
	
	/**
	 * @return The world's seed.
	 */
	@ZenMethod
	@ZenGetter("seed")
	public long getSeed() {
		return event.getSeed();
	}
	
	/**
	 * @return The name of this world's world type.
	 */
	@ZenMethod
	@ZenGetter("worldType")
	public String getWorldType() {
		return event.getWorldType().getName();
	}
	
	/**
	 * @return The "high detail" biome map.
	 * Each pixel in this map represents one column of blocks in the world.
	 */
	@ZenMethod
	@ZenGetter("highDetail")
	public Layer getHighDetail() {
		return Layer.ofVanilla(event.getNewBiomeGens()[0]);
	}
	
	/**
	 * @return The "low detail" biome map.
	 * Each pixel in this map represents roughly a 4x4 square of columns in the world.
	 * This map is "zoomed out" 4x compared to the "high detail" biome map.
	 * *If that 4x relation does not hold, world generation has severe issues.*
	 */
	@ZenMethod
	@ZenGetter("lowDetail")
	public Layer getLowDetail() {
		return Layer.ofVanilla(event.getNewBiomeGens()[1]);
	}
	
	/**
	 * Set the "high detail" biome map to something else.
	 * You should probably use "chainHighDetail" or "chain" instead.
	 */
	@ZenMethod
	@ZenSetter("highDetail")
	public void setHighDetail(Layer... layers) {
		set(0, layers);
	}
	
	/**
	 * Set the "low detail" biome map to something else.
	 * You should probably use "chainLowDetail" or "chain" instead.
	 */
	@ZenMethod
	@ZenSetter("lowDetail")
	public void setLowDetail(Layer... layers) {
		set(1, layers);
	}
	
	//Internal.
	private void set(int i, Layer... layers) {
		GenLayer l = Layers.chain(layers).flatten().apply(event.getSeed(), event.getWorldType());
		l.initWorldGenSeed(getSeed());
		event.getNewBiomeGens()[i] = l;
	}
	
	/**
	 * Chain the following layer or group of layers onto the end of the high detail biome map.
	 */
	@ZenMethod
	public void chainHighDetail(Layer... layer) {
		setHighDetail(getHighDetail().then(layer));
	}
	
	/**
	 * Chain the following layer or group of layers onto the end of the low detail biome map.
	 */
	@ZenMethod
	public void chainLowDetail(Layer... layer) {
		setLowDetail(getLowDetail().then(layer));
	}
	
	/**
	 * Chain the following layer or group of layers onto the end of *both* biome maps.
	 */
	@ZenMethod
	public void chain(Layer... layers) {
		chainHighDetail(layers);
		chainLowDetail(layers);
	}
}
