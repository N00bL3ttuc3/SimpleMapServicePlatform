<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script>
export default {
  name: "drawVector",
  data(){
    return {
      map:''
    }
  },
  mounted() {
    this.initMap();
    this.initMapControl();
  },
  methods:{
    initMap() {
      let map = L.map("map", {
        minZoom: 3,
        maxZoom: 20,
        center: [23.144634, 113.432572],
        zoom: 14,
        zoomControl: true,
        attributionControl: false,
        crs: L.CRS.EPSG3857
      });
      this.map = map;　　　　//data上需要挂载

      window.map = map;
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        // attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'

      }).addTo(map);
    },
    initMapControl(){
      this.map.pm.addControls({
        position: 'topleft',
        drawMarker: false,
      })
    },
    savePolygons(){
      // 获取绘制的边界，然后将多边形进行存储，并在初次进入本页面时加载
    }
  }
}
</script>

<style scoped>
#map {
  position: relative;
  height: 750px;
  width: 100%;
  margin-top: 50px;
}
</style>