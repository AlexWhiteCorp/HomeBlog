import Vue from 'vue'
import App from 'pages/App.vue'
import router from 'router/router'

Vue.config.devtools = true;

let app = new Vue({
    el: '#app',
    router,
    render: a => a(App)
})

