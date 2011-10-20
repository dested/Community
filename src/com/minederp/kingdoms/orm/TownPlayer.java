package com.minederp.kingdoms.orm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import com.minederp.kingdoms.CommunityPlugin;

public class TownPlayer {

	public static TownPlayer makeTownPlayer(ResultSet st) {
		TownPlayer f = new TownPlayer();
		try {
			f.setTownPlayerID(st.getInt("TownPlayerID"));
			f.setPlayerName(st.getString("PlayerName"));
			f.setPlayerNickName(st.getString("PlayerNickName")); 
			f.setTownID(st.getInt("TownID"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

	private static List<TownPlayer> loopThroughTownPlayer(ResultSet st) {
		List<TownPlayer> fm = new ArrayList<TownPlayer>();
		try {
			while (st.next()) {
				fm.add(makeTownPlayer(st));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fm;
	}

	private int _TownPlayerID;

	public int getTownPlayerID() {
		return _TownPlayerID;
	}

	public void setTownPlayerID(int aTownPlayerID) {
		_TownPlayerID = aTownPlayerID;
	}

	public static List<TownPlayer> getAllByTownPlayerID(int aTownPlayerID) {

		try {
			return loopThroughTownPlayer(CommunityPlugin.wrapper
					.selectQuery("*", "TownPlayer", "where TownPlayerID=" + aTownPlayerID + ""));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlayer getFirstByTownPlayerID(int aTownPlayerID) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlayer", "where TownPlayerID=" + aTownPlayerID + "");
			if (fc.next())
				return makeTownPlayer(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String _PlayerName;

	public String getPlayerName() {
		return _PlayerName;
	}

	public void setPlayerName(String aPlayerName) {
		if (aPlayerName == null || aPlayerName.equals("null"))
			return;
		_PlayerName = aPlayerName;
	}

	public static List<TownPlayer> getAllByPlayerName(String aPlayerName) {

		try {
			return loopThroughTownPlayer(CommunityPlugin.wrapper.selectQuery("*", "TownPlayer", "where PlayerName='" + aPlayerName + "'"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlayer getFirstByPlayerName(String aPlayerName) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlayer", "where PlayerName='" + aPlayerName + "'");
			if (fc.next())
				return makeTownPlayer(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String _PlayerNickName;

	public String getPlayerNickName() {
		return _PlayerNickName;
	}

	public void setPlayerNickName(String aPlayerNickName) {
		if (aPlayerNickName == null || aPlayerNickName.equals("null"))
			return;
		_PlayerNickName = aPlayerNickName;
	}

	public static List<TownPlayer> getAllByPlayerNickName(String aPlayerNickName) {

		try {
			return loopThroughTownPlayer(CommunityPlugin.wrapper
					.selectQuery("*", "TownPlayer", "where PlayerNickName='" + aPlayerNickName + "'"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlayer getFirstByPlayerNickName(String aPlayerNickName) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlayer", "where PlayerNickName='" + aPlayerNickName + "'");
			if (fc.next())
				return makeTownPlayer(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
 

	public Town getTown() {
		return Town.getFirstByTownID(_TownID);
	}

	private int _TownID;

	public int getTownID() {
		return _TownID;
	}

	public void setTownID(int aTownID) {
		_TownID = aTownID;
	}

	public static List<TownPlayer> getAllByTownID(int aTownID) {

		try {
			return loopThroughTownPlayer(CommunityPlugin.wrapper.selectQuery("*", "TownPlayer", "where TownID=" + aTownID + ""));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlayer getFirstByTownID(int aTownID) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlayer", "where TownID=" + aTownID + "");
			if (fc.next())
				return makeTownPlayer(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<TownPlayer> getAll() {
		try {
			return loopThroughTownPlayer(CommunityPlugin.wrapper.selectQuery("*", "TownPlayer", ""));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void insert() {
		try {
			CommunityPlugin.wrapper.insertQuery("TownPlayer", "default,'" + _PlayerName + "', '" + _PlayerNickName + "', " 
					+ _TownID + "");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			CommunityPlugin.wrapper.updateQuery("UPDATE TownPlayer SET PlayerName= '" + _PlayerName + "' , PlayerNickName= '" + _PlayerNickName
					+ "' , TownID= " + _TownID +   " where TownPlayerID=" + _TownPlayerID + " ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof TownPlayer)
			if (((TownPlayer) b)._TownPlayerID == _TownPlayerID)
				return true;
		return false;

	}

	public void delete() {
		try {
			CommunityPlugin.wrapper.deleteQuery("TownPlayer", "TownPlayerID = " + _TownPlayerID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}