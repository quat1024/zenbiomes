package quaternary.zenbiomes.genlayer.choice;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.world.gen.layer.GenLayer;
import quaternary.zenbiomes.layer.Layer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.function.Consumer;

public class GenLayerChoiceCircle extends AbstractGenLayerChoice {
	public GenLayerChoiceCircle(int centerX, int centerY, int radius, GenLayer inside, GenLayer outside) {
		super(inside, outside);
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.radiusSq = radius * radius;
	}
	
	private final int centerX;
	private final int centerY;
	private final int radiusSq;
	
	@Override
	protected boolean choose(int x, int y) {
		return distSq(centerX, centerY, x, y) <= radiusSq;
	}
	
	private int distSq(int x1, int y1, int x2, int y2) {
		return ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2));
	}
	
	@Override
	protected boolean probablyAllTheSame(int areaX, int areaY, int areaWidth, int areaHeight) {
		//is this a small circle
		if(radiusSq >= areaWidth * 2 || radiusSq >= areaHeight * 2) {
			//and we're kind of close to it?
			return distSq(areaX + areaWidth / 2, areaY + areaHeight / 2, centerX, centerY) <= radiusSq * 3;
		} else return false;
	}
	
	@ZenClass("mods.zenbiomes.layers.choice.CircleLayer")
	@ZenRegister
	public static class ZenLayer {
		public ZenLayer() {
			this(0, 0, 64);
		}
		
		public ZenLayer(int centerX, int centerY, int radius) {
			this.centerX = centerX;
			this.centerY = centerY;
			this.radius = radius;
		}
		
		private int centerX;
		private int centerY;
		private int radius;
		
		@ZenMethod
		public ZenLayer copy(Consumer<ZenLayer> act) {
			ZenLayer c = new ZenLayer(centerX, centerY, radius);
			act.accept(c);
			return c;
		}
		
		@ZenMethod
		public ZenLayer x(int x) {
			return copy(c -> c.centerX = x);
		}
		
		@ZenMethod
		public ZenLayer y(int y) {
			return copy(c -> c.centerY = y);
		}
		
		@ZenMethod
		public ZenLayer center(int x, int y) {
			return copy(c -> {
				c.centerX = x;
				c.centerY = y;
			});
		}
		
		@ZenMethod
		public ZenLayer radius(int radius) {
			return copy(c -> c.radius = radius);
		}
		
		@ZenMethod
		public Layer inside(Layer inside) {
			return (parent) -> (seed, world) -> new GenLayerChoiceCircle(
				centerX,
				centerY,
				radius,
				inside.flatten().apply(seed + 1, world),
				parent.apply(seed + 1, world)
			);
		}
		
		@ZenMethod
		public Layer outside(Layer outside) {
			return (parent) -> (seed, world) -> new GenLayerChoiceCircle(
				centerX,
				centerY,
				radius,
				parent.apply(seed + 1, world),
				outside.flatten().apply(seed + 1, world)
			);
		}
	}
}
