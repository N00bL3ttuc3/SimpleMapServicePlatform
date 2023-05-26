<template>
  <div id="app">

    <div id="map"></div>
    <div id="infoBox">
      <el-card class="box-card" >
        <vs-input border v-model="place" placeholder="请输入地点" />
        <br>
        <el-button type="primary" icon="el-icon-search" @click="onSubmit">反查</el-button>
      </el-card>
      <el-card>
        当前坐标：[{{lat}},{{lng}}]
        <br>
        当前层级：{{map._zoom}}级
      </el-card>
      <el-card id="placeInfo">
        {{result}}
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "searchPlace",
  mounted() {
    this.initMap();
    this.initLatLng();
  },
  data(){
    return {
      map:'',
      place:'',
      result:'',
      lat: 37.4225835,
      lng: 122.0392896,
      zoom: 12
    }
  },
  methods:{
    initMap() {
      let map = L.map("map", {
        minZoom: 3,
        maxZoom: 20,
        // center: [37.50532850349443,122.11231039163332],
        // center:[22.562044880137023,113.98087712969483],
        center:[23.17700159071724,113.40021007792119],
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
      // L.tileLayer('http://192.168.65.8/osm/{z}/{x}/{y}.png', {
      //   // attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      // }).addTo(map);
    },
    async onSubmit() {
        const {data} = await axios.get('https://nominatim.openstreetmap.org/',{
          params:{
            details:1,
            q:this.place,
            format:'json',
            limit:1
          }
        })
      let lat = data[0].lat
      let lon = data[0].lon
      console.log(lat,lon)
      this.map.setView([lat,lon],16)
      console.log(data)
      let new_data = '边界：'+data[0].boundingbox+
      '省市区全称：'+data[0].display_name+
      '类别：'+data[0].type
      this.result = new_data
    },
    initLatLng(){
      map.on('mouseup',(e)=>{
        this.lat = e.latlng.lat
        this.lng = e.latlng.lng
        console.log(e);
      })
    }
  }
}
</script>

<style scoped>
#app{
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
.box-card{
  /*height: 100%;*/
}
.el-radio{
  margin-top: 10px;
  /*height: 50px;*/
}
#placeInfo{
  margin-top: 5px;
}
</style>