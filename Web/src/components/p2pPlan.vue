<template>
  <div id="app">
    <div id="map"></div>
    <div id="infoBox">
      <el-card class="box-card" >
        <vs-input border v-model="startPos" placeholder="起点" />
        <vs-input border v-model="targetPos" placeholder="终点" />
        <br>
        <el-button type="primary" icon="el-icon-search" @click="navigatePath">搜索</el-button>
      </el-card>
      <el-card id="navigationInfoForm" style="overflow: scroll">
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
  name: "p2pPlan",
  data() {
    return {
      map: "",
      count: 0,  //判断是否为获取起点坐标
      dialogTableVisible: true,
      startPos:'',
      targetPos:'',
      //路径详细信息
      pathInfo:[],
      layers:[]  //存储当前添加的路径图像信息
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
        maxZoom: 20,
        center: [23.144634, 113.432572],
        // center: [37.4225835,122.0392896],
        zoom: 14,
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
        if (this.count === 0){
          this.count++
          this.startPos = e.latlng
        }else {
          this.targetPos = e.latlng
        }
        console.log('单目标点规划中....')
        console.log('startPos:[',this.startPos.lng+','+this.startPos.lat,']')
        console.log('targetPos:['+this.targetPos.lng+','+this.targetPos.lat+']')
      });
    },
    async navigatePath() {
      let postData = {
        fromLat:this.startPos.lat,
        fromLon:this.startPos.lng,
        toLat:this.targetPos.lat,
        toLon:this.targetPos.lng
      }
      //重置所有数据
      this.count = 0
      this.startPos = ''
      this.targetPos = ''
      const {data} = await axios.post('http://localhost:82/GHp2p',postData)
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
      let navigationInfoForm = document.getElementById("navigationInfoForm")
      console.log(navigationInfoForm.style);
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
  height: 575px;
  /*display: none;*/
}
</style>

<!--<style scoped>-->
<!--#app{-->
<!--  display: flex;-->
<!--  margin-top: 50px;-->
<!--}-->
<!--#map {-->
<!--  position: relative;-->
<!--  height: 750px;-->
<!--  width: 75%;-->
<!--  margin-top: 2px;-->
<!--}-->
<!--#infoBox{-->
<!--  margin-top: 3px;-->
<!--  width: 25%;-->
<!--  margin-left: 1px;-->
<!--}-->
<!--#navigationInfoForm{-->
<!--  margin-top: 5px;-->
<!--  height: 605px;-->
<!--  /*display: none;*/-->
<!--}-->
<!--</style>-->