
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue'),
  
  },{
    path:'/catalogue',
    name:'catalogue',
    component: () => import(/* webpackChunkName: "about" */ '../views/font/Catalogue.vue'),
  },
  {
    path:'/404',
    name:'404',
    component: () => import(/* webpackChunkName: "about" */ '../views/404/404.vue'),
  },
  {
    path:'/reads',
    name:'reads',
    component: () => import(/* webpackChunkName: "about" */ '../views/font/Reads.vue'),
  },
  {
    path: '/',
    name: 'Zhuye',
    component: ()=>import('../views/font/zhuye.vue'),
    redirect:'/homepage',
    children:
    [
      {path:"/homepage",name:"Homepage",component:()=>import('../views/font/Homepage.vue')},
      {path:"/fantasy",name:"fantasy",component:()=>import('../views/font/fantasy.vue')},
      {path:"/sciencefiction",name:"sciencefiction",component:()=>import('../views/font/ScienceFiction.vue')},
      {path:"/xianxia",name:"xianxia",component:()=>import('../views/font/Xianxia.vue')},
      {path:"/briefbook",name:"briefbook",component:()=>import('@/views/font/BriefBook.vue')},
     {path:"/search",name:"Search",component:()=>import('../views/font/Search.vue')}
    ]
  } 
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export const setrouter= () =>{
  const muenrouter= localStorage.getItem("muen")
  if(muenrouter){
    const muenr={path: '/manage',name:'Manage',component: () => import(/* webpackChunkName: "about" */ '../views/Manage.vue'),//显示主页
    children:[]}
    const s=JSON.parse(muenrouter)
    s.forEach(item=>{
      if(item.path){
        const muenc={path:item.path,name:item.name,component:() => import(/* webpackChunkName: "about" */ '../views/'+item.pagepath+'.vue')}
        muenr.children.push(muenc)
      }else if(item.children.length){
        item.children.forEach(item=>{
        const muens={path:item.path,name:item.name,component:() => import(/* webpackChunkName: "about" */ '../views/'+item.pagepath+'.vue')}
        muenr.children.push(muens)
      })
      }
    })
    router.addRoute(muenr)
  }
}
// export const setcategory =()=>{
//   const category=localStorage.getItem("category")
//   if(category){
//     const category1={path: '/',name: 'Zhuye',component: ()=>import('../views/font/zhuye.vue'),redirect:'/homepage',children:[
//       {path:"/briefbook",name:"briefbook",component:()=>import('@/views/font/BriefBook.vue')},
//       {path:"/homepage",name:"homepage",component:()=>import('@/views/font/Homepage.vue')}
//     ]}
//     const s=JSON.parse(category)
//     s.forEach(item=>{
//       const category2={path:item.path.replace("/",""),name:item.name,component:()=>import('../views/font/'+item.pagepath+'.vue')}
//       category1.children.push(category2)
//     })
//     router.addRoute(category1)
//   }
// }
// setcategory()
setrouter()
 router.beforeEach((to,from,next)=>{
  if(!to.matched.length){
const muens=localStorage.getItem("muen")
   if(muens){
      next("/404")
   }else{
     next("/login")
     routes.push("/login")
   }
  }
   //路由放行
  next()
 })
export default router
