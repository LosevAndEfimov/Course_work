package com.gluonapplication;

public class LevelData { 
	static String[] Level1 = new String[]{
			"TTTTTTTTDTTDTTTTTTTT",
			"T0000000D00D0000000T",
			"T00DLLLLL00RRRRRD00T",
			"T00D000000000000D00T",
			"T00D000000000000D00T",
			"T00RRRRRRDDLLLLLL00T",
			"T00000000DD00000000T",
			"T00000000DD00000000T",
			"T00000000DD00000000T",
			"T0000DLLLLRRRRD0000T",
			"T0000D00000000D0000T",
			"T0000RRRD00DLLL0000T",
			"T0000000D00D0000000T",
			"T0000000D00D0000000T",
			"T0000000RDDL0000000T",
			"TTTTTTTTTEETTTTTTTTT"
		};
	
	static String[] Level2 = new String[]{
        "TTTTTTTTTTTTTTTTTTTT",
        "T000000000000000000T",
        "T0000RRD000DLLL0000T",
        "T0000U0D000D00U0000T",
        "T0000U0D000D00U0000T",
        "RRRRRU0D000D00ULLLLL",
        "T000000D000D0000000T",
        "T000000D000D0000000T",
        "T00DLLLL000RRRRD000T",
        "T00D00000000000D000T",
        "T00D00000000000D000T",
        "T00D00000000000D000T",
        "T00RRRRD000DLLLL000T",
        "T000000D000D0000000T",
        "T000000D000D0000000T",
        "TTTTTTTETTTETTTTTTTT"
    };
	
	public static String[][] levels = new String[][]{
		Level1, Level2
	};
	
	public static int[] startCoordinates_1 = new int[]{
	    8, 0, 11, 0
	};
	
	public static int[] startCoordinates_2 = new int[]{
        0, 5, 19, 5
    };
	
	public static int[][] startCoordinates = new int[][]{
	  startCoordinates_1, startCoordinates_2
	};
}
