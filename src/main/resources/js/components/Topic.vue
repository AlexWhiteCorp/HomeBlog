<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div>
        <div id="postsList">
            <div v-text="'Topic'">Topic</div>
        </div>
        <div id="post-preview-template" hidden>
            <div class="articleShotInfo">
                <div id="post-#postId-title" class="articleShotInfoTitle"><strong>#title</strong></div>
                <div class="articleShotInfoDate">#date</div>
                <div class="articleShotInfoContent">#preview</div>
                <div class="continueReadButtonDiv"><a id="post-#postId-button" class="greenButton">Читати далі</a></div>
            </div>
        </div>
    </div>
</template>
<script>
    import router from "../router/router";

    export default {
        methods: {
            showPost(id){
                this.$router.push('/post/' + this.$route.params.topicName + '/' + id)
            }
        },
        mounted() {
            var mobile = false;
            if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|BB|PlayBook|IEMobile|Windows Phone|Kindle|Silk|Opera Mini/i.test(navigator.userAgent)) {
                mobile = true;
            }
            var currTopic = document.location.href.split('/')[4];

            var postsList = document.getElementById('postsList');
            var postPreviewTemplate = document.getElementById('post-preview-template').innerHTML;

            function loadPostPreview(path) {
                console.log('Load');
                axios
                    .get(path)
                    .then(function(response){
                        response.data['time'] = new Date().getTime();
                        response.data['topicName'] = response.config.url.split('/')[2];
                        response.data['postId'] = response.config.url.split('/')[4].split('.')[0];
                        if(mobile)response.data.preview = response.data.preview.substring(0, 270) + '...';

                        sessionStorage.setItem('preview/ ' + response.data.topicName + '/' + response.data.postId, JSON.stringify(response.data));

                        setPostPreview(response.data);
                    });
            }
            
            function setPostPreview(data) {
                postsList.innerHTML += postPreviewTemplate.replace(/#postId/g, data.postId)
                                                          .replace(/#title/g, data.title)
                                                          .replace(/#date/g, data.date)
                                                          .replace(/#preview/g, data.preview);
                setTimeout(function(){
                    document.getElementById('post-' + data.postId + '-title').onclick = function(){
                        window.scrollTo(0, 0);
                        router.push('/post/' + data.topicName + '/' + data.postId)
                    }

                    document.getElementById('post-' + data.postId + '-button').onclick = function(){
                        window.scrollTo(0, 0);
                        router.push('/post/' + data.topicName + '/' + data.postId)
                    }
                }, 50);
            }

            function changeTopic(topic) {
                var state = JSON.parse(sessionStorage.getItem('postsState'))[topic];
                var postsList = document.getElementById('postsList');
                postsList.innerHTML = '';
                for(var i = state; i >= state - 9; i--){
                    if(i < 1) break;
                    if(sessionStorage.getItem('preview/ ' + topic + '/' + i) == null){
                        loadPostPreview('/post/' + topic + '/previews/' + i + '.json')
                    }
                    else{
                        var data = JSON.parse(sessionStorage.getItem('preview/ ' + topic + '/' + i));
                        if(new Date().getTime() - data.time > 100)loadPostPreview('/post/' + topic + '/previews/' + i + '.json');
                        else setPostPreview(data);
                    }
                }
            }
            window.changeTopic = changeTopic;
        }
    }
</script>
<style>
    .articleShotInfo{
        width: 100%;
        min-height: 250px;
        max-height: 600px;
        margin: 10px 0px 30px 0px;
        overflow: hidden;

        text-align: left;
        color: #091d09;
    }

    .articleShotInfoTitle{
        margin: 5px 0px 0px 30px;
        cursor: pointer;

        font-size: 28px;
        line-height: 30px;
        color: #055f24;
    }

    .articleShotInfoDate{
        margin: 0px 0px 0px 30px;

        font-size: 16px;
        color: #055f24;
    }

    strong.articleShotInfoTitle:hover{
        filter: brightness(1.5);
    }

    .articleShotInfoContent{
        margin: 10px 0px 0px 0px;

        text-align: left;
    }

    .articleShotInfoImg{
        background: grey;
        height: 250px;
        width: 70%;
        margin: 0px 15% 0px 15%;
    }

    .continueReadButtonDiv{
        margin: 20px 0px 15px 0px;
    }

    .greenButton{
        width: 150px;
        height: 75px;
        margin: 10px 0px 0px 45px;
        padding: 7px 10px 7px 10px;
        border: 1px solid darkgreen;
        border-radius: 3px;
        cursor: pointer;

        color: darkgreen;
    }

    .greenButton:hover{
        background: darkgreen;

        color: #c4f8c4  ;
    }

    @media screen and (max-width: 640px) {
        .articleShotInfo{
            width: 100%;
            min-height: 100px;
            max-height: 1000px;
            margin: 10px 10px 15px 5px;
        }

        .articleShotInfoTitle{
            margin: 5px 0px 0px 0px;

            font-size: 20px;
            line-height: 30px;
        }

        .articleShotInfoDate{
            margin: 0px 0px 0px 10px;

            font-size: 16px;
            color: #055f24;
        }

        strong.articleShotInfoTitle:hover{
            filter: brightness(1.5);
        }

        .articleShotInfoContent{
            margin: 10px 0px 0px 5px;
        }

        .articleShotInfoImg{
            background: grey;
            height: 250px;
            width: 70%;
            margin: 0px 15% 0px 15%;
        }

        .continueReadButtonDiv{
            margin: 20px 0px 15px 0px;
        }

        .greenButton{
            width: 100px;
            height: 50px;
            margin: 10px 0px 0px 15px;
            padding: 7px 10px 7px 10px;
            border: 1px solid darkgreen;
            border-radius: 3px;
            cursor: pointer;

            color: darkgreen;
        }

        .greenButton:hover{
            background: darkgreen;

            color: #c4f8c4  ;
        }
    }
</style>