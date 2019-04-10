package quaternary.zenbiomes;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import quaternary.zenbiomes.func.Layer;
import quaternary.zenbiomes.func.LayerFactory;
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
	
	@ZenMethod
	@ZenGetter("seed")
	public long getSeed() {
		return event.getSeed();
	}
	
	@ZenMethod
	@ZenGetter("worldType")
	public String getWorldType() {
		return event.getWorldType().getName();
	}
	
	@ZenMethod
	@ZenGetter("highDetail")
	public Layer getHighDetail() {
		return Layer.ofVanilla(event.getNewBiomeGens()[0]);
	}
	
	@ZenMethod
	@ZenGetter("lowDetail")
	public Layer getLowDetail() {
		return Layer.ofVanilla(event.getNewBiomeGens()[1]);
	}
	
	@ZenMethod
	@ZenSetter("highDetail")
	public void setHighDetail(Layer layer) {
		GenLayer l = layer.flatten().apply(event.getSeed(), event.getWorldType());
		l.initWorldGenSeed(getSeed());
		event.getNewBiomeGens()[0] = l;
	}
	
	@ZenMethod
	@ZenSetter("lowDetail")
	public void setLowDetail(Layer layer) {
		GenLayer l = layer.flatten().apply(event.getSeed(), event.getWorldType());
		l.initWorldGenSeed(getSeed());
		event.getNewBiomeGens()[1] = l;
	}
	
	@ZenMethod
	public void chainHighDetail(Layer layer) {
		setHighDetail(getHighDetail().then(layer));
	}
	
	@ZenMethod
	public void chainLowDetail(Layer layer) {
		setLowDetail(getLowDetail().then(layer));
	}
	
	@ZenMethod
	public void chainBothDetails(Layer layer) {
		chainHighDetail(layer);
		chainLowDetail(layer);
	}
	
	@ZenMethod
	public void defaultLowDetail() {
		setLowDetail(getHighDetail().then(VanillaLayers.voronoiZoom));
	}
}
