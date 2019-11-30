function loadState() {
    axios
        .get('/posts/state.json')
        .then(function(response){
            sessionStorage.setItem('posts-state', JSON.stringify(response.data));
        });
}

function loadPost(topic, id, todo) {
    axios
        .get('/post/' + topic + '/' + id + '.json')
        .then(function(response) {
           todo(response.data);
        });
}

function deletePost(topic, id, todo) {
    axios
        .get('/adminbench/posts/delete', {
            params: {
                'id' : id,
                'topic' : topic
            }
        })
        .then(function(response){
            todo();
        });
}

