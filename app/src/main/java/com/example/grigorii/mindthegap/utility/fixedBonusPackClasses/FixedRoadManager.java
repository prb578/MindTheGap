package com.example.grigorii.mindthegap.utility.fixedBonusPackClasses;

import android.content.Context;

import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

/**
 * Created by grigorii on 18/06/16.
 *
 * This class is the same as original FixedRoadManager, except it
 * contains two extra methods for building objects of FixedPolyline
 * class. Their logic is the same except FixedPolyline objects are
 * built.
 */
public abstract class FixedRoadManager extends RoadManager {
    /**
     * @param waypoints
     * @return the road found.
     * In case of error, road status is set to error, and the shape has just straight lines between waypoints.
     */
    public abstract Road getRoad(ArrayList<GeoPoint> waypoints);

    /**
     * @param waypoints
     * @return the list of roads found.
     * Road at index 0 is the shortest (in time).
     * The array may contain more entries, for alternate routes - assuming the routing service used supports alternate routes.
     * In case of error, return 1 road with its status set to error, and its shape with just straight lines between waypoints.
     */
    public abstract Road[] getRoads(ArrayList<GeoPoint> waypoints);

    public FixedRoadManager() {
        mOptions = "";
    }

    /**
     * Add an option that will be used in the route request.
     * Note that some options are set in the request in all cases.
     * @param requestOption see provider documentation.
     * Just one example: "routeType=bicycle" for MapQuest; "mode=bicycling" for Google.
     */
    public void addRequestOption(String requestOption){
        mOptions += "&" + requestOption;
    }

    /**
     * @return the GeoPoint as a string, properly formatted: lat,lon
     */
    protected String geoPointAsString(GeoPoint p){
        StringBuffer result = new StringBuffer();
        double d = p.getLatitude();
        result.append(Double.toString(d));
        d = p.getLongitude();
        result.append("," + Double.toString(d));
        return result.toString();
    }

    /**
     * Using the road high definition shape, builds and returns a Polyline.
     * @param road
     * @param color
     * @param width
     * @param context
     */
    public static Polyline buildRoadOverlay(Road road, int color, float width, Context context){
        Polyline roadOverlay = new Polyline(context);
        roadOverlay.setColor(color);
        roadOverlay.setWidth(width);
        if (road != null) {
            ArrayList<GeoPoint> polyline = road.mRouteHigh;
            roadOverlay.setPoints(polyline);
        }
        return roadOverlay;
    }

    /**
     * Builds an overlay for the road shape with a default (and nice!) style.
     * @return route shape overlay
     */
    public static Polyline buildRoadOverlay(Road road, Context context){
        return buildRoadOverlay(road, 0x800000FF, 5.0f, context);
    }

    public static FixedPolyline buildFixedRoadOverlay(Road road, int color, float width, Context context){
        FixedPolyline roadOverlay = new FixedPolyline(context);
        roadOverlay.setColor(color);
        roadOverlay.setWidth(width);
        if (road != null) {
            ArrayList<GeoPoint> polyline = road.mRouteHigh;
            roadOverlay.setPoints(polyline);
        }
        return roadOverlay;
    }


    public static FixedPolyline buildFixedRoadOverlay(Road road, Context context){
        return buildFixedRoadOverlay(road, 0x800000FF, 5.0f, context);
    }
}
