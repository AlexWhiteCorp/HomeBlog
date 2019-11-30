<template>
    <div id="desktop-bar-menu" class="desktop-bar-menu">
        <div id="bar-menu-option-needlework" class="bar-menu-option" path="needlework">
            <img src="/images/icons/plant-pot.png" alt="" class="desktop-bar-menu-option-img">
            <div class="desktop-bar-menu-option-text">Вишивка</div>
        </div>
        <div id="bar-menu-option-school" class="bar-menu-option" path="school">
            <img src="/images/icons/plant-pot.png" alt="" class="desktop-bar-menu-option-img">
            <div class="desktop-bar-menu-option-text">Школа</div>
        </div>
        <div id="bar-menu-option-plants" class="bar-menu-option" path="plants">
            <img src="/images/icons/plant-pot.png" alt="" class="desktop-bar-menu-option-img">
            <div class="desktop-bar-menu-option-text">Вазони</div>
        </div>
    </div>
</template>
<script>
    export default {
        methods: {
            push(path){
                this.$router.push(path)
            }
        },
        mounted(){
            let component = this;
            let currOption = document.getElementById('bar-menu-option-' + document.location.pathname.split('/')[2]);
            selectOption(currOption, currOption);

            var options = document.getElementsByClassName('bar-menu-option');
            for(var i = 0; i < options.length; i++){
                options[i].onclick = function(){
                    var path = this.getAttribute('path');
                    component.push('/topics/' + path);
                    selectOption(currOption, document.getElementById('bar-menu-option-' + this.getAttribute('path')));
                }
            }

            function selectOption(opt1, opt2) {
                if(opt1 != null) opt1.style.background = '#90ee90';
                if(opt2 != null)opt2.style.background = '#4dc44d';
                currOption = opt2;
                if(document.location.pathname.indexOf('topics') != -1) setTimeout(function(){
                    changeTopic(currOption.getAttribute('path'));
                },50);
            }


        }
    }
</script>
<style>
    .desktop-bar-menu{
        position: fixed;
        top: 60px;
        left: 0px;
        background: lightgreen;
        min-height: 100%;
        width: 75px;

        text-align: center;
    }

    .bar-menu-option{
        min-height: 60px;
        max-height: 60px;
        padding: 15px 0px 15px 0px;
    }

    .bar-menu-option:hover{
        background: lightgreen;
        filter: brightness(0.8);
    }

    .desktop-bar-menu-option-img{
        width: 45px;
        height: 45px;
    }

    .desktop-bar-menu-option-text{
        color: darkgreen;
        font-size: 14px;
        filter: brightness(0.8);
    }

    @media screen and (max-width: 640px) {
        .desktop-bar-menu{
            background: #8ede8e;
            top: unset;
            left: 0;
            bottom: 0;
            z-index: 99999;
            width: 100%;
            min-height: 45px;
            max-height: 45px;
        }

        .bar-menu-option{
            float: left;
            width: 20%;
            min-height: 45px;
            max-height: 45px;
            padding: 0px 0px 0px 0px;

            text-align: center;
        }

        .bar-menu-option:hover{
            background: lightgreen;
            filter: brightness(0.8);
        }

        .desktop-bar-menu-option-img{
            width: 25px;
            height: 25px;
            margin: 4px 0px 0px 0px;
        }

        .desktop-bar-menu-option-text{
            color: darkgreen;
            font-size: 12px;
            line-height: 12px;
            filter: brightness(0.8);
        }
    }
</style>