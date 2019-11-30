<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <div>
        <div class="title"><strong id="post-title"></strong></div>
        <div id="post-date" class="date"></div>
        <div id="post-text" class="text"></div>


        <div id="comments-div">
            <div id="commentsHeader" class="commentsHeader"><strong>Коментарі</strong></div>
            <div v-if="auth" id="add-comment" class="comment row">
                <div class="comment-head row">
                    <img class="comment-user-icon" v-bind:src="usericon" alt="">
                    <div class="comment-user-nickname"><strong>{{nickname}}</strong></div>
                </div>
                <textarea id="new-comment-text" class="comment-text scroll" type="text" placeholder="Введіть текст повідомлення..."></textarea>
                <div id="add-comment-button" class="add-comment-button" @click="addNewComment">Відправити</div>
            </div>
            <div v-else class="notAuthorizedText">
                Тільки <strong>авторизовані</strong> користувачі можуть залишати коментарі.
                <a class="notAuthorizedText" href="/login"><Strong>Ввійти</Strong></a>
            </div>
            <div id="comments"></div>
            <div id="loading"><strong id="loadingText">Завантаження коментарів</strong></div>
        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return{
                'auth': FeData.auth,
                'nickname': FeData.nickname,
                'usericon': FeData.usericon
            }
        },
        mounted() {
            if(FeData.auth){
                document.getElementById('new-comment-text').style.minWidth =
                    document.getElementsByClassName('comment-head')[0].offsetWidth - 70 + 'px';
                document.getElementById('new-comment-text').onfocus = function(){
                    this.style.minHeight = '100px';
                }

                document.getElementById('comments-div').style.display = 'none';
            }

            if(localStorage.getItem(document.location.href.split('//')[1]) == null){
                loadPostInfo('/post/' + this.$route.params.topicName + '/' + this.$route.params.postId + '.json');
            }
            else{
                var data = JSON.parse(localStorage.getItem(document.location.href.split('//')[1]));
                //Change value of max delta time(300000) to 100 for Hot Spot
                if(new Date().getTime() - data.time > 100){
                    loadPostInfo('/post/' + this.$route.params.topicName + '/' + this.$route.params.postId + '.json');
                }
                else {
                    setPostInfo(data);
                    console.log('Loaded from cache');
                }
            }

            function setPostInfo(data) {
                document.getElementById('post-title').innerText = data.title;
                document.getElementById('post-date').innerText = data.date;
                document.getElementById('post-text').innerHTML = data.preview + data.content;

                document.getElementById('comments-div').style.display = 'block';
            }

            function loadPostInfo(path) {
                console.log('Go to Server...');
                axios
                    .get(path)
                    .then(function(response) {
                        response.data['time'] = new Date().getTime();
                        localStorage.setItem(document.location.href.split('//')[1], JSON.stringify(response.data));
                        setPostInfo(response.data);
                    });
            }

            var page = 1;
            loadComments(page);

            var comments = document.getElementById('comments');
            function loadComments() {
                var loadingDiv = document.getElementById('loadingText');
                var timer = setInterval(function() {
                    if(loadingDiv.innerText.indexOf('...') > -1)loadingDiv.innerText = loadingDiv.innerText.replace('...', '');
                    else loadingDiv.innerText += '.';
                }, 450);
                axios
                    .get('/comments/load', {
                        params: {
                            'topic' : document.location.href.split('/')[4],
                            'article' : document.location.href.split('/')[5],
                            'page' : page
                        }
                    })
                    .then(function(response){
                        page++;
                        clearInterval(timer);
                        loadingDiv.style.display = 'none';
                        for(var i in response.data){
                            comments.innerHTML += '<div class="comment">\n' +
                                '                    <div class="comment-head row">\n' +
                                '                        <img class="comment-user-icon" src="' + response.data[i].icon + '" alt="">\n' +
                                '                        <div class="comment-user-nickname"><strong>' + response.data[i].nickname + '</strong></div>\n' +
                                '                        <div class="comment-date">' + response.data[i].date + '</div>\n' +
                                '                    </div>\n' +
                                '                    <div class="comment-text scroll">' + response.data[i].text + '</div>\n' +
                                '                </div>';
                        }
                    });
            }
        },
        methods:{
            addNewComment(){
                document.getElementById('new-comment-text').style.minHeight = '40px';
                if(document.getElementById('new-comment-text').value != ''){
                    axios
                        .get('/comments/add', {
                            params: {
                                'topic' : document.location.href.split('/')[4],
                                'article' : document.location.href.split('/')[5],
                                'text' : document.getElementById('new-comment-text').value
                            }
                        })
                        .then(function(response){
                            if(response.data.id == 1){
                                var comments = document.getElementById('comments');
                                var img = document.getElementById('user-icon').src;
                                var nickname = document.getElementById('nickname').innerText.replace('Вітаю, ', '').replace('!', '');
                                var date = response.data.text;
                                var text = document.getElementById('new-comment-text').value;

                                comments.innerHTML = '<div class="comment">\n' +
                                    '                <div class="comment-head row">\n' +
                                    '                    <img class="comment-user-icon" src="' + img + '" alt="">\n' +
                                    '                    <div class="comment-user-nickname"><strong>' + nickname + '</strong></div>\n' +
                                    '                    <div class="comment-date">' + date + '</div>\n' +
                                    '                </div>\n' +
                                    '                <div class="comment-text scroll">' + text + '</div>\n' +
                                    '            </div>' + comments.innerHTML;

                                document.getElementById('new-comment-text').value = '';
                            }
                        });
                }
            }
        }
    }
</script>
<style>
    .in-content{
        text-align: left;
        color: #091d09;
    }

    .title{
        margin: 15px 0px 0px 30px;

        text-align: left;
        font-size: 28px;
        line-height: 30px;
        color: #055f24;
    }

    .date{
        margin: 0px 0px 0px 30px;

        text-align: left;
        font-size: 16px;
        color: #055f24;
    }

    .img{
        background: grey;
        height: 250px;
        width: 70%;
        margin: 10px 15% 10px 15%;
    }

    .text{
        margin: 10px 20px 20px 25px;

        text-align: left;
        font-size: 20px;
        line-height: 27px;
    }

    .commentsHeader{
        border-bottom: 1px solid #055f24;
        margin: 10px 70px 20px 70px;

        font-size: 18px;
    }

    .comment{
        margin: 15px 70px 15px 70px;
    }

    .comment-head{

    }

    .comment-user-icon{
        float: left;
        max-width: 35px;
        border: 1px solid transparent;
        border-radius: 1000px;
        overflow: hidden;
    }

    .comment-user-nickname{
        float: left;
        margin: 0px 0px 0px 10px;
    }

    .comment-date{
        float: right;
    }

    .comment-text{
        background: none;
        min-height: 40px;
        max-height: 100px;
        margin: -12px 0px 0px 45px;
        border: 1px solid #055f24;
        border-radius: 3px;
        padding: 7px 10px 7px 10px;
        overflow-y: auto;

        text-align: left;
        color: #091d09;
        font-size: 16px;
        line-height: 20px;
    }

    .add-comment-button{
        float: right;
        width: fit-content;
        margin: 10px 0px 0px 45px;
        padding: 5px 7px 5px 7px;
        border: 1px solid #193e19;
        border-radius: 3px;

        font-weight: bold;
    }

    .add-comment-button:hover{
        background: darkgreen;

        color: #c4f8c4  ;
    }

    #new-comment-text{
        background: #aff5af;
        padding: 2px 10px 7px 10px;
        resize: none;
    }

    .notAuthorizedText{
        margin: 35px 0px 50px 0px;

        text-align: center;
        font-size: 20px;
        filter: brightness(1.5);
    }

    a.notAuthorizedText{
        border-bottom: 1px solid #091d09;
    }

    a.notAuthorizedText:hover{
        color: #193e19;
    }

    #loading{
        margin: 0px 0px 50px 0px;

        text-align: center;
        font-size: 24px;
        color: #091d09;
    }

    @media screen and (max-width: 640px) {
        .in-content{
            margin: 10px 10px 15px 5px;

            text-align: left;
        }

        .title{
            margin: 5px 0px 0px 5px;

            text-align: left;
            font-size: 20px;
            line-height: 30px;
        }

        .date{
            margin: 0px 0px 0px 15px;

            text-align: left;
            font-size: 16px;
            color: #055f24;
        }

        .img{
            background: grey;
            height: 250px;
            width: 70%;
            margin: 10px 15% 10px 15%;
        }

        .text{
            margin: 10px 5px 20px 10px;

            text-align: left;
            font-size: 18px;
            line-height: 28px;
        }

        .commentsHeader{
            border-bottom: 1px solid #055f24;
            margin: 10px 20px 15px 15px;
            padding-bottom: 3px;

            font-size: 20px;
        }

        .comment{
            margin: 15px 10px 15px 10px;
        }

        .comment-user-icon{
            float: left;
            max-width: 35px;
            border: 1px solid transparent;
            border-radius: 1000px;
            overflow: hidden;
        }

        .comment-user-nickname{
            float: left;
            margin: 0px 0px 0px 10px;
        }

        .comment-date{
            float: right;
        }

        .comment-text{
            background: none;
            min-height: 40px;
            max-height: 100px;
            margin: -12px 0px 0px 45px;
            border: 1px solid #055f24;
            border-radius: 3px;
            padding: 7px 10px 7px 10px;
            overflow-y: auto;


            text-align: left;
            color: #091d09;
            font-size: 16px;
            line-height: 20px;
        }

        .add-comment-button{
            float: right;
            width: fit-content;
            height: fit-content;
            margin: 10px 0px 0px 45px;
        }

        #new-comment-text{
            background: #aff5af;
            padding: 2px 10px 7px 10px;
            resize: none;
        }

        .notAuthorizedText{
            margin: 35px 0px 50px 0px;

            text-align: center;
            font-size: 20px;
            filter: brightness(1.5);
        }

        a.notAuthorizedText{
            border-bottom: 1px solid #091d09;
        }

        a.notAuthorizedText:hover{
            color: #193e19;
        }

        #loading{
            margin: 0px 0px 50px 0px;

            text-align: center;
            font-size: 24px;
            color: #091d09;
        }
    }
</style>