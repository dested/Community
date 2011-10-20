package com.minederp.community.games.communities.content;

import com.minederp.community.util.Polygon;

public interface PolygonChecker {
	public void save(Polygon polygon);
	public boolean collides(Polygon polygon);
}
