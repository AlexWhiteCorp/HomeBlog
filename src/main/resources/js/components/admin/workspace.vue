<template>
    <div id="workspace" class="workspace">
        <div id="workspace-bar-menu" class="workspace-bar-menu row">
            <div id="workspace-bar-menu-title" class="workspace-bar-menu-title">Панель Адміністратора</div>
            <div id="workspace-close-button" class="workspace-bar-menu-button">✖</div>
            <div id="workspace-bar-menu-buttons" class="workspace-bar-menu-buttons">
            </div>
        </div>
        <div id="workspace-body" class="workspace-body scroll">
            <router-view></router-view>
        </div>
    </div>
</template>
<script>
    import router from "../../router/router.admin";

    export default {
        methods: {
        },
        mounted(){
            var workSpaceBody = document.getElementById('workspace-body');
            workSpaceBody.style.minHeight = window.innerHeight - 140 + 'px';
            workSpaceBody.style.maxHeight = window.innerHeight - 140 + 'px';

            document.getElementById('workspace-close-button').onclick = function(){
                var opts = document.getElementsByClassName('explorer-option-title');
                for(var i = 0; i < opts.length; i++){
                    var id = opts[i].getAttribute('id').replace('-option-title', '');
                    var nodes = document.getElementById(id + '-option-nodes');
                    if(nodes.style.display === 'block')closeOption(opts[i], nodes, false);
                }
                router.push('/adminbench');
            }

            function closeOption(option, nodes, push) {
                if(nodes.innerHTML.length != 0){
                    option.innerText = option.innerText.replace('▼', '▶');
                }
                else{
                    option.innerText = '▶ ' + option.innerText;
                }
                option.style.marginLeft = '0px';
                nodes.style.display = 'none';
                if(push){
                    var p = option.getAttribute('path');
                    router.push(p.replace('/' + p.split('/')[p.split('/').length - 1], ''));
                    for(var i = 0; i < opts.length; i++){
                        if(opts[i].getAttribute('path').indexOf(p) > -1){
                            var id = opts[i].getAttribute('id').replace('-option-title', '');
                            var nodes = document.getElementById(id + '-option-nodes');
                            if(nodes.style.display === 'block') closeOption(opts[i], nodes, false);
                        }

                    }
                }
            }
        }
    }
</script>
<style>
    .workspace{
        float: left;
        margin: 0px 15px 0px 15px;
        width: 100%;
        height: 100%;
    }

    .workspace-bar-menu{
        background: #90ee90;
        min-height: 30px;
        max-height: 30px;
        border: 1px solid #193e19;

        font-size: 24px;
        line-height: 30px;
        color: #055f24;
        text-align: right;
    }

    workspace-bar-menu-title{
        float: left;
        width: fit-content;
        margin: 0px 0px 0px 10px;

        font-size: 18px;
        line-height: 30px;
        font-weight: bold;
    }

    .workspace-bar-menu-buttons{
        float: right;
        width: fit-content;
    }

    .workspace-bar-menu-button{
        float: right;
        width: fit-content;
        min-width: 30px;
        height: 30px;
        padding: 0px 5px 0px 5px;
        border-left: 1px solid #193e19;
        cursor: pointer;

        text-align: center;
        font-size: 18px;
        line-height: 30px;
        font-weight: bold;
    }

    .workspace-body{
        height: 100%;
        border: 2px solid #226625;
        border-top: none;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
        padding-top: 5px;
        overflow-y: auto;
    }
</style>