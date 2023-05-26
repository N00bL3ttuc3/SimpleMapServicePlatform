import Vue from 'vue'
import App from './App.vue'
import Vuesax from 'vuesax'
import 'vuesax/dist/vuesax.css' //Vuesax styles
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css'
import router from "@/router/index";


import 'leaflet.pm';
import 'leaflet.pm/dist/leaflet.pm.css';
import elTableInfiniteScroll from 'el-table-infinite-scroll';

Vue.use(elTableInfiniteScroll);
Vue.use(Vuesax)
Vue.use(ElementUI)


Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router
}).$mount('#app')

