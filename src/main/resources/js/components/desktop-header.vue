<template>
    <div id="desktop-header" class="desktop-header row">
        <div class="desktop-header-left row">
            <div class="desktop-header-logo-div">
                <img src="/images/logo.png" alt="" id="desktop-header-logo" class="desktop-header-logo-img">
            </div>
        </div>
        <div class="desktop-header-center">
            <span >
                <strong class="desktop-header-title" style="color: #055f24">Home</strong>
                <strong class="desktop-header-title" style="color: #557112">Blog</strong>
            </span>
        </div>
        <div id="desktop-header-right" class="desktop-header-right">
            <div v-if="auth" id="header-right-user" class="row">
                <img id="user-icon" src="/images/icons/user.png" alt="" class="desktop-header-user-icon">
                <strong id="nickname" class="desktop-header-user-text" v-text="nickname"></strong>
            </div>
            <div v-else>
                <a href="/login" class="authorityLink">Ввійти </a>
                <div style="display: inline">|</div>
                <a href="/registration" class="authorityLink">Зареєструватися</a>
            </div>
        </div>
    </div>
</template>
<script>
    import router from "../router/router";

    export default {
        data() {
            return{
                'auth' : FeData.auth,
                'nickname' : FeData.nickname
            }
        },
        methods:{
            showHome() {
                this.$router.push('/')
            }
        },
        mounted(){
            setTimeout(function () {
                document.getElementById('desktop-header-logo').onclick = function () {
                    router.push('/');
                }
            }, 100);
        }
    }
</script>
<style>
    .desktop-header{
        background: lightgreen;
        width: 100%;

        min-height: 60px;
        max-height: 60px;
        margin: 0px 0px 20px 0px;

        text-align: center;
    }

    .desktop-header-left{
        float: left;
        min-width: 75px;
    }

    .desktop-header-logo-div{
        position: fixed;
        left: 0px;
        background: #006400;
        min-width: 75px;
        max-width: 75px;
        min-height: 60px;
        max-height: 60px;
    }

    .desktop-header-logo-img{
        width: 50px;
        height: 50px;
        margin: 5px;
        outline: none;

        cursor: pointer;
    }

    .desktop-header-center{
        float: left;
        margin: 0px 0px 0px 85px;
    }

    .desktop-header-title{
        font-size: 42px;
        line-height: 60px;
        outline: none;
    }

    .desktop-header-right{
        float: right;
        padding: 0px 30px 0px 0px;

        color: #055f24;
        font-size: 18px;
        line-height: 60px;
    }

    #header-right-user{
        cursor: pointer;
    }

    .authorityLink{
        display: inline;
    }

    .authorityLink:hover{
        filter: brightness(1.3);
    }

    .desktop-header-user-icon{
        float: left;
        width: 30px;
        margin: 15px 5px 15px 0px;
    }

    .desktop-header-user-text{
        float: left;

        font-size: 18px;
        line-height: 60px;
    }

    @media screen and (max-width: 640px) {
        .desktop-header{
            position: fixed;
            background: #8ede8e;
            min-height: 45px;
            max-height: 45px;
        }

        .desktop-header-left{
            min-width: fit-content;
            margin: 0px 0px 0px 0px;
        }

        .desktop-header-logo-div{
            position: relative;
            min-width: 50px;
            max-width: 50px;
            min-height: 45px;
            max-height: 45px;
        }

        .desktop-header-logo-img{
            width: 35px;
            height: 35px;
            margin: 5px;
        }

        .desktop-header-center{
            float: left;
            margin: 0px 0px 0px 10px;
        }

        .desktop-header-title{
            font-size: 30px;
            line-height: 45px;
        }

        .desktop-header-right{
            float: right;
            padding: 0px 0px 0px 0px;

            font-size: 8px;
            line-height: 45px;
        }

        #header-right-user{
            cursor: pointer;
        }

        .desktop-header-user-icon{
            float: left;
            width: 30px;
            margin: 8px 5px 7px 0px;
        }

        .desktop-header-user-text{
            display: none;
        }
    }
</style>