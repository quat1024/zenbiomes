package quaternary.zenbiomes.func;

import crafttweaker.annotations.ZenRegister;
import io.vavr.Function1;
import io.vavr.collection.Stream;
import net.minecraft.world.gen.layer.GenLayer;
import quaternary.zenbiomes.Layers;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenClass("mods.zenbiomes.Layer")
@ZenRegister
public interface Layer extends Function1<LayerFactory, LayerFactory> {
	/// Seeding
	@ZenMethod
	default Layer seed(long newSeed) {
		//function that ignores the passed-in seed in favor of newSeed
		return (parent) -> (seed, world) -> this.apply(parent).apply(newSeed, world);
	}
	
	/// Composing layers with eachother
	//with one
	@ZenMethod
	default Layer then(Layer next) {
		return (parent) -> next.apply(this.apply(parent));
	}
	
	//with array/varargs
	@ZenMethod
	default Layer then(Layer... nextArr) {
		return then(Stream.ofAll(Arrays.asList(nextArr)));
	}
	
	//with vavr Stream
	@ZenMethod
	default Layer then(Stream<Layer> nextChain) {
		return nextChain.foldLeft(this, Layer::then);
	}
	
	/// Other utilities
	@ZenMethod
	default Layer repeat(int count) {
		return Layers.chain(Stream.continually(this).take(count));
	}
	
	@ZenMethod //TODO does this need to be exposed at all to zen
	default LayerFactory flatten() {
		return apply(null);
	}
	
	static Layer ofVanilla(GenLayer layer) {
		return (parent) -> LayerFactory.ofVanilla(layer);
	}
}
