<template>
  <div id="app">
    <div id="map"></div>
    <div id="infoBox">
      <el-card class="box-card" >
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="当前纬度">
            <el-input v-model="form.lat" disabled></el-input>
          </el-form-item>
          <el-form-item label="当前经度">
            <el-input v-model="form.lng" disabled></el-input>
          </el-form-item>
          <el-form-item label="地点名称">
            <el-input v-model="form.name" placeholder="请输入地点名称"></el-input>
          </el-form-item>
          <el-form-item label="地点类别">
            <el-select v-model="form.type" placeholder="请选择地点类别" size="100px" style="width: 100%;">
              <el-option label="餐厅" value="restaurant"></el-option>
              <el-option label="医院" value="hospital"></el-option>
              <el-option label="学校" value="school"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="地点标签" style="text-align: left">
            <el-radio-group v-model="form.tag" >
              <el-radio label="吃喝玩乐"></el-radio>
              <el-radio label="周末团聚"></el-radio>
              <el-radio label="新奇体验"></el-radio>
              <el-radio label="办公上班"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="其他描述">
            <el-input type="textarea" v-model="form.description"></el-input>
          </el-form-item>
        </el-form>
        <br>
        <el-button type="primary" icon="el-icon-search" @click="onSubmit">提交</el-button>
      </el-card>
    </div>
  </div>
</template>

<script>
import posLogo from '../assets/location.png'
import restaurant from '../assets/canting.png'
import school from '../assets/xuexiao.png'
import hospital from '../assets/yiyuan.png'
import axios from "axios";

export default {
  name: "markPlace",
  mounted() {
    this.initMap();
    this.initMarks();
    this.addMarker();
  },
  data(){
    return {
      markFlag: false,
      markers: [],
      icon: null,
      form: {
        name:'',
        type:'',
        tag:'',
        description:'',
        lat: 0.0,
        lng: 0.0
      }
    }
  },
  methods:{
    initMap() {
      let map = L.map("map", {
        minZoom: 3,
        maxZoom: 18,
        center: [22.553791338737252,114.07007428515739],
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
      this.icon = L.icon({
        iconUrl: restaurant,
        iconSize: [38,38]
      })
    },
    async initMarks(){
      const {data} = await axios.get('http://localhost:82/posInfo')
      for (let i=0;i<data.length;i++){
        console.log(data[i]);
        let lat = data[i].lat
        let lng = data[i].lng
        if (data[i].type === 'hospital')
          this.icon = L.icon({iconUrl: hospital, iconSize: [38,38]})
        else if (data[i].type === 'restaurant')
          this.icon = L.icon({iconUrl: restaurant, iconSize: [38,38]})
        else if (data[i].type === 'school')
          this.icon = L.icon({iconUrl: school, iconSize: [38,38]})
        let tmpMarker = L.marker([lat,lng],{icon:this.icon})
        tmpMarker.addTo(map)

        this.markers.push(tmpMarker)
        tmpMarker.on('click',async (e)=>{
          await this.clickEvent(e)
        })
      }
    },
    addMarker(){
      map.on('click',(e) =>{
        console.log(e);
        let lng = e.latlng.lng
        let lat = e.latlng.lat
        this.form.lat = lat
        this.form.lng = lng
        // if (this.markFlag === true) {
          let tmpMarker = L.marker([lat,lng],{icon:L.icon({iconUrl: posLogo, iconSize: [38,38]})})
          tmpMarker.on('click',async (e) => {
            await this.clickEvent(e)
          })

        this.markers.push(tmpMarker)
          tmpMarker.addTo(map)
        // }
        // this.markFlag = false
      })
    },
    async onSubmit() {
      let data = {
        name: this.form.name,
        type: this.form.type,
        tag: this.form.tag,
        description: this.form.description,
        lat: this.form.lat,
        lng: this.form.lng
      }
      console.log(data);
      console.log('this is on sumbit')
      console.log(this.form.lat)
      console.log(this.form.lng)
      const {data: posInfo} = await axios.get('http://localhost:82/posInfo/'+this.form.lat+'&'+this.form.lng)
      console.log(posInfo)
      if (posInfo.id>0) {
        posInfo.name = this.form.name
        posInfo.type = this.form.type
        posInfo.description = this.form.description
        const {data:updateRes} = await axios.post('http://localhost:82/posInfo', posInfo)
        if (updateRes===1) console.log('更新成功')
      }else{
        const {data:insertRes} = await axios.put('http://localhost:82/posInfo', data)
        if (insertRes===1) console.log('插入成功')
      }

      //更改对应类别的图标
      //存在bug，如果只是push进去tmpMarker，那么在进行图标转换时候，会找不到对应的marker
      //所以需要push的时候添加id索引，尝试在tmpMarker中添加id属性，这样可以直接在修改信息的时候根据id找到对应的marker
      //解决方案：更改后，删除全部maker，重新加载数据库中的数据

      //原方案：
      // if (this.form.type === 'hospital')
      //   this.icon = L.icon({iconUrl: hospital, iconSize: [38,38]})
      // else if (this.form.type === 'restaurant')
      //   this.icon = L.icon({iconUrl: restaurant, iconSize: [38,38]})
      // else if (this.form.type === 'school')
      //   this.icon = L.icon({iconUrl: school, iconSize: [38,38]})
      // this.markers[this.markers.length-1].setIcon(this.icon)

      //现方案：

      for(let i=0;i<this.markers.length;i++){
        map.removeLayer(this.markers[i])
      }
      this.markers = []
      this.initMarks()


      this.form.lat = ''
      this.form.lng = ''
      this.form.name = ''
      this.form.type = ''
      this.form.tag = ''
      this.form.description = ''
    },
    async clickEvent(e){
      let lng = e.latlng.lng
      let lat = e.latlng.lat
      this.form.lat = lat
      this.form.lng = lng
      console.log(e.latlng)
      const {data:posInfo} = await axios.get('http://localhost:82/posInfo/'+lat+'&'+lng)
      this.form.name = posInfo.name
      this.form.type = posInfo.type
      this.form.tag = posInfo.tag
      this.form.description = posInfo.description
    }
  }
}
</script>

<style scoped>
#app{
  display: flex;
  margin-top: 50px;
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
  height: 100%;
}
.el-radio{
  margin-top: 10px;
  /*height: 50px;*/
}
</style>