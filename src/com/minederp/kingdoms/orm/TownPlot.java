package com.minederp.kingdoms.orm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import com.minederp.kingdoms.CommunityPlugin;

public class TownPlot {

	public static TownPlot makeTownPlot(ResultSet st) {
		TownPlot f = new TownPlot();
		try {
			f.setTownPlotID(st.getInt("TownPlotID"));
			f.setOwnerID(st.getInt("OwnerID"));
			f.setTownID(st.getInt("TownID"));
			f.setPlotPolygon(st.getString("PlotPolygon"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

	private static List<TownPlot> loopThroughTownPlot(ResultSet st) {
		List<TownPlot> fm = new ArrayList<TownPlot>();
		try {
			while (st.next()) {
				fm.add(makeTownPlot(st));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fm;
	}

	private int _TownPlotID;

	public int getTownPlotID() {
		return _TownPlotID;
	}

	public void setTownPlotID(int aTownPlotID) {
		_TownPlotID = aTownPlotID;
	}

	public static List<TownPlot> getAllByTownPlotID(int aTownPlotID) {

		try {
			return loopThroughTownPlot(CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where TownPlotID=" + aTownPlotID + ""));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlot getFirstByTownPlotID(int aTownPlotID) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where TownPlotID=" + aTownPlotID + "");
			if (fc.next())
				return makeTownPlot(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public TownPlayer getOwner() {
		return TownPlayer.getFirstByTownPlayerID(_OwnerID);
	}

	private int _OwnerID;

	public int getOwnerID() {
		return _OwnerID;
	}

	public void setOwnerID(int aOwnerID) {
		_OwnerID = aOwnerID;
	}

	public static List<TownPlot> getAllByOwnerID(int aOwnerID) {

		try {
			return loopThroughTownPlot(CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where OwnerID=" + aOwnerID + ""));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlot getFirstByOwnerID(int aOwnerID) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where OwnerID=" + aOwnerID + "");
			if (fc.next())
				return makeTownPlot(fc);
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

	public static List<TownPlot> getAllByTownID(int aTownID) {

		try {
			return loopThroughTownPlot(CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where TownID=" + aTownID + ""));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlot getFirstByTownID(int aTownID) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where TownID=" + aTownID + "");
			if (fc.next())
				return makeTownPlot(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String _PlotPolygon;

	public String getPlotPolygon() {
		return _PlotPolygon;
	}

	public void setPlotPolygon(String aPlotPolygon) {
		if (aPlotPolygon == null || aPlotPolygon.equals("null"))
			return;
		_PlotPolygon = aPlotPolygon;
	}

	public static List<TownPlot> getAllByPlotPolygon(String aPlotPolygon) {

		try {
			return loopThroughTownPlot(CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where PlotPolygon='" + aPlotPolygon + "'"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TownPlot getFirstByPlotPolygon(String aPlotPolygon) {
		try {
			ResultSet fc = CommunityPlugin.wrapper.selectQuery("*", "TownPlot", "where PlotPolygon='" + aPlotPolygon + "'");
			if (fc.next())
				return makeTownPlot(fc);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<TownPlot> getAll() {
		try {
			return loopThroughTownPlot(CommunityPlugin.wrapper.selectQuery("*", "TownPlot", ""));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void insert() {
		try {
			CommunityPlugin.wrapper.insertQuery("TownPlot", "default," + _OwnerID + ", " + _TownID + ", '" + _PlotPolygon + "'");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			CommunityPlugin.wrapper.updateQuery("UPDATE TownPlot SET OwnerID= " + _OwnerID + " , TownID= " + _TownID + " , PlotPolygon= '"
					+ _PlotPolygon + "' where TownPlotID=" + _TownPlotID + " ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof TownPlot)
			if (((TownPlot) b)._TownPlotID == _TownPlotID)
				return true;
		return false;

	}

	public void delete() {
		try {
			CommunityPlugin.wrapper.deleteQuery("TownPlot", "TownPlotID = " + _TownPlotID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}