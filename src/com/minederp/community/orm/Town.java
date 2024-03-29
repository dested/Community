package com.minederp.community.orm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import com.minederp.community.CommunityPlugin;

public class Town {

	public static Town makeTown(ResultSet st) {
		Town f = new Town();
		try {
			f.setTownID(st.getInt("TownID"));
			f.setTownName(st.getString("TownName"));
			f.setTownSpawn(st.getString("TownSpawn"));
			f.setTownHeart(st.getString("TownHeart"));
			f.setGovernorID(st.getInt("GovernorID"));
			f.setTownPolygon(st.getString("TownPolygon"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

	private static List<Town> loopThroughTown(ResultSet st) {
		List<Town> fm = new ArrayList<Town>();
		try {
			while (st.next()) {
				fm.add(makeTown(st));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fm;
	}

	private int _TownID;

	public int getTownID() {
		return _TownID;
	}

	public void setTownID(int aTownID) {
		_TownID = aTownID;
	}

	public static List<Town> getAllByTownID(int aTownID) {

		try {
			return loopThroughTown(CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownID=" + aTownID + ""));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Town getFirstByTownID(int aTownID) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownID=" + aTownID + "");
			if (fc.next())
				return makeTown(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String _TownName;

	public String getTownName() {
		return _TownName;
	}

	public void setTownName(String aTownName) {
		if (aTownName == null || aTownName.equals("null"))
			return;
		_TownName = aTownName;
	}

	public static List<Town> getAllByTownName(String aTownName) {

		try {
			return loopThroughTown(CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownName='" + aTownName + "'"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Town getFirstByTownName(String aTownName) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownName='" + aTownName + "'");
			if (fc.next())
				return makeTown(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String _TownSpawn;

	public String getTownSpawn() {
		return _TownSpawn;
	}

	public void setTownSpawn(String aTownSpawn) {
		if (aTownSpawn == null || aTownSpawn.equals("null"))
			return;
		_TownSpawn = aTownSpawn;
	}

	public static List<Town> getAllByTownSpawn(String aTownSpawn) {

		try {
			return loopThroughTown(CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownSpawn='" + aTownSpawn + "'"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Town getFirstByTownSpawn(String aTownSpawn) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownSpawn='" + aTownSpawn + "'");
			if (fc.next())
				return makeTown(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String _TownHeart;

	public String getTownHeart() {
		return _TownHeart;
	}

	public void setTownHeart(String aTownHeart) {
		if (aTownHeart == null || aTownHeart.equals("null"))
			return;
		_TownHeart = aTownHeart;
	}

	public static List<Town> getAllByTownHeart(String aTownHeart) {

		try {
			return loopThroughTown(CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownHeart='" + aTownHeart + "'"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Town getFirstByTownHeart(String aTownHeart) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownHeart='" + aTownHeart + "'");
			if (fc.next())
				return makeTown(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public TownPlayer getGovernor() {
		return TownPlayer .getFirstByTownPlayerID(_GovernorID);
	}

	private int _GovernorID;

	public int getGovernorID() {
		return _GovernorID;
	}

	public void setGovernorID(int aGovernorID) {
		_GovernorID = aGovernorID;
	}

	public static List<Town> getAllByGovernorID(int aGovernorID) {

		try {
			return loopThroughTown(CommunityPlugin.wrapper.selectQuery("*", "Town", "where GovernorID=" + aGovernorID + ""));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Town getFirstByGovernorID(int aGovernorID) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "Town", "where GovernorID=" + aGovernorID + "");
			if (fc.next())
				return makeTown(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String _TownPolygon;

	public String getTownPolygon() {
		return _TownPolygon;
	}

	public void setTownPolygon(String aTownPolygon) {
		if (aTownPolygon == null || aTownPolygon.equals("null"))
			return;
		_TownPolygon = aTownPolygon;
	}

	public static List<Town> getAllByTownPolygon(String aTownPolygon) {

		try {
			return loopThroughTown(CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownPolygon='" + aTownPolygon + "'"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Town getFirstByTownPolygon(String aTownPolygon) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "Town", "where TownPolygon='" + aTownPolygon + "'");
			if (fc.next())
				return makeTown(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<Town> getAll() {
		try {
			return loopThroughTown(CommunityPlugin.wrapper.selectQuery("*", "Town", ""));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void insert() {
		try {
			CommunityPlugin.wrapper.insertQuery("Town", "default,'" + _TownName + "', '" + _TownSpawn + "', '" + _TownHeart + "', "+ _GovernorID + ", '" + _TownPolygon + "'");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			CommunityPlugin.wrapper.updateQuery("UPDATE Town SET TownName= '" + _TownName + "' , TownSpawn= '" + _TownSpawn + "' , TownHeart= '"
					+ _TownHeart + " , GovernorID= " + _GovernorID + " , TownPolygon= '" + _TownPolygon
					+ "' where TownID=" + _TownID + " ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof Town)
			if (((Town) b)._TownID == _TownID)
				return true;
		return false;

	}

	public void delete() {
		try {
			CommunityPlugin.wrapper.deleteQuery("Town", "TownID = " + _TownID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}