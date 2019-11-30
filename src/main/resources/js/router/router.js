import Vue from 'vue'
import VueRouter from 'vue-router'

//import Post from 'components/Post.vue'
//import Home from 'components/Home.vue'

import Home from "components/Home.vue"
import Post from "components/Post.vue"
import Topic from "components/Topic.vue"


Vue.use(VueRouter)


const routes = [
    { path: '/', component: Home },
    { path: '/topics/:topicName', component: Topic },
    { path: '/post/:topicName/:postId', component: Post }
]

export default new VueRouter({
    mode: 'history',
    routes
})