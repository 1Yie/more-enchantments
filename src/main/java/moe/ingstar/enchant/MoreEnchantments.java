package moe.ingstar.enchant;

import moe.ingstar.enchant.Command.ModInfoCommand;
import moe.ingstar.enchant.Encantment.Util.*;
import moe.ingstar.enchant.Registry.ItemRegister;
import moe.ingstar.enchant.Registry.BuffEffectRegistry;
import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreEnchantments implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("More Enchantments");
	public static final String MOD_ID = "more_enchantments";

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded correctly. Mod's namespace is: " + MOD_ID);

		LOGGER.info("\n\n" +
				"                  ,~^^```\"~-,,<```.`;smm#####m,\n" +
				"              ,^  ,s###MT7;#M ]Q#` ,a###########M\"`````'``\"~-,\n" +
				"             /   \"^^\\  *W\"3@\"  ||  ~'7#######\"\\                '\".\n" +
				"          :\"          a ,##\\         ]######               e#######M\n" +
				"         L         ,######~        ,#######              ############m\n" +
				"        f         ]######b         #######,           ,,##############b\n" +
				"       ;         .%#####J W####Mm, #####b    ,;,ap ,###################p\n" +
				"       ~ @#Q|^5mg##8#####  %#\"\"Wgs#######T `@#mw,s#@#@########WW@#######\n" +
				"      ,^  ^= .MT\\ j###C            @#@##\"    `we\"|   @#####b` ,m 3######\n" +
				"     ;    ,      #####      p   ,###,#\"    .      ,######## ###m  ######\n" +
				"     ,%\"\"Y\"   ,]#\",##\\    `|p  @##,###M\"1^3`   ;##########Q]##`  @######\n" +
				"    [     7W  ##,#### @m,   ^1 #######m#mw,@m  \\@########C^|`.;s#######`\n" +
				"     y@Mw,   \"#######C  ^\"     |5###### mm ^\"~ ,#########  ;##########`\n" +
				"    ^   ,aQ   ^@#####       N   ^j###7  `     ,######################\n" +
				"   D   #####mg,######      M##p   ##b     ,##################^,#####\n" +
				"   [   ##############       ##########m,###################, ;#####\n" +
				"    o  ^############       @##########M`^`~\"%############\"  @#####b\n" +
				"      \"m,j#########b      @#######M|          @#######M\"^^\n" +
				"          ,^^\"||^7^\"7\\.   \"#####\"              \\#M7|\n" +
				"                           \"||`\n" +
				"\n" +
				"         Karl Marx     Friedrich Engels     Vladimir Lenin\n");

		ClientTickEvents.START_CLIENT_TICK.register(client -> {

			MinecraftClient mc = MinecraftClient.getInstance();
			Window window = mc.getWindow();

			if (window != null) {
				long windowHandle = window.getHandle();
				ConfusionHandler.initialize(mc, windowHandle);
			}
		});

		ModEnchantments.registerEnchantments();
		ItemRegister.register();
		BuffEffectRegistry.register();

		ModInfoCommand.initialize();
		MidasTouchHandler.initialize();
		DiamondLuckHandler.initialize();
		LeechHandler.initialize();
		HealthBoostHandler.initialize();
		KissOfDeathHandler.initialize();
		BenevolenceHandler.initialize();
		ContainerOfHeartHandler.initialize();
		TetanusBladeEnchantHandler.initialize();
		AbsoluteImmunityHandler.initialize();
		DeathAsHomeHandler.initialize();
		AreaDestructionHandler.initialize();
		BackstabberHandler.initialize();
		DeathBacktrackHandler.initialize();
		BeheadHandler.initialize();

		LostCurseHandler.onInitialize();
		SeaCurseHandler.onInitialize();
		ComingDamageHandler.onInitialize();
	}
}