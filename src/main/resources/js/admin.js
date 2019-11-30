import Vue from 'vue'
import Admin from 'pages/Admin.vue'
import router from 'router/router.admin'

Vue.config.devtools = true;

let app = new Vue({
    el: '#admin',
    router,
    render: a => a(Admin)
})

