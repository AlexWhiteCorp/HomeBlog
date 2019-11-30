import Vue from 'vue'
import VueRouter from 'vue-router'

//const postslist = () => import(/* webpackChunkName: "http://192.168.0.102:8000/postslist" */ "components/admin/postslist.vue")

import PostsCrudRepo from 'components/admin/postscrudrepo.vue'
import postslist from 'components/admin/postslist.vue'
import Empty from 'components/admin/empty.vue'


Vue.use(VueRouter)

const routes = [
    { path: '/adminbench', component: Empty},
    { path: '/adminbench/posts/:topic', component: postslist},
    { path: '/adminbench/posts/:topic/:type', component: PostsCrudRepo}
]

export default new VueRouter({
    mode: 'history',
    routes
})