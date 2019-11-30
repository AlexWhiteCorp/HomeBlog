<template>
    <div>
        <desktop-header/>
        <div class="row">
            <div id="sidebar-left" class="sidebar-left">
                <div id="in-sidebar-left" class="in-sidebar-left">
                    <explorer/>
                </div>
            </div>
            <div id="content" class="content">
                <div id="in-content" class="in-content">
                    <workspace/>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import router from "../router/router.admin";

    import DesktopHeader from 'components/desktop-header.vue'
    import Explorer from 'components/admin/explorer.vue'
    import WorkSpace from 'components/admin/workspace.vue'

    export default {
        data(){
            return{
                'auth' : FeData.auth,
            }
        },
        components: {
            'desktop-header': DesktopHeader,
            'explorer': Explorer,
            'workspace': WorkSpace
        },
        mounted(){
            setTimeout(function () {
                document.getElementById('desktop-header-logo').onclick = function () {
                    document.location.href = '/';
                }
            }, 100);
        }
    }
</script>
<style>
    @media screen and (max-width: 640px) {
        .in-sidebar-left{
            margin: 50px 0px 0px 0px;
        }

        .in-content{
            margin: 50px 0px 0px 0px;
        }
    }
</style>