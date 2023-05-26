<template>
  <div style="display: flex">
    <vs-navbar center-collapsed>
      <template #right>
        <vs-button @click="logout">退出登陆</vs-button>
      </template>
    </vs-navbar>

    <div id="map"></div>


    <el-card class="box-card" id="menu" style="overflow: scroll;" >
    <el-collapse accordion >
      <h4>多边形</h4>
      <el-collapse-item title="多边形的绘制与插入" name="1" >
        <el-form :label-position="labelPosition" label-width="80px" :model="polygonFeatures">
          <el-form-item label="名称">
            <el-input v-model="polygonFeatures.name" style="width: 190px"></el-input>
          </el-form-item>
          <el-form-item label="机场设施">
            <el-select v-model="polygonFeatures.aeroway" placeholder="无分类">
              <el-option v-for="item in aeroway" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="便利设施">
            <el-select v-model="polygonFeatures.amenity" placeholder="无分类">
              <el-option v-for="item in amenity" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用地种类">
            <el-select v-model="polygonFeatures.landuse" placeholder="无分类">
              <el-option v-for="item in landuse" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="休闲体育">
            <el-select v-model="polygonFeatures.leisure" placeholder="无分类">
              <el-option v-for="item in leisure" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="自然特征">
            <el-select v-model="polygonFeatures.natural" placeholder="无分类">
              <el-option v-for="item in natural" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="购物场所">
            <el-select v-model="polygonFeatures.shop" placeholder="无分类">
              <el-option v-for="item in shop" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button @click="drawPolygon">
          绘制多边形
        </el-button>
        <el-button @click="insertPolygon">
          插入多边形
        </el-button>
      </el-collapse-item>
      <el-collapse-item title="多边形的更新" name="2">
        <el-form :label-position="labelPosition" label-width="80px" :model="polygonFeatures">
          <el-form-item label="名称">
            <el-input v-model="polygonFeatures.name" style="width: 190px"></el-input>
          </el-form-item>
          <el-form-item label="机场设施">
            <el-select v-model="polygonFeatures.aeroway" placeholder="无分类">
              <el-option v-for="item in aeroway" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="便利设施">
            <el-select v-model="polygonFeatures.amenity" placeholder="无分类">
              <el-option v-for="item in amenity" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用地种类">
            <el-select v-model="polygonFeatures.landuse" placeholder="无分类">
              <el-option v-for="item in landuse" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="休闲体育">
            <el-select v-model="polygonFeatures.leisure" placeholder="无分类">
              <el-option v-for="item in leisure" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="自然特征">
            <el-select v-model="polygonFeatures.natural" placeholder="无分类">
              <el-option v-for="item in natural" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="购物场所">
            <el-select v-model="polygonFeatures.shop" placeholder="无分类">
              <el-option v-for="item in shop" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button @click="selectOnePolygon">
          选择多边形
        </el-button>
        <el-button @click="updatePolygon">
          编辑多边形
        </el-button>
      </el-collapse-item>
      <el-collapse-item title="多边形的销毁" name="3">
        <el-button @click="selectOnePolygon">
          选择多边形
        </el-button>
        <el-button @click="deletePolygon">
          删除多边形
        </el-button>
      </el-collapse-item>
      <h4>线条</h4>
      <el-collapse-item title="线条的绘制与插入" name="4">
        <el-form :label-position="labelPosition" label-width="80px" :model="lineFeatures">
          <el-form-item label="名称">
            <el-input v-model="lineFeatures.name" style="width: 190px"></el-input>
          </el-form-item>
          <el-form-item label="铁路种类">
            <el-select v-model="lineFeatures.railway" placeholder="无分类">
              <el-option v-for="item in railway" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="道路种类">
            <el-select v-model="lineFeatures.highway" placeholder="无分类">
              <el-option v-for="item in highway" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button @click="drawLine">
          绘制线条
        </el-button>
        <el-button @click="insertLine">
          插入线条
        </el-button>
      </el-collapse-item>
      <el-collapse-item title="线条的更新" name="5">
        <el-form :label-position="labelPosition" label-width="80px" :model="lineFeatures">
          <el-form-item label="名称">
            <el-input v-model="lineFeatures.name" style="width: 190px"></el-input>
          </el-form-item>
          <el-form-item label="铁路种类">
            <el-select v-model="lineFeatures.railway" placeholder="无分类">
              <el-option v-for="item in railway" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="道路种类">
            <el-select v-model="lineFeatures.highway" placeholder="无分类">
              <el-option v-for="item in highway" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button @click="selectOneLine">
          选择线条
        </el-button>
        <el-button @click="updateLine">
          编辑线条
        </el-button>
      </el-collapse-item>
      <el-collapse-item title="线条的销毁" name="6">
        <el-button @click="selectOneLine">
          选择线条
        </el-button>
        <el-button @click="deleteLine">
          删除线条
        </el-button>
      </el-collapse-item>
      <h4>点</h4>
      <el-collapse-item title="点的绘制与插入" name="7">
        <el-form :label-position="labelPosition" label-width="80px" :model="pointFeatures">
          <el-form-item label="名称">
            <el-input v-model="pointFeatures.name" style="width: 190px"></el-input>
          </el-form-item>
          <el-form-item label="便利设施">
            <el-select v-model="pointFeatures.amenity" placeholder="无分类">
              <el-option v-for="item in amenity" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="休闲体育">
            <el-select v-model="pointFeatures.leisure" placeholder="无分类">
              <el-option v-for="item in leisure" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="购物场所">
            <el-select v-model="pointFeatures.shop" placeholder="无分类">
              <el-option v-for="item in shop" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button @click="drawPoint">
          绘制点
        </el-button>
        <el-button @click="insertPoint">
          插入点
        </el-button>
      </el-collapse-item>
      <el-collapse-item title="点的更新" name="8">
        <el-form :label-position="labelPosition" label-width="80px" :model="pointFeatures">
          <el-form-item label="名称">
            <el-input v-model="pointFeatures.name" style="width: 190px"></el-input>
          </el-form-item>
          <el-form-item label="便利设施">
            <el-select v-model="pointFeatures.amenity" placeholder="无分类">
              <el-option v-for="item in amenity" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="休闲体育">
            <el-select v-model="pointFeatures.leisure" placeholder="无分类">
              <el-option v-for="item in leisure" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="购物场所">
            <el-select v-model="pointFeatures.shop" placeholder="无分类">
              <el-option v-for="item in shop" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button @click="selectOnePoint">
          选择点
        </el-button>
        <el-button @click="updatePoint">
          编辑点
        </el-button>
      </el-collapse-item>
      <el-collapse-item title="点的销毁" name="9">
        <el-button @click="selectOnePoint">
          选择点
        </el-button>
        <el-button @click="deletePoint">
          删除点
        </el-button>
      </el-collapse-item>
    </el-collapse>
      <br>
      <el-button @click="clean">同步</el-button>
    </el-card>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "mapControl",
  data(){
    return {
      polygonFeatures:{
        way:null,
        name:null,
        aeroway:null,
        amenity:null,
        highway:null,
        landuse:null,
        leisure:null,
        natural:null,
        shop:null
      },
      lineFeatures:{
        way:null,
        name:null,
        highway:null,
        railway:null
      },
      pointFeatures:{
        way:null,
        name:null,
        amenity:null,
        leisure:null,
        shop:null
      },
      labelPosition: 'right',
      aeroway: [{
        value:null,
        label:'默认值'
      },{
        value: 'aerodrome',
        label: '飞机场'
      },{
        value: 'helipad',
        label: '直升机着陆平台'
      }],
      amenity: [{
        value:null,label:'默认值'
      }, {
        value:'bar',label:'酒吧'
      }, {
        value:'cafe',label:'咖啡厅'
      },{
        value:'fast_food',label:'快餐厅'
      },{
        value:'restaurant',label:'餐馆'
      },{
        value:'library',label:'图书馆'
      },{
        value:'bus_station',label:'公交车站'
      },{
        value:'bicycle_parking',label:'自行车停放处'
      },{
        value:'parking',label:'停车场'
      },{
        value:'atm',label:'自助取款机'
      },{
        value:'dentist',label:'牙医'
      },{
        value:'hospital',label:'医院'
      },{
        value:'courthouse',label:'法院'
      },{
        value:'police',label:'警察局'
      },{
        value:'fire_station',label:'消防队'
      },{
        value:'post_office',label:'邮局'
      },{
        value:'telephone',label:'电话亭'
      },{
        value:'recycling',label:'可回收垃圾'
      }],
      landuse: [{
        value:null,
        label:'默认值'
      },{
        value: 'commercial',
        label: '商业用地'
      },{
        value: 'construction',
        label: '建筑用地'
      },{
        value: 'industrial',
        label: '工业用地'
      }, {
        value: 'education',
        label: '教育用地'
      },{
        value: 'forest',
        label: '森林'
      },{
        value: 'grass',
        label: '草地'
      },{
        value: 'religious',
        label: '宗教用地'
      },{
        value: 'basin',
        label: '人工湖'
      },],
      leisure: [{
        value:null,label:'默认值'
      },{
        value: 'beach_resort',
        label: '受管制海滩'
      },{
        value: 'fishing',
        label: '钓鱼台'
      },{
        value: 'fitness_station',
        label: '户外健身场'
      },{
        value: 'playground',
        label: '儿童乐园'
      },{
        value: 'water_park',
        label: '水上公园'
      },],
      natural: [{
        value:null,label:'默认值'
      },{
        value: 'grassland',
        label: '草地'
      },{
        value: 'scrub',
        label: '灌木丛'
      },{
        value: 'tree',
        label: '树木'
      },{
        value: 'wood',
        label: '森林'
      },],
      shop: [{
        value:null,label:'默认值'
      },{
        value: 'wine',
        label: '酒行'
      }, {
        value: 'dairy',
        label: '乳制品店'
      }, {
        value: 'seafood',
        label: '海鲜店'
      }, {
        value: 'department_store',
        label: '大型购物中心'
      }, {
        value: 'supermarket',
        label: '超级市场'
      },{
        value: 'clothes',
        label: '服装店'
      },{
        value: 'jewelry',
        label: '珠宝店'
      },{
        value: 'hairdresser',
        label: '理发店'
      },{
        value: 'optician',
        label: '眼镜店'
      },{
        value: 'florist',
        label: '鲜花店'
      },{
        value: 'art',
        label: '艺术品商店'
      },{
        value: 'books',
        label: '书店'
      },{
        value: 'laundry',
        label: '洗衣店'
      },{
        value: 'pet',
        label: '宠物店'
      }],
      highway:[{
        value:null,label:'默认值'
      },{
        value: 'motorway',
        label: '高速公路'
      },{
        value: 'trunk',
        label: '主干线'
      },{
        value: 'primary',
        label: '次干线'
      },{
        value: 'secondary',
        label: '城乡公路'
      },{
        value: 'road',
        label: '普通道路'
      },{
        value: 'footway',
        label: '人行道'
      },{
        value: 'bus_guideway',
        label: '公交专用道'
      }],
      railway:[{
        value:null,label:'默认值'
      },{
        value: 'construction',
        label: '建造中的铁路'
      },{
        value: 'monorail',
        label: '单轨铁路'
      },{
        value: 'rail',
        label: '运输铁路'
      },]
    }
  },
  mounted() {
    this.initMap();
  },
  methods:{
    initMap() {
      let map = L.map("map", {
        minZoom: 3,
        maxZoom: 18,
        center:[23.15541905126334,113.4163820743561],
        zoom: 16,
        zoomControl: true,
        attributionControl: false,
        crs: L.CRS.EPSG3857
      });
      this.map = map;　　　　//data上需要挂载
      window.map = map;
      L.tileLayer('http://192.168.65.8/osm/{z}/{x}/{y}.png', {
      }).addTo(map);
    },
    drawPolygon(){
      map.off('click')
      map.pm.enableDraw('Polygon', {
        snappable: true,
        snapDistance: 20,
      });
      map.on('pm:create', e => {
        let latlngs = e.layer._latlngs[0]
        let geom = 'POLYGON(('
        for (let i=0;i<latlngs.length;i++){
          geom += latlngs[i].lng + ' '+ latlngs[i].lat + ','
        }
        geom += latlngs[0].lng + ' ' + latlngs[0].lat
        geom += '))'
        console.log(geom)
        this.polygonFeatures.way = geom
        console.log(this.polygonFeatures)
      });
    },
    drawLine(){
      map.off('click')
      map.pm.enableDraw('Line', {
        snappable: true,
        snapDistance: 20,
      });
      map.on('pm:create', e => {
        console.log(e)
        let latlngs = e.layer._latlngs
        let geom = 'LINESTRING('
        for (let i=0;i<latlngs.length-1;i++){
          geom += latlngs[i].lng + ' '+ latlngs[i].lat + ','
        }
        geom += latlngs[latlngs.length-1].lng + ' ' + latlngs[latlngs.length-1].lat
        geom += ')'
        console.log(geom)
        this.lineFeatures.way = geom
      });
    },
    drawPoint(){
      map.off('click')
      map.on('click', e => {
        console.log(e)
        let geom = 'POINT(' + e.latlng.lng + ' ' + e.latlng.lat + ')'
        console.log(geom)
        this.pointFeatures.way = geom
      });
    },
    selectOnePolygon(){
      map.on('click',async (e)=>{
        // this.polygonFeatures.geom = 'POINT(' + e.latlng.lng + ' ' + e.latlng.lat + ')'
        const {data} = await axios.get('http://localhost:82/polygonEdit/'+e.latlng.lng+'/'+e.latlng.lat)
        console.log(data)
        this.polygonFeatures = data
      })

    },
    selectOneLine(){
      map.on('click',async (e)=>{
        // this.polygonFeatures.geom = 'POINT(' + e.latlng.lng + ' ' + e.latlng.lat + ')'
        const {data} = await axios.get('http://localhost:82/lineEdit/'+e.latlng.lng+'/'+e.latlng.lat)
        console.log(data)
        this.lineFeatures = data
      })
    },
    selectOnePoint(){
      map.on('click',async (e)=>{
        // this.polygonFeatures.geom = 'POINT(' + e.latlng.lng + ' ' + e.latlng.lat + ')'
        const {data} = await axios.get('http://localhost:82/pointEdit/'+e.latlng.lng+'/'+e.latlng.lat)
        console.log(data)
        this.pointFeatures = data
      })
    },
    async insertPolygon(){
      let inputData = this.polygonFeatures
      const {data} = await axios.put('http://localhost:82/polygonEdit',inputData)
      console.log(data)
      if (data === true){
        alert('插入成功')
      }else{
        alert('插入失败')
      }
    },
    async updatePolygon(){
      let inputData = this.polygonFeatures
      console.log(inputData)
      const {data} = await axios.post('http://localhost:82/polygonEdit',inputData)
      console.log(data)
      if (data === true){
        alert('更新成功')
      }else{
        alert('更新失败')
      }
    },
    async deletePolygon(){
      let inputData = this.polygonFeatures
      const {data} = await axios.delete('http://localhost:82/polygonEdit',{data:inputData})
      console.log(data)
      if (data === true){
        alert('删除成功')
      }else{
        alert('删除失败')
      }
    },
    async insertLine(){
      let inputData = this.lineFeatures
      const {data} = await axios.put('http://localhost:82/lineEdit',inputData)
      console.log(data)
      if (data === true){
        alert('插入成功')
      }else{
        alert('插入失败')
      }
    },
    async updateLine(){
      let inputData = this.lineFeatures
      const {data} = await axios.post('http://localhost:82/lineEdit',inputData)
      console.log(data)
      if (data === true){
        alert('更新成功')
      }else{
        alert('更新失败')
      }
    },
    async deleteLine(){
      let inputData = this.lineFeatures
      const {data} = await axios.delete('http://localhost:82/lineEdit',{data:inputData})
      console.log(data)
      if (data === true){
        alert('删除成功')
      }else{
        alert('删除失败')
      }
    },
    async insertPoint(){
      let inputData = this.pointFeatures
      const {data} = await axios.put('http://localhost:82/pointEdit',inputData)
      console.log(data)
      if (data === true){
        alert('插入成功')
      }else{
        alert('插入失败')
      }
    },
    async updatePoint(){
      let inputData = this.pointFeatures
      const {data} = await axios.post('http://localhost:82/pointEdit',inputData)
      console.log(data)
      if (data === true){
        alert('更新成功')
      }else{
        alert('更新失败')
      }
    },
    async deletePoint(){
      let inputData = this.pointFeatures
      const {data} = await axios.delete('http://localhost:82/pointEdit',{data:inputData})
      console.log(data)
      if (data === true){
        alert('删除成功')
      }else{
        alert('删除失败')
      }
    },
    logout(){
      this.$router.push('/')
      localStorage.removeItem('token')
    },
    async clean(){
       const {data} = await axios.post('http://localhost:82/cleanCache')
      console.log(data)
      if (data === true){
        alert('同步成功')
      }else{
        alert('同步失败')
      }
    }
  }
}
</script>

<style scoped>
#map {
  position: relative;
  height: 770px;
  width: 75%;
  margin-top: 2px;
}
#menu{
  margin-top: 20px;
  margin-left: 5px;
  position: relative;
  width: 25%;
  height: 750px;
}
</style>