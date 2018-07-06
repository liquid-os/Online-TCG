package org.author.game;

import java.awt.Toolkit;
import java.util.ArrayList;

public class Properties {
	static byte PACK_TYPE_GAME = 0, PACK_TYPE_MAPEDITOR = 1, PACK_TYPE_DEMO = 2;
	public static final int defaultCamOffset = -64;
	static int width = 800, height = 600;
	static float gravity = 9.8F;
	static int framerate = 80, port = 8553, selHeroID = 0;
	static String gameName = "Card RTS";
	static double ver = 0.1;
	static String gameTitle = "Pre-Alpha";
	public static float masterVol = 0F;
	public static String map = null;
	public static boolean legal = true, beta = false;
	public static String username = "username";
	static PanelBase startPanel = new PanelPackOpen(5);
	static byte packingType = PACK_TYPE_GAME;
	static String resPack = "";
	static String fileExt = ".PNG";

}
