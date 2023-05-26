<template>
  <div id="app">
    <div id="map"></div>
    <div id="infoBox">
      <el-card class="box-card" >
        <vs-input border v-model="points[0]" placeholder="起点" />

        <vs-tr
            :key="i"
            v-for="(point, i) in points"
            :data="point"
        >
          <vs-td>
            [{{ i+1 }}]
          </vs-td>
          <vs-td>
            [{{ point.lat }},{{ point.lng }}]
          </vs-td>
        </vs-tr>

        <!--        <vs-input border v-model="targetPos" placeholder="终点" />-->
        <br>
        <el-button type="primary" icon="el-icon-search" @click="navigatePath">搜索</el-button>
      </el-card>
      <el-card id="navigationInfoForm" style="overflow:scroll;">
        <el-table
            :data=pathInfo
            stripe
            style="width: 100%">
          <el-table-column
              prop="navigationInfo"
              label="导航信息"
              width="250px">
          </el-table-column>
          <el-table-column
              prop="distance"
              label="距离"
              width="100px">
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "tspPlan",
  data() {
    return {
      map: "",
      count: 0,         //判断是否为获取起点坐标
      dialogTableVisible: true,
      points:[],        //point[0]是起点，point[end]是终点
      pathInfo:[],      //路径详细信息
      layers:[],        //存储当前添加的路径图像信息
      approachPoints:[] //存储途径点坐标
    };
  },
  mounted() {
    this.initMap();
    this.prepareNavData();
  },
  methods:{
    initMap() {
      let map = L.map("map", {
        minZoom: 3,
        maxZoom: 30,
        center: [37.520227, 122.076237],
        zoom: 16,
        zoomControl: true,
        attributionControl: false,
        crs: L.CRS.EPSG3857
      });
      this.map = map;　　　　//data上需要挂载
      window.map = map;
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(map);
    },
    prepareNavData(){
      map.on('mouseup', (e) => {
        this.points.push(e.latlng)
      });
    },
    async navigatePath() {
      let pointsData = []
      for (let i=0;i<this.points.length;i++){
        pointsData.push({
          lat: this.points[i].lat,
          lon: this.points[i].lng,
          valid: true
        })
      }
      //重置所有数据
      this.count = 0
      this.points = []
      const {data} = await axios.post('http://localhost:82/GHTsp',{
        points: pointsData
      })
      // 显示到地图上
      let geojsonFeatures = [
        {
          "type":"LineString",
          "coordinates": data[1]
        }
      ]
      let info = data[2]
      console.log(info);
      this.pathInfo = []
      navigationInfoForm.style.display = ""
      for(let i=0;i<info.length;i++){
        this.pathInfo.push({navigationInfo: info[i].text,distance: info[i].distance})
      }
      console.log(this.pathInfo)
      let layer = L.geoJSON(geojsonFeatures)
      this.layers.push(layer)
      layer.addTo(map)
    }
  }
}
</script>

<style scoped>
#app{
  margin-top: 50px;
  display: flex;
}
#map {
  position: relative;
  height: 750px;
  width: 75%;
  margin-top: 2px;
}
#infoBox{
  margin-top: 3px;
  width: 25%;
  margin-left: 1px;
}
#navigationInfoForm{
  margin-top: 5px;
  height: 605px;
  /*display: none;*/
}
</style>

<!--<style scoped>-->
<!--#app{-->
<!--  display: flex;-->
<!--}-->
<!--#map {-->
<!--  position: relative;-->
<!--  height: 750px;-->
<!--  width: 75%;-->
<!--  margin-top: -15px;-->
<!--  margin-top: 2px;-->
<!--}-->
<!--#infoBox{-->
<!--  margin-top: 3px;-->
<!--  width: 25%;-->
<!--  margin-left: 1px;-->
<!--}-->
<!--#navigationInfoForm{-->
<!--  margin-top: 5px;-->
<!--  height: 100%;-->
<!--  /*display: none;*/-->
<!--}-->
<!--</style>-->