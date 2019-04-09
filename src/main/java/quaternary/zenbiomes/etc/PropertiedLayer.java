package quaternary.zenbiomes.etc;

import crafttweaker.annotations.ZenRegister;
import quaternary.zenbiomes.func.Layer;
import quaternary.zenbiomes.func.LayerFactory;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//big TODO: make this an immutable object
@ZenClass("mods.zenbiomes.PropertiedLayer")
@ZenRegister
public abstract class PropertiedLayer implements Layer {
	public PropertiedLayer(String name) {
		this.name = name;
	}
	
	private final String name;
	//The current properties as assembled by the builder. Mutable by ZenScript.
	private final Map<String, Object> props = new HashMap<>();
	private final Set<String> allProps = new HashSet<>();
	
	//add a property. The user must set this property, or it will error.
	public void add(String key) {
		allProps.add(key);
	}
	
	public <T> void add(String key, T def) {
		add(key);
		props.put(key, def);
	}
	
	public void set(String key, Object value) {
		props.put(key, value);
	}
	
	public <T> T get(String key) {
		return (T) props.get(key);
	}
	
	public abstract Layer applyProperties(PropertiedLayer props);
	
	@Override
	public LayerFactory apply(LayerFactory layerFactory) {
		for(String key : allProps) {
			if(!props.containsKey(key)) {
				Etc.ctLogAndThrow("Missing property %s in layer %s", key, name);
				assert false;
			}
		}
		
		return applyProperties(this).apply(layerFactory);
	}
}
