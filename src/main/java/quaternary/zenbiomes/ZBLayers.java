package quaternary.zenbiomes;

import quaternary.zenbiomes.func.Layer;
import quaternary.zenbiomes.func.LayerFactory;
import quaternary.zenbiomes.genlayer.GenLayerChoiceCircle;
import quaternary.zenbiomes.genlayer.GenLayerConstant;
import quaternary.zenbiomes.genlayer.GenLayerReplace;

public class ZBLayers {
	//Constant
	public static Layer constant(int constant) {
		return (parent) -> (seed, world) -> new GenLayerConstant(constant);
	}
	
	//Replace
	//replace(3, 4)
	public static Layer replace(int source, int destination) {
		return (parent) -> (seed, world) -> new GenLayerReplace(source, destination, parent.apply(seed + 1, world));
	}
	
	//replace(3).with(4)
	public static DSL.Replace_0 replace(int source) {
		return (destination) -> replace(source, destination);
	}
	
	//Circle
	//circle(0, 0, 100, a, b)
	public static Layer circle(int centerX, int centerY, int radius, Layer inside) {
		return (parent) -> (seed, world) -> new GenLayerChoiceCircle(centerX, centerY, radius, inside.flatten().apply(seed + 1, world), parent.apply(seed + 1, world));
	}
	
	//circle.center(0, 0).radius(100).inside(a)
	public static DSL.Circle_0 circle = (cx, cy) -> (radius) -> (inside) -> circle(cx, cy, radius, inside);
	
	//Mostly internal stuff relating to syntax sugar on ZenScript's end.
	public static class DSL {
		public interface Replace_0 {
			Layer with(int destination);
		}
		
		public interface Circle_0 {
			Circle_1 center(int x, int y);
		}
		
		public interface Circle_1 {
			Circle_2 radius(int radius);
		}
		
		public interface Circle_2 {
			Layer inside(Layer inside);
		}
	}
}
