package quaternary.zenbiomes.zen;

import crafttweaker.annotations.ZenRegister;
import io.vavr.collection.Stream;
import quaternary.zenbiomes.layer.Layer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenClass("mods.zenbiomes.Layers")
@ZenRegister
public class Layers {
	//with array
	@ZenMethod
	public static Layer chain(Layer... components) {
		return chain(Stream.ofAll(Arrays.asList(components)));
	}
	
	//with vavr Stream
	@ZenMethod
	public static Layer chain(Stream<Layer> components) {
		return components.head().then(components.tail());
	}
	
	//K lol
	@ZenMethod
	public static Layer repeat(int count, Layer layer) {
		return layer.repeat(count);
	}
}
