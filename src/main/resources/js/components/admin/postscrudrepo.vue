<template>
    <div id="add-post">
        <div class="input-field-div row">
            <div class="input-field-label">Категорія:</div>
            <input id="topic-input" class="input-field" type="text" spellcheck="false" autocomplete="off">
        </div>
        <div class="input-field-div row">
            <div class="input-field-label">ID:</div>
            <input id="id-input" class="input-field" type="text" spellcheck="false" autocomplete="off" style="width: 45px">
        </div>
        <div class="input-field-div row">
            <div class="input-field-label">Заголовок:</div>
            <input id="title-input" class="input-field" type="text" spellcheck="false" autocomplete="off" placeholder="Заголовок статті">
        </div>
        <div class="input-field-div row">
            <div class="input-field-label">Теги:</div>
            <input id="tags-input" class="input-field" type="text" spellcheck="false" autocomplete="off" placeholder="Наприклад: #бісер, #ікони">
        </div>
        <div class="input-field-div row">
            <div class="input-field-label">Прев'ю:</div>
            <div class="text-area-bar row">
                <div id="preview-text-area-minus" class="text-area-button">-</div>
                <div id="preview-text-area-plus" class="text-area-button">+</div>
                <div id="preview-text-area-link" class="text-area-button">a</div>
            </div>
            <textarea id="preview-text-area" class="text-area" placeholder="Короткий опис з одним набором фотографій"></textarea>
        </div>
        <div class="input-field-div row">
            <div class="input-field-label">Текст статті:</div>
            <div class="text-area-bar row">
                <div id="content-text-area-minus" class="text-area-button">-</div>
                <div id="content-text-area-plus" class="text-area-button">+</div>
                <div id="content-text-area-link" class="text-area-button">a</div>
            </div>
            <textarea id="content-text-area" class="text-area" placeholder="Повний текст статті"></textarea>
        </div>
        <div style="text-align: center; float: left; width: 100%">
            <button id="save-button" class="save-button">Зберегти</button>
        </div>

        <div id="link-template" hidden>
            <a href="${Адреса посилання}">${Текст}</a>
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
            var topic =  this.$route.params.topic;
            var type =  this.$route.params.type;

            document.getElementById('workspace-bar-menu-title').innerText = 'Редагування - додати нову статтю';

            var idInput = document.getElementById('id-input');
            var topicInput = document.getElementById('topic-input');
            var titleInput = document.getElementById('title-input');
            var tagsInput = document.getElementById('tags-input');
            var previewTextArea = document.getElementById('preview-text-area');
            var contentTextArea = document.getElementById('content-text-area');

            if(type == 'change'){
                var id = document.location.href.split('=')[1];
                loadPost(topic, id, function(data){
                    topicInput.value = data.topic;
                    idInput.value = data.id;
                    titleInput.value = data.title;
                    tagsInput.value = data.tags;
                    previewTextArea.value = data.preview;
                    contentTextArea.value = data.content;
                })
            }
            else{
                loadState();
                idInput.value = parseInt(JSON.parse(sessionStorage.getItem('posts-state'))[topic]) + 1;
                idInput.setAttribute('readonly', '');

                topicInput.value = topic;
                topicInput.setAttribute('readonly', '');
            }

            document.getElementById('preview-text-area-minus').onclick = function(){
                previewTextArea.style.height = '120px';
            }
            document.getElementById('preview-text-area-plus').onclick = function(){
                previewTextArea.style.height = '300px';
            }
            document.getElementById('preview-text-area-link').onclick = function(){
                let l = previewTextArea.value.length;
                let ss = previewTextArea.selectionStart;
                previewTextArea.value = previewTextArea.value.substring(0, previewTextArea.selectionStart) +
                                        document.getElementById('link-template').innerHTML +
                                        previewTextArea.value.substring(previewTextArea.selectionStart, l);
                l = previewTextArea.value.length - l;
                previewTextArea.selectionStart = ss + l;
                previewTextArea.selectionEnd = ss + l;
                previewTextArea.focus();
            }

            document.getElementById('content-text-area-minus').onclick = function(){
                contentTextArea.style.height = '120px';
            }
            document.getElementById('content-text-area-plus').onclick = function(){
                contentTextArea.style.height = '300px';
            }
            document.getElementById('content-text-area-link').onclick = function(){
                let l = contentTextArea.value.length;
                let ss = contentTextArea.selectionStart;
                contentTextArea.value = contentTextArea.value.substring(0, contentTextArea.selectionStart) +
                    document.getElementById('link-template').innerHTML +
                    contentTextArea.value.substring(contentTextArea.selectionStart, l);
                l = contentTextArea.value.length - l;
                contentTextArea.selectionStart = ss + l;
                contentTextArea.selectionEnd = ss + l;
                contentTextArea.focus();
            }

            document.getElementById('save-button').onclick = function() {
                axios
                    .get('/adminbench/posts/' + type, {
                        params: {
                            'id' : idInput.value,
                            'topic' : topicInput.value,
                            'title' : titleInput.value,
                            'tags' : tagsInput.value,
                            'preview' : previewTextArea.value.replace(/\n/g, '').replace(/"/g, '\\"'),
                            'content' : contentTextArea.value.replace(/\n/g, '').replace(/"/g, '\\"')
                        }
                    })
                    .then(function(response){
                        console.log((response.data));
                        router.push('../' + topic);
                    });
            }
        }
    }
</script>
<style>
    .input-field-div{
        margin: 10px 0px 10px 0px;
        max-height: 30px;
        min-height: 30px;

        color: #193e19;
        font-size: 18px;
        line-height: 30px;
        text-align: right;
        font-weight: bold;
    }

    .input-field-label{
        float: left;
        min-width: 100px;
        margin: 0px 10px 0px 10px;

        text-align: right;
    }

    .input-field{
        float: left;
        background: #90ee90;
        height: 24px;
        width: 80%;
        padding: 3px 10px 3px 10px;
        border: 1px solid #226625;
        border-radius: 3px;
        outline: none;

        color: #193e19;
        font-size: 16px;
        line-height: 20px;
        text-align: left;
        font-weight: bold;
    }

    .text-area-bar{
        float: left;
        background: #4dc44d;
        height: 30px;
        width: 82%;
        margin: -25px 0% 0px 12%;
        border: 1px solid #226625;

        color: #193e19;
        font-size: 16px;
        font-weight: bold;
    }

    .text-area-button{
        float: right;
        min-width: 30px;
        min-height: 30px;
        border-left: 1px solid #193e19;
        cursor: pointer;

        text-align: center;
        font-size: 16px;
        line-height: 30px;
        font-weight: bold;
    }

    .text-area-button:hover{
        background: #226625;

        color: #4dc44d;
    }

    .text-area{
        float: left;
        background: #90ee90;
        height: 150px;
        width: 80%;
        margin: -3px 0% 0px 12%;
        padding: 3px 10px 3px 10px;
        border: 1px solid #226625;
        border-radius: 3px;
        border-top-left-radius: 0px;
        border-top-right-radius: 0px;
        outline: none;
        resize: none;

        color: #193e19;
        font-size: 16px;
        line-height: 20px;
        text-align: left;
        font-weight: bold;
    }

    .save-button{
        background: #4dc44d;
        width: fit-content;
        height: 35px;
        border: 1px solid #193e19;
        border-radius: 3px;
        margin: 15px 0px 10px 0px;


        color: #193e19;
        font-size: 18px;
        font-weight: bold;
        line-height: 35px;

    }
</style>