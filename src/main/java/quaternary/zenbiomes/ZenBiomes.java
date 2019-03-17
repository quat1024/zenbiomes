package quaternary.zenbiomes;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.fml.common.Mod;
import quaternary.zenbiomes.func.Layer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@Mod(
	modid = ZenBiomes.MODID,
	name = ZenBiomes.NAME,
	version = ZenBiomes.VERSION
)
@ZenClass("mods.zenbiomes.ZenBiomes")
@ZenRegister
public class ZenBiomes {
	public static final String MODID = "zenbiomes";
	public static final String NAME = "Zen Biomes";
	public static final String VERSION = "GRADLE:VERSION";
	
	@ZenMethod
	public void uwu(Layer layer) {
		System.out.println(layer);
	}
}
