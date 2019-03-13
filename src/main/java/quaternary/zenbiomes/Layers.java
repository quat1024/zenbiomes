package quaternary.zenbiomes;

import io.vavr.collection.Iterator;
import io.vavr.collection.Stream;
import quaternary.zenbiomes.func.Layer;

import java.util.Arrays;

public class Layers {
	//with array
	public static Layer chain(Layer... components) {
		return chain(Stream.ofAll(Arrays.asList(components)));
	}
	
	public static Layer compose(Layer... components) {
		return compose(Stream.ofAll(Arrays.asList(components)));
	}
	
	//with vavr Stream
	public static Layer chain(Stream<Layer> components) {
		return components.head().then(components.tail());
	}
	
	public static Layer compose(Stream<Layer> components) {
		return components.head().compose(components.tail());
	}
}
