<template>
    <div v-if="auth" id="user-popup-container" class="popup-container">
        <div class="popup-container-header row">
            <img class="popup-container-user-icon" src="/images/icons/user.png" alt="">
            <div class="popup-container-user-name" v-text="nickname">Nickname</div>
            <div id="user-popup-container-close-button" class="popup-container-close-button">×</div>
        </div>
        <div id="user-popup-container-item-1" class="popup-container-item"><a href="">• &nbsp;Мій акаунт</a></div>
        <div id="user-popup-container-item-2" class="popup-container-item"><a href="/adminbench">• &nbsp;Адмін Панель</a></div>
        <div id="user-popup-container-item-exit" class="popup-container-item">• &nbsp;Exit
            <form id="logoutForm" action="/logout" method="post" hidden="true">
                <input hidden type="submit" value="Sign Out"/>
            </form>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return{
                'auth' : FeData.auth,
                'nickname' : FeData.nickname
            }
        },
        mounted(){
            document.getElementById('user-popup-container-item-exit').onclick = function(){
                document.getElementById('logoutForm').submit();
                return false;
            }
            var desktopHeader = document.getElementById('desktop-header');
            var userPopupContainer = document.getElementById('user-popup-container');

            var showingUserPopupContainer = false;
            var userPopupContainerHeight = userPopupContainer.offsetHeight;

            userPopupContainer.style.top = '0px';
            userPopupContainer.style.left = window.innerWidth - userPopupContainer.offsetWidth - 9 + 'px';
            userPopupContainer.style.display = 'none';


            document.getElementById('header-right-user').onclick = showUserPopupContainer;
            document.getElementById('user-popup-container-close-button').onclick = hideUserPopupContainer;

            function showUserPopupContainer() {
                if(!showingUserPopupContainer){
                    showingUserPopupContainer = true;

                    userPopupContainer.style.display = 'block';
                    var timer = setInterval(function() {
                        userPopupContainer.style.minHeight = userPopupContainer.offsetHeight + 5 + 'px';
                        userPopupContainer.style.maxHeight = userPopupContainer.offsetHeight + 5 + 'px';
                        if(userPopupContainer.offsetHeight >= userPopupContainerHeight){
                            userPopupContainer.style.minHeight = userPopupContainerHeight + 'px';
                            userPopupContainer.style.maxHeight = userPopupContainerHeight + 'px';
                            clearInterval(timer);
                            window.onclick = function(e){
                                e = e || window.event;
                                if(getClickedElement(e).indexOf('user-popup-container') < 0){
                                    hideUserPopupContainer();
                                }
                            }
                        }
                    }, 5);
                }

                return false;
            }

            function hideUserPopupContainer() {
                if(showingUserPopupContainer){
                    window.onclick = null;
                    var timer = setInterval(function() {
                        userPopupContainer.style.minHeight = userPopupContainer.offsetHeight - 40 + 'px';
                        userPopupContainer.style.maxHeight = userPopupContainer.offsetHeight - 40 + 'px';
                        if(userPopupContainer.offsetHeight <= 60){
                            showingUserPopupContainer = false;
                            userPopupContainer.style.display = 'none';
                            userPopupContainer.style.minHeight = '60px';
                            userPopupContainer.style.maxHeight = '60px';
                            clearInterval(timer);
                        }
                    }, 5);
                }
                return false;
            }

            function getClickedElement(e){
                if (navigator.userAgent.match('MSIE') || navigator.userAgent.match('Gecko')) {
                    var elem = document.elementFromPoint(e.clientX,e.clientY);
                } else {
                    var elem = document.elementFromPoint(e.pageX,e.pageY);
                }
                return elem.id;
            }
        }
    }
</script>
<style>
    .popup-container{
        position: absolute;
        left: 250px;
        z-index: 9999;
        background: #90ee90;
        min-width: 250px;
        max-width: 250px;
        height: fit-content;

        border: solid 1.5px #055f24;
        box-shadow: 0px 5px 15px rgba(32, 91, 119, 0.5);

        overflow: hidden;

        font-size: 18px;
        line-height: 18px;
    }

    .popup-container-header{
        min-height: 56px;
        max-height: 56px;
        border-bottom: solid 2px #055f24;
    }

    .popup-container-user-icon{
        float: left;
        width: 30px;
        margin: 12px 0px 0px 10px;
    }

    .popup-container-user-name{
        float: left;
        margin: 20px 0px 0px 10px;

        font-size: 22px;
    }

    .popup-container-close-button{
        float: right;
        cursor: pointer;
        margin:  10px 10px 0px 0px;

        font-size: 28px;
    }

    .popup-container-close-button:hover{
        filter: brightness(1.5);
    }

    .popup-container-item{
        padding: 10px 10px 10px 25px;
        cursor: pointer;
    }

    .popup-container-item:hover{
        background: lightgreen;
        filter: brightness(0.8);
    }

    @media screen and (max-width: 640px) {
        .popup-container{
            background: #8ede8e;
            left: 10px;
            min-width: 99%;
            max-width: 99%;
        }

        .popup-container-header{
            min-height: 41px;
            max-height: 41px;
        }

        .popup-container-user-icon{
            margin: 6px 0px 0px 5px;
        }

        .popup-container-user-name{
            margin: 0px 0px 0px 5px;

            font-size: 22px;
            line-height: 45px;
        }

        .popup-container-close-button{
            margin:  0px 10px 0px 0px;

            font-size: 28px;
            line-height: 45px;
        }

        .popup-container-item{
            padding: 7px 10px 7px 10px;
        }
    }
</style>