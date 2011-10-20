package com.minederp.community.listeners;

import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.minederp.community.CommunityPlugin;

public class KingdomsVehicleListener extends VehicleListener {

	private final CommunityPlugin kingdomsPlugin;

	public KingdomsVehicleListener(CommunityPlugin kingdomsPlugin) {
		this.kingdomsPlugin = kingdomsPlugin;
 

	}

	public void onVehicleEnter(VehicleEnterEvent event) {
		System.out.print("enter");
		System.out.print("enter");
		System.out.print("enter");
		System.out.print("enter");
	}

	public void onVehicleExit(VehicleExitEvent event) {
		System.out.print("exit");
		System.out.print("exit");
		System.out.print("exit");
	}

	public void onVehicleMove(VehicleMoveEvent event) {
		System.out.print("move");
		System.out.print("move");
		System.out.print("move");
	}

}
