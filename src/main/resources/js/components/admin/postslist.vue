<template>
    <div>
        <div class="posts-list-header row">
            <div class="posts-list-header-id">ID</div>
            <div class="posts-list-header-title">Заголовок</div>
        </div>
        <div id="posts-list"></div>
        <div id="posts-list-menu" hidden>
            <div id="posts-list-update-button" class="workspace-bar-menu-button">оновити</div>
            <div id="posts-list-add-button" class="workspace-bar-menu-button button" path="/adminbench/posts/#topic/add">додати</div>
        </div>
        <div id="list-item-template" hidden>
            <div id="#id" class="list-item row">
                <div class="list-item-id">#id</div>
                <div class="list-item-title">#title</div>
                <div class="list-item-buttons">
                    <div id="#id-list-item-delete" class="delete list-item-button">
                        <img src="/images/icons/admin/delete.png" alt="" class="button-img">
                    </div>
                    <div class="list-item-button button" path="/adminbench/posts/#topic/change?id=#id">
                        <img src="/images/icons/admin/change.png" alt="" class="button-img">
                    </div>
                    <div id="#id-list-item-open" class="open list-item-button">
                        <img src="/images/icons/admin/open.png" alt="" class="button-img">
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import router from "../../router/router.admin";

    export default {
        data(){
            return{
                'topic' : this.$route.params.topic,
            }
        },
        methods: {
        },
        mounted(){
            document.getElementById('workspace-bar-menu-title').innerText = 'Редагування - список статтей';
            document.getElementById('workspace-bar-menu-buttons').innerHTML = document.getElementById('posts-list-menu').innerHTML;

            var topic =  this.$route.params.topic;
            var postsList = document.getElementById('posts-list');
            var listItemTemplate = document.getElementById('list-item-template');

            document.getElementById('posts-list-update-button').onclick = function(){
                loadPostslist(topic);
            }

            loadPostslist(topic);

            function loadPostslist(topic) {
                axios
                    .get('/posts/' + topic + '/list.json')
                    .then(function(response){
                        postsList.innerHTML = '';
                        for(var i in response.data){
                            postsList.innerHTML = listItemTemplate.innerHTML.replace(/#id/g, i).replace(/#title/g, response.data[i])
                                                + postsList.innerHTML;
                        }

                        var buttons = document.getElementsByClassName('button');
                        for(var i = 0; i < buttons.length; i++){
                            buttons[i].onclick = function () {
                                router.push(this.getAttribute('path').replace('#topic', topic));
                            }
                        }

                        var openButtons = document.getElementsByClassName('open');
                        for(var i = 0; i < openButtons.length; i++){
                            openButtons[i].onclick = function(){
                                window.open('/post/' + topic + '/' + this.getAttribute('id').split('-')[0], '_blank');
                            }
                        }

                        var deleteButtons = document.getElementsByClassName('delete');
                        for(var i = 0; i < deleteButtons.length; i++){
                            deleteButtons[i].onclick = function(){
                                var id = this.getAttribute('id').split('-')[0];
                                deletePost(topic, id, function () {
                                    document.getElementById(id).remove();
                                })
                            }
                        }
                    });
            }
        }
    }
</script>
<style>
    .posts-list-header{
        margin: 5px 5px 0px 10px;
        border-bottom: 1px solid #193e19;

        font-size: 18px;
        line-height: 26px;
        color: #193e19;
    }

    .posts-list-header-id{
        float: left;
        min-width: 45px;
        padding: 0px 10px 0px 0px;
        border-right: 1px solid #193e19;

        text-align: right;
        font-weight: bold;
    }

    .posts-list-header-title{
        float: left;
        padding: 0px 0px 0px 10px;

        text-align: center;
        font-weight: bold;
    }

    .list-item{
        margin: 0px 0px 0px 10px;
        border-bottom: 1px solid #193e19;

        font-size: 18px;
        line-height: 26px;
        color: #193e19;
    }

    .list-item:hover{}

    .list-item-id{
        float: left;
        min-width: 45px;
        padding: 5px 10px 5px 0px;
        border-right: 1px solid #193e19;

        text-align: right;
        font-weight: bold;
    }

    .list-item-title{
        float: left;
        padding: 5px 0px 5px 10px;
    }

    .list-item-buttons{
        min-width: 100px;
        min-height: 30px;
    }

    .list-item-buttons:hover .list-item-button{
        visibility: visible;
    }

    .list-item-button{
        float: right;
        margin: 0px 10px 0px 0px;
        padding: 5px 0px 3px 10px;
        border-left: 1px solid #193e19;
        visibility: hidden;
    }


    .button-img{
        height: 20px;
        cursor: pointer;
        }
</style>