package quaternary.zenbiomes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quaternary.zenbiomes.zen.ZenInitBiomeGensEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@Mod(
	modid = ZenBiomes.MODID,
	name = ZenBiomes.NAME,
	version = ZenBiomes.VERSION
)
@ZenClass("mods.zenbiomes.ZenBiomes")
@ZenRegister
public class ZenBiomes {
	public static final String MODID = "zenbiomes";
	public static final String NAME = "ZenBiomes";
	public static final String VERSION = "GRADLE:VERSION";
	
	public static final Logger LOG = LogManager.getLogger(NAME);
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		MinecraftForge.TERRAIN_GEN_BUS.register(TerrainHandler.class);
	}
	
	private static final List<IEventHandler<ZenInitBiomeGensEvent>> initBiomeGenHandlers = new ArrayList<>(4);
	
	@ZenMethod
	public static void add(IEventHandler<ZenInitBiomeGensEvent> handler) {
		initBiomeGenHandlers.add(handler);
	}
	
	public static class TerrainHandler {
		@SubscribeEvent(priority = EventPriority.LOW)
		public static void initBiomeGens(WorldTypeEvent.InitBiomeGens e) {
			ZenInitBiomeGensEvent z = new ZenInitBiomeGensEvent(e);
			initBiomeGenHandlers.forEach(h -> h.handle(z));
			
			if(initBiomeGenHandlers.size() != 0) {
				LOG.info("Replaced biome generation algorithm. Have a nice day!");
			}
		}
	}
}
