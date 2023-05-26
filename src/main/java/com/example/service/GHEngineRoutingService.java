package com.example.service;

import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.FastVehicleRoutingTransportCostsMatrix;
import com.graphhopper.util.shapes.GHPoint;

import java.util.List;
import java.util.Map;

public interface GHEngineRoutingService {
    GraphHopper createGraphHopperInstance();

    @Deprecated
    List<double[]> getP2pPointList(GraphHopper hopper, double fromLat, double fromLon, double toLat, double toLon);

    @Deprecated
    List<Map<String, Object>> getP2pPathInstructions(GraphHopper hopper, double fromLat, double fromLon, double toLat, double toLon);


    PathWrapper getP2pAllInfo(GraphHopper hopper, double fromLat, double fromLon, double toLat, double toLon);
    PathWrapper getMutiP2pAllInfo(GraphHopper hopper, List<GHPoint> points);
    PathWrapper getTspAllInfo(GraphHopper hopper,List<GHPoint> points);

}
