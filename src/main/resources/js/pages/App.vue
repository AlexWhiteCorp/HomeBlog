<template>
    <div>
        <desktop-header/>
        <desktop-bar-menu/>
        <user-popup-container v-if="auth"/>
        <div class="row">
            <div id="content" class="content">
                <div id="in-content" class="in-content">
                    <router-view ref="router"></router-view>
                </div>
            </div>
            <div id="sidebar-right" class="sidebar-right">
                <div id="in-sidebar-right" class="in-sidebar-right">Right Sidebar - place for advertising</div>
            </div>
        </div>
    </div>
</template>
<script>
    import DesktopBarMenu from 'components/desktop-bar-menu.vue'
    import DesktopHeader from 'components/desktop-header.vue'
    import UserPopupContainer from 'components/user-popup-container.vue'
    import DesktopFooter from 'components/desktop-footer.vue'

    export default {
        data(){
            return{
                'auth' : FeData.auth
            }
        },
        components: {
            'desktop-bar-menu': DesktopBarMenu,
            'desktop-header': DesktopHeader,
            'user-popup-container': UserPopupContainer,
            'desktop-footer': DesktopFooter
        },
        methods: {
            showLogin(){
                this.$router.push('/auth')
            }
        },
        mounted(){
            var content = document.getElementById('content');
            var sidebarRight = document.getElementById('sidebar-right');

            var minHeight = window.innerHeight - document.getElementById('desktop-header').offsetHeight - 30 + 'px';

            document.getElementById('in-content').style.minHeight = minHeight;
            document.getElementById('sidebar-right').style.width =
                window.innerWidth - document.getElementById('content').offsetLeft - document.getElementById('content').offsetWidth - 20 + 'px';
            document.getElementById('in-sidebar-right').style.minHeight = minHeight;
            document.getElementById('in-sidebar-right').style.maxHeight = minHeight;

            loadPostsState();

            function loadPostsState() {
                axios
                    .get('/posts/state.json')
                    .then(function(response){
                        console.log((response.data));
                        sessionStorage.setItem('postsState', JSON.stringify(response.data));
                    });
            }
        }
    }
</script>
<style>
    .content{
        float: left;
    }

    .in-content{
        width: 950px;
        padding: 0px 10px 15px 175px;

        text-align: center;
    }

    .sidebar-right{
        float: left;
    }

    .in-sidebar-right{
        border: 2px solid grey;
        margin: 0px 10px 0px 5px;

        text-align: center;
    }

    @media screen and (max-width: 640px) {
        .content{
            margin: 50px 0px 0px 0px;
        }

        .in-content{
            width: fit-content;
            padding: 0px;

            text-align: center;
        }

        .sidebar-right{
            display: none;
        }
    }
</style>