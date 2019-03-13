package quaternary.zenbiomes.func;

import io.vavr.Function1;
import io.vavr.collection.Stream;
import quaternary.zenbiomes.Layers;

import java.util.Arrays;

public interface Layer extends Function1<LayerFactory, LayerFactory> {
	/// Seeding
	
	default Layer seed(long newSeed) {
		//function that ignores the passed-in seed in favor of newSeed
		return (parent) -> (seed) -> this.apply(parent).apply(newSeed);
	}
	
	/// Composing layers with eachother
	
	//with one
	default Layer then(Layer next) {
		return (parent) -> next.apply(this.apply(parent));
	}
	
	//(shadow from super)
	default Layer compose(Layer prev) {
		return (parent) -> this.apply(prev.apply(parent));
	}
	
	//with array/varargs
	default Layer then(Layer... nextArr) {
		return then(Stream.ofAll(Arrays.asList(nextArr)));
	}
	
	default Layer compose(Layer... prevArr) {
		return compose(Stream.ofAll(Arrays.asList(prevArr)));
	}
	
	//with vavr Stream
	default Layer then(Stream<Layer> nextChain) {
		return nextChain.foldLeft(this, Layer::then);
	}
	
	default Layer compose(Stream<Layer> prevChain) {
		//TODO does this actually compose them
		return prevChain.foldRight(this, (x, xs) -> x.compose(xs));
	}
	
	/// Other utilities
	
	default Layer repeat(int count) {
		return Layers.chain(Stream.continually(this).take(count));
	}
	
	default LayerFactory flatten() {
		return apply(null);
	}
}
