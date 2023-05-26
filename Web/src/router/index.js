import Vue from 'vue';
import VueRouter from "vue-router";
import p2pPlan from "@/components/p2pPlan";
import markPlace from "@/components/markPlace";
import p2pPlanWithViaPoints from "@/components/p2pPlanWithViaPoints";
import searchPlace from "@/components/searchPlace";
import tspPlan from "@/components/tspPlan";
import drawVector from "@/components/drawVector";
import mapControl from "@/components/mapControl";
import login from "@/components/login";
import register from "@/components/register";


Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'',component:login},
        {path:'/markPlace',component:markPlace},
        {path:'/p2pPlan',component:p2pPlan},
        {path:'/p2pPlan2.0',component:p2pPlanWithViaPoints},
        {path:'/searchPlace',component:searchPlace},
        {path:'/tspPlan',component:tspPlan},
        {path:'/drawVector',component:drawVector},
        {path:'/mapControl',component:mapControl},
        {path:'/login',component:login},
        {path:'/register',component:register}
    ]
})


// router.beforeEach(function (to, from, next){
//     if (to.path === '/' || to.path === '/login' || to.path==='/register'){
//         next()
//     }else{
//         let token = localStorage.getItem('token')
//         if (token === null || token === '') {
//             next('/login');
//         } else {
//             next();
//         }
//     }
// })

export default router