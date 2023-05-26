package com.example.service.impl;

import com.example.service.GHEngineRoutingService;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Coordinate;
import com.graphhopper.jsprit.core.util.FastVehicleRoutingTransportCostsMatrix;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import org.geolatte.geom.V;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.*;

@Service
public class GHEngineRoutingServiceImpl implements GHEngineRoutingService {
    @Override
    public GraphHopper createGraphHopperInstance() {
        GraphHopper hopper = new GraphHopperOSM();
        hopper.setDataReaderFile("src/main/resources/data/china-lastest.osm.pbf");
        hopper.setGraphHopperLocation("resources");
        hopper.setEncodingManager(new EncodingManager("car"));
        hopper.importOrLoad();
        return hopper;
    }

    
    @Override
    public List<double[]> getP2pPointList(GraphHopper hopper, double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest req = new GHRequest(fromLat,fromLon,toLat,toLon).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.CHINA);
        GHResponse rsp = hopper.route(req);
        PointList points = rsp.getBest().getPoints();
        List<double[]> res = new ArrayList();
        for (int i=0;i<points.size();i++){
            res.add(new double[]{points.getLon(i),points.getLat(i)});
        }
        return res;
    }

    @Override
    public List<Map<String, Object>> getP2pPathInstructions(GraphHopper hopper, double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest req = new GHRequest(fromLat,fromLon,toLat,toLon).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.CHINA);
        GHResponse rsp = hopper.route(req);
        return rsp.getBest().getInstructions().createJson();
    }


    @Override
    public PathWrapper getP2pAllInfo(GraphHopper hopper, double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest req = new GHRequest(fromLat,fromLon,toLat,toLon).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.CHINA);
        GHResponse rsp = hopper.route(req);
        return rsp.getBest();
    }

    @Override
    public PathWrapper getMutiP2pAllInfo(GraphHopper hopper, List<GHPoint> points) {
        GHRequest req = new GHRequest(points).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.CHINA);
        GHResponse rsp = hopper.route(req);
        return rsp.getBest();
    }


    private VehicleType buildType(String typeId) {
        final int WEIGHT_INDEX = 0;
        VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance(typeId).addCapacityDimension(WEIGHT_INDEX,1);
        return vehicleTypeBuilder.build();
    }

    private VehicleImpl buildVehicle(VehicleType vehicleType,GHPoint startPoint) {
        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vehicle");
        vehicleBuilder.setStartLocation(Location.Builder.newInstance().setIndex(0).setCoordinate(Coordinate.newInstance(startPoint.getLat(),startPoint.getLon())).build());
        vehicleBuilder.setType(vehicleType);
        vehicleBuilder.setReturnToDepot(false);
        return vehicleBuilder.build();
    }

    private FastVehicleRoutingTransportCostsMatrix buildCostMatrix(GraphHopper hopper,List<GHPoint> points) {
        FastVehicleRoutingTransportCostsMatrix.Builder costsMatrixBuilder =
                FastVehicleRoutingTransportCostsMatrix.Builder.newInstance(points.size(),false);
        for (int i=0;i<points.size();i++){
            GHPoint from = points.get(i);
            for (int j=0;j<points.size();j++){
                GHPoint to = points.get(j);
                PathWrapper p2pAllInfo = getP2pAllInfo(hopper, from.getLat(), from.getLon(), to.getLat(), to.getLon());
                costsMatrixBuilder.addTransportTimeAndDistance(i,j,p2pAllInfo.getDistance(),p2pAllInfo.getDistance());
            }
        }
        return costsMatrixBuilder.build();
    }

    private VehicleRoutingProblem buildProblem(VehicleImpl vehicle, FastVehicleRoutingTransportCostsMatrix costMatrix,List<GHPoint> points) {
        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        vrpBuilder.addVehicle(vehicle);
        for (int i=1;i<points.size();i++){
            com.graphhopper.jsprit.core.problem.job.Service service =
                    com.graphhopper.jsprit.core.problem.job.Service.Builder.newInstance(String.valueOf(i))
                            .addSizeDimension(0,1)
                            .setLocation(Location.Builder.newInstance().setIndex(i).setCoordinate(Coordinate.newInstance(points.get(i).getLat(),points.get(i).getLon())).build())
                            .build();
            vrpBuilder.addJob(service);
        }
        return vrpBuilder.setRoutingCost(costMatrix).build();
    }

    private VehicleRoutingProblemSolution getSolution(VehicleRoutingProblem problem) {
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        return Solutions.bestOf(solutions);
    }

    @Override
    public PathWrapper getTspAllInfo(GraphHopper hopper,List<GHPoint> points) {
        for (GHPoint point : points) {
            System.out.println("初始：x:"+point.getLat()+" y:"+point.getLon());
        }

        VehicleType vehicleType = buildType("vehicleType");
        VehicleImpl vehicle = buildVehicle(vehicleType,points.get(0));
        FastVehicleRoutingTransportCostsMatrix costMatrix = buildCostMatrix(hopper,points);
        VehicleRoutingProblem problem = buildProblem(vehicle,costMatrix,points);
        VehicleRoutingProblemSolution solution = getSolution(problem);

        SolutionPrinter.print(problem, solution, SolutionPrinter.Print.VERBOSE);

//        System.out.println(costMatrix);
//        double[][][] matrix = costMatrix.getMatrix();
//        for (int i=0;i<matrix.length;i++){
//            for (int j=0;j<matrix[i].length;j++){
//                for (int k=0;k<matrix[i][j].length;k++)
//                    System.out.println("i:"+i+" j:"+j+" k:"+k+" value:"+matrix[i][j][k]);
//            }
//        }

        /*
        初始：x:37.514423573416394 y:122.08441257476808
        初始：x:37.52579221894634 y:122.06909179687501
        初始：x:37.51946139054317 y:122.08733081817628

        最终顺序：[
        37.514423573416394,122.08441257476808,
        37.52579221894634,122.06909179687501,
        37.51946139054317,122.08733081817628]

         */


        Collection<VehicleRoute> routes = solution.getRoutes();
        List<GHPoint> afterTspPoints = new ArrayList<>();
        afterTspPoints.add(points.get(0));
        for (VehicleRoute route : routes) {
            List<TourActivity> activities = route.getActivities();
            TourActivity tourActivity = activities.get(0);
            Location location = tourActivity.getLocation();
            Coordinate coordinate = location.getCoordinate();
            afterTspPoints.add(new GHPoint(coordinate.getX(),coordinate.getY()));
        }
        System.out.println("最终顺序："+afterTspPoints);
        return getMutiP2pAllInfo(hopper,afterTspPoints);
    }

}


/*
----------------------------------------------------------+
| solution                                                 |
+---------------+------------------------------------------+
| indicator     | value                                    |
+---------------+------------------------------------------+
| costs         | 3096.8888186332033                       |
| noVehicles    | 2                                        |
| unassgndJobs  | 0                                        |
+----------------------------------------------------------+
+--------------------------------------------------------------------------------------------------------------------------------+
| detailed solution                                                                                                              |
+---------+----------------------+-----------------------+-----------------+-----------------+-----------------+-----------------+
| route   | vehicle              | activity              | job             | arrTime         | endTime         | costs           |
+---------+----------------------+-----------------------+-----------------+-----------------+-----------------+-----------------+
| 1       | vehicle              | start                 | -               | undef           | 0               | 0               |
| 1       | vehicle              | service               | 1               | 1321            | 1321            | 1321            |
| 1       | vehicle              | end                   | -               | 2487            | undef           | 2487            |
+---------+----------------------+-----------------------+-----------------+-----------------+-----------------+-----------------+
| 2       | vehicle              | start                 | -               | undef           | 0               | 0               |
| 2       | vehicle              | service               | 2               | 305             | 305             | 305             |
| 2       | vehicle              | end                   | -               | 610             | undef           | 610             |
+--------------------------------------------------------------------------------------------------------------------------------+
最终顺序：[37.51578517915083,122.08509922027588, 37.519801771325895,122.07801818847658, 37.51827004557588,122.08655834198]
 */


