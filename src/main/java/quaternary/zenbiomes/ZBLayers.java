package quaternary.zenbiomes;

import crafttweaker.annotations.ZenRegister;
import quaternary.zenbiomes.etc.PropertiedLayer;
import quaternary.zenbiomes.func.Layer;
import quaternary.zenbiomes.genlayer.GenLayerChoiceCircle;
import quaternary.zenbiomes.genlayer.GenLayerConstant;
import quaternary.zenbiomes.genlayer.GenLayerReplace;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.zenbiomes.ZBLayers")
@ZenRegister
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
	//todo this syntax no worky
	@ZenGetter("circle")
	public static DSL.Circle getCircle() {
		return new DSL.Circle();
	}
	
	//TODO other cool things:
	//math functions
	// add
	// negate
	// multiply
	// random
	//transformations
	// rotate 90
	// flip
	// uneven zooms
	// rough zooms (pixely)
	// zigzag (offset rows/cols)
	//shapes
	// spiral
	// stripe, but with more than 2
	// cone (circular stripe)
	// squares
	//???
	// scale2x? does that even make sense
	// a y combinator lmao
	// mix layers (e.g. BG shows where FG <= 0)
	// border (generalized "shore")
	
	//Mostly internal stuff relating to syntax sugar on ZenScript's end.
	public static class DSL {
		//TODO make this not bad (arbitrary arg order?)
		public interface Replace_0 {
			Layer with(int destination);
		}
		
		@ZenClass("mods.zenbiomes.dsl.circle")
		@ZenRegister
		public static class Circle extends PropertiedLayer {
			public Circle() {
				super("circle");
				add("x", 0);
				add("y", 0);
				add("radius");
				add("inside");
			}
			
			@ZenMethod
			public Circle x(int x) {
				set("x", x);
				return this;
			}
			
			@ZenMethod
			public Circle y(int y) {
				set("y", y);
				return this;
			}
			
			@ZenMethod
			public Circle center(int x, int y) {
				x(x);
				y(y);
				return this;
			}
			
			@ZenMethod
			public Circle radius(int radius) {
				set("radius", radius);
				return this;
			}
			
			@ZenMethod
			public Circle inside(Layer inside) {
				set("inside", inside);
				return this;
			}
			
			@Override
			public Layer applyProperties(PropertiedLayer props) {
				return circle(
					props.get("x"),
					props.get("y"),
					props.get("radius"),
					props.get("inside")
				);
			}
		}
	}
}
