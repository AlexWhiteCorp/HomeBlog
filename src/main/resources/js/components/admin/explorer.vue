<template>
    <div id="explorer" class="explorer">
        <div id="explorer-title" class="explorer-title"><strong>Меню</strong></div>
        <div id="explorer-body" class="explorer-body">
            <div id="posts-option" class="explorer-option">
                <div id="posts-option-title" class="explorer-option-title"  path="/adminbench/posts">▶ Статті</div>
                <div id="posts-option-nodes" class="explorer-option-nodes">
                    <div id="needlework-option" class="explorer-option">
                        <div id="needlework-option-title" class="explorer-option-title" path="/adminbench/posts/needlework">▶ Вишивка</div>
                        <div id="needlework-option-nodes" class="explorer-option-nodes"></div>
                    </div>
                    <div id="school-option" class="explorer-option">
                        <div id="school-option-title" class="explorer-option-title" path="/adminbench/posts/school">▶ Школа</div>
                        <div id="school-option-nodes" class="explorer-option-nodes"></div>
                    </div>
                    <div id="plants-option" class="explorer-option">
                        <div id="plants-option-title" class="explorer-option-title" path="/adminbench/posts/plants">▶ Вазони</div>
                        <div id="plants-option-nodes" class="explorer-option-nodes"></div>
                    </div>
                </div>
            </div>
            <div id="users-option" class="explorer-option">
                <div id="users-option-title" class="explorer-option-title" path="/adminbench/users">▶ Користувачі</div>
                <div id="users-option-nodes" class="explorer-option-nodes"></div>
            </div>
                <div id="statistics-option" class="explorer-option">
                    <div id="statistics-option-title" class="explorer-option-title" path="/adminbench/statistics">▶ Статистика</div>
                    <div id="statistics-option-nodes" class="explorer-option-nodes"></div>
                </div>
        </div>
    </div>
</template>
<script>
    import router from "../../router/router.admin";

    export default {
        methods: {
            open(path){
                this.$router.push(path);
            }
        },
        mounted(){
            document.getElementById('explorer-body').style.minHeight = window.innerHeight - 140 + 'px';
            document.getElementById('explorer-body').style.maxHeight = window.innerHeight - 140 + 'px';

            var opts = document.getElementsByClassName('explorer-option-title');
            for(var i = 0; i < opts.length; i++){
                opts[i].onclick = function(event){
                    var id = this.getAttribute('id').replace('-option-title', '');
                    var nodes = document.getElementById(id + '-option-nodes');
                    if(nodes.style.display !== 'block'){
                        openOption(this, nodes, true);
                    }
                    else{
                        closeOption(this, nodes, true);
                    }
                    event.stopPropagation();
                    return false;
                }
            }

            function openOption(option, nodes, push) {
                if(nodes.innerHTML.length != 0){
                    option.innerText = option.innerText.replace('▶', '▼');
                    option.style.marginLeft = '-3px';
                }
                else{
                    option.innerText = option.innerText.replace('▶', '');
                    option.style.marginLeft = '19px';
                }
                nodes.style.display = 'block';
                if(push)router.push(option.getAttribute('path'));
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

            var path = window.location.pathname;
            if(!openOptionByPath(path, true)){
                if(!openOptionByPath(path.replace('/' + path.split('/')[path.split('/').length - 1], ''), false))router.push('/adminbench');
            }

            function openOptionByPath(path, push){
                for(var i = opts.length - 1; i >= 0; i--){
                    if(opts[i].getAttribute('path') == path){
                        var id = opts[i].getAttribute('id').replace('-option-title', '');
                        var nodes = document.getElementById(id + '-option-nodes');
                        openOption(opts[i], nodes, push);
                        openOptionByPath(path.replace('/' + path.split('/')[path.split('/').length - 1], ''), false);
                        return true;
                    }
                }
                return false;
            }
        }
    }
</script>
<style>
    .explorer{
        float: left;
        width: 100%;
        margin: 0px 0px 0px 5px;

        text-align: center;
    }

    .explorer-title{
        background: #90ee90;
        min-height: 30px;
        max-height: 30px;
        border: 1px solid #193e19;

        font-size: 24px;
        line-height: 30px;
        color: #055f24;
    }

    .explorer-body{
        height: 100%;
        border: 2px solid #226625;
        border-top: none;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
        padding-top: 5px;

        text-align: left;
    }

    .explorer-option{
        margin: 5px 0px 5px 20px;
    }

    .explorer-option-title{
        width: fit-content;
        cursor: pointer;

        font-size: 18px;
    }

    .explorer-option-nodes{
        display: none;
    }
</style>