package com.minederp.kingdoms.games;

public class BlockReprintItem {
	String Key;
	final boolean drop;

	public BlockReprintItem(int x2, int y2, int z2, int typeId, byte data, String key) {
		X = x2;
		Y = y2;
		Z = z2;
		Data = data;
		Type = typeId;
		Key = key;
		this.drop = false;
	}

	public BlockReprintItem(int x2, int y2, int z2, int typeId, byte data, String key, boolean drop) {
		X = x2;
		Y = y2;
		Z = z2;
		Data = data;
		Type = typeId;
		Key = key;
		this.drop = drop;
	}

	int Type;
	byte Data;
	int X;
	int Y;
	int Z;
}