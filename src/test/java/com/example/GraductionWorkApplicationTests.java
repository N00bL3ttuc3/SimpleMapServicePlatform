package com.example;

import com.example.controller.OSMEditController;
import com.example.dao.CityRoutingDao;
import com.example.dao.OsmEditDao;
import com.example.dao.PosInfoDao;
import com.example.dao.UserDao;
import com.example.model.OSMLineFearures;
import com.example.model.OSMPolygonFeatures;
import com.example.model.User;
import com.example.service.GHEngineRoutingService;
import com.example.service.OSMEditService;
import com.example.service.PosInfoService;
import com.example.service.UserService;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.reader.shp.GraphHopperSHP;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import org.checkerframework.checker.units.qual.A;
import org.geolatte.geom.ByteBuffer;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.codec.Wkb;
import org.geolatte.geom.codec.Wkt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SpringBootTest
class GraductionWorkApplicationTests {

    @Test
    void contextLoads() {
        String s="0105000020110F000001000000010200000002000000A60EC8A41A2E6841C798FE8D8FAB4341FC75BD01182E6841C34A467A85AB4341";
        Geometry geo = Wkb.fromWkb(ByteBuffer.from(s));

        // geometry对象和WKT输出一致

//        Geometry geometry1 = Wkt.fromWkt(wkt);
        System.out.println("-----wkt------"+ Wkt.toWkt(geo));
//        System.out.println("-----wkb------"+Wkb.toWkb(geo));
    }

    @Autowired
    private CityRoutingDao cityRoutingDao;

    @Autowired
    private PosInfoDao posInfoDao;

    @Autowired
    private PosInfoService posInfoService;

    @Test
    public void t(){
        System.out.println(posInfoService.getPosInfo(123.32,43.23));
    }

    @Test
    public void ttt(){

        GraphHopper hopper = new GraphHopperOSM().forServer();
//        hopper.setDataReaderFile("./src/main/resources/static/output/streets.shp");
//         where to store graphhopper files?
//        hopper.setGraphHopperLocation("/outShp/resources");
        hopper.setEncodingManager(new EncodingManager("car"));

        // now this can take minutes if it imports or a few seconds for loading
        // of course this is dependent on the area you import
        hopper.importOrLoad();
        // simple configuration of the request object, see the GraphHopperServlet classs for more possibilities.
        GHRequest req = new GHRequest(37.469312, 122.074661,35.752792,113.630301).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.CHINA);
//        System.out.println("req:----------");
//        System.out.println(req.toString());
//        System.out.println("---------------");
        GHResponse rsp = hopper.route(req);
//        System.out.println("rsp:-----------");
        System.out.println(rsp.toString());
        /*
        rsp.getBest().getInstructions()获取路径信息
        rsp.getBest().getPoints()获取经过的点的坐标
         */
//        System.out.println(rsp.getBest().getPoints());
//        System.out.println("--------------");

        if(rsp.hasErrors()) {
            // handle them!
            // rsp.getErrors()
            return;
        }

        // use the best path, see the GHResponse class for more possibilities.
        PathWrapper path = rsp.getBest();

        // points, distance in meters and time in millis of the full path
        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();
//        System.out.println(pointList);

        InstructionList il = path.getInstructions();
        // iterate over every turn instruction
        for(Instruction instruction : il) {
            instruction.getDistance();
            //System.out.println(instruction.getDistance());
        }

        // or get the json
        List<Map<String, Object>> iList = il.createJson();

        // or get the result as gpx entries:
        List<GPXEntry> list = il.createGPXList();

//         first check for errors

//        for (GPXEntry gpxEntry : list) {
//            System.out.println(gpxEntry.toString());
//        }
//        System.out.println(iList);
//        for (Map<String, Object> stringObjectMap : iList) {
//            System.out.println(stringObjectMap);
//        }

    }

    @Autowired
    GHEngineRoutingService service;

    @Test
    void testTSP(){
        GraphHopper hopper = service.createGraphHopperInstance();
        ArrayList a = new ArrayList();
        a.add(new GHPoint(22.627631584950645,113.84617684483658));
        a.add(new GHPoint(22.562652432569877, 113.93303766012627));
        a.add(new GHPoint(22.53411531912490, 114.01474862470711));
        PathWrapper tspAllInfo = service.getTspAllInfo(hopper, a);
//        System.out.println(tspAllInfo);
        PointList points = tspAllInfo.getPoints();
        System.out.println(points);
    }

    @Test
    void ppp(){
        GraphHopper hopper = service.createGraphHopperInstance();
        PathWrapper p2pAllInfo1 = service.getP2pAllInfo(hopper, 37.514423573416394, 122.08441257476808, 37.52579221894634, 122.06909179687501);
        PathWrapper p2pAllInfo2 = service.getP2pAllInfo(hopper, 37.514423573416394, 122.08441257476808, 37.51946139054317,122.08733081817628);
        PathWrapper p2pAllInfo3 = service.getP2pAllInfo(hopper, 37.52579221894634,  122.06909179687501, 37.51946139054317,122.08733081817628);
        System.out.println(p2pAllInfo1.getDistance());
        System.out.println(p2pAllInfo2.getDistance());
        System.out.println(p2pAllInfo3.getDistance());
    }


    @Autowired
    private OsmEditDao osmEditDao;

    @Test
    void testosm(){
//        osmEditDao.insertPolygons("新的插入测试",null,null,null,"grass",null,null,null,"POLYGON((113.41591000556947 23.155961599754125," +
//                "113.41636061668397 23.156523874962375," +
//                "113.41709017753602 23.156395636964717," +
//                "113.41737985610963 23.15567552900555," +
//                "113.4163820743561 23.15541905126334," +
//                "113.41591000556947 23.155961599754125" +
//                "))");
//        OSMPolygonFeatures geomFromLatLng = osmEditDao.getGeomFromLatLng("POINT(113.41897925273922 23.15231661814496)");
//        System.out.println(geomFromLatLng.toString());
        //        String geom = osmEditDao.getGeom("POINT(113.41796890572851 23.15893677614931)");
//        System.out.println(geom);
//        osmEditDao.updatePolygons("test123",osmId);
//        osmEditDao.deletePolygons(geom);

//        osmEditDao.insertLine("line test","trunk_link",null,"LINESTRING(113.41079105513134 23.156755689492734,113.41134881654668 23.158353719170535,113.41227126811819 23.157663214858676,113.41167060197861 23.156578129462865)");
        OSMLineFearures lineFromLatLng = osmEditDao.getLineFromLatLng("POINT(113.43334318107489 23.137340235233864)");
        System.out.println(lineFromLatLng.toString());
    }


    @Autowired
    private GHEngineRoutingService ghEngineRoutingService;

    @Test
    void test6(){
//        User bzk = userDao.findUserByName("bzk");
//        System.out.println(bzk.toString());
//        ghEngineRoutingService.createGraphHopperInstance();
    }



    @Test
    void test7(){
        GraphHopper hopper = new GraphHopperOSM().forServer();
        hopper.setDataReaderFile("/Users/bianzhenkun/spring_study/graductionWork/target/classes/data/china-latest.osm.pbf");
//        hopper1.setDataReaderFile()
        // where to store graphhopper files?
        hopper.setGraphHopperLocation("/Users/bianzhenkun/spring_study/graductionWork/target/classes/data/resources");
        hopper.setEncodingManager(new EncodingManager("car"));

        // now this can take minutes if it imports or a few seconds for loading
        // of course this is dependent on the area you import
        hopper.importOrLoad();
    }
}
