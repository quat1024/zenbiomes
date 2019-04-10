package quaternary.zenbiomes.zen;

import crafttweaker.annotations.ZenRegister;
import quaternary.zenbiomes.layer.Layer;
import quaternary.zenbiomes.genlayer.choice.GenLayerChoiceCircle;
import quaternary.zenbiomes.genlayer.GenLayerConstant;
import quaternary.zenbiomes.genlayer.GenLayerReplace;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.zenbiomes.ZBLayers")
@ZenRegister
public class ZBLayers {
	//Constant
	@ZenMethod
	public static Layer constant(int constant) {
		return (parent) -> (seed, world) -> new GenLayerConstant(constant);
	}
	
	//Replace
	//replace(3, 4)
	@ZenMethod
	public static Layer replace(int source, int destination) {
		return (parent) -> (seed, world) -> new GenLayerReplace(source, destination, parent.apply(seed + 1, world));
	}
	
	//replace(3).with(4)
	@ZenMethod
	public static DSL.Replace_0 replace(int source) {
		return (destination) -> replace(source, destination);
	}
	
	@ZenProperty
	public static final GenLayerChoiceCircle.ZenLayer circle = new GenLayerChoiceCircle.ZenLayer();
	
	//TODO other cool things:
	//transformations
	// rotate 90
	// flip
	// uneven zooms
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
		@ZenClass("mods.zenbiomes.internal.Replace_0")
		@ZenRegister
		public interface Replace_0 {
			@ZenMethod
			Layer with(int destination);
		}
	}
}
