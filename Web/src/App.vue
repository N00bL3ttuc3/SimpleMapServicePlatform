<template>
  <div id="app">
    <template>
        <div class="center examplex">
          <vs-navbar center-collapsed v-model="active" v-show="!(path==='/mapControl')">
            <vs-navbar-group id="overlayMenu">
              覆盖物
              <template #items>
                <vs-navbar-item :active="active == 'markPlace'" id="markPlace" to="markPlace">
                  地点标记
                </vs-navbar-item>
                <vs-navbar-item :active="active == 'drawVector'" id="drawVector" to="drawVector">
                  覆盖物绘制
                </vs-navbar-item>
              </template>
            </vs-navbar-group>
            <vs-navbar-item :active="active == 'search'" id="search" to="searchPlace">
              坐标拾取系统
            </vs-navbar-item>
            <vs-navbar-group id="navigateMenu">
              路径规划
              <template #items>
                <vs-navbar-item :active="active == 'p2pPlan'" id="p2pPlan" to="p2pPlan">
                  单点路径规划1.0
                </vs-navbar-item>
                <vs-navbar-item :active="active == 'mutiP2pPlan'" id="mutiP2pPlan" to="p2pPlan2.0">
                  单点路径规划2.0
                </vs-navbar-item>
                <vs-navbar-item :active="active == 'tspPlan'" id="tspPlan" to="tspPlan">
                  旅行商路径规划
                </vs-navbar-item>
              </template>
            </vs-navbar-group>

            <template #right>
              <vs-button @click="logout">退出登陆</vs-button>
            </template>
          </vs-navbar>
        </div>
    </template>


    <div id="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>


import axios from "axios";

export default {
  name: 'App',
  data:() => ({
    active: 'markPlace',
    loginForm: {
      username: '',
      password: ''
    },
    userToken: '',
    checkbox1: false,
    path: '/'
  }),
  watch:{
    $route(to,from){
      this.path = to.path
    }
  },
  methods:{
    logout(){
      this.$router.push('/')
      localStorage.removeItem('token')
    }
  }
}
</script>

<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#container{
  margin-top: 25px;
}
</style>




