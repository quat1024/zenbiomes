package quaternary.zenbiomes.etc;

import crafttweaker.CraftTweakerAPI;

import java.util.Arrays;

public class Etc {
	//Haha i love jarbo
	public static int[] fillArray(int[] in, int num) {
		Arrays.fill(in, num);
		return in;
	}
	
	public static void ctLogAndThrow(String msg, Object... fmt) throws RuntimeException {
		String message = String.format(msg, fmt) + " @ " + CraftTweakerAPI.getScriptFileAndLine();
		CraftTweakerAPI.logError(message);
		throw new RuntimeException(message);
	}
}
