<template>
    <div class="post-crud-div">
        <div class="post-crud-bar-menu">
            <div class="bar-menu-title">{{title}}</div>
            <div id="bar-menu-close-button" class="bar-menu-button" @click="closeAll()">✖</div>
            <div id="bar-menu-delete-button" class="bar-menu-button">Видалити</div>
            <div id="bar-menu-change-button" class="bar-menu-button">Змінити</div>
            <div id="bar-menu-open-button" class="bar-menu-button">Відкрити</div>
            <div id="bar-menu-add-button" class="bar-menu-button">Додати</div>
            <div id="bar-menu-update-button" class="bar-menu-button">Оновити</div>
        </div>
        <div id="post-crud-workspace" class="post-crud-workspace">

        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return{
                'title' : document.location.href.split('/')[5]
            }
        },
        methods: {
            closeAll(){
                this.$router.push('/adminbench');
            }
        },
        mounted(){
            axios
                .get('/admin/posts/add', {
                    params: {
                        'id' : '4',
                        'topic' : 'plants',
                        'title' : 'Flowers',
                        'tags' : '#Flowers',
                        'preview' : 'Орхідея реагує на положення по відношенню до світла. Тому, якщо існує необхідність переставити горщик з рослиною,' +
                            '    необхідно розміщувати його тією ж стороною до джерела освітлення, так як і стояв раніше. Також не варто рухати орхідею' +
                            '    під час поливу. Пересування несприятливо позначається на рості квітки, особливо в момент появи квітконосу.',
                        'text' : 'post test'
                    }
                })
                .then(function(response){
                    alert(response.data.id + ' ' + response.data.text);
                });
        }
    }
</script>
<style>
    .post-crud-div{
        height: 100%;
    }

    .post-crud-bar-menu{
        background: #90ee90;
        min-height: 30px;
        max-height: 30px;
        border: 1px solid #193e19;

        font-size: 24px;
        line-height: 30px;
        color: #055f24;
        text-align: right;
    }

    .post-crud-workspace{
        height: 100%;
        border: 2px solid #226625;
        border-top: none;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
        padding-top: 5px;
    }

    .bar-menu-title{
        float: left;
        margin: 0px 10px 0px 10px;

        font-size: 17px;
    }

    .bar-menu-button{
        float: right;
        padding: 0px 7px 0px 7px;
        border-left: 1px solid #193e19;
        cursor: pointer;

        font-size: 14px;
    }
</style>