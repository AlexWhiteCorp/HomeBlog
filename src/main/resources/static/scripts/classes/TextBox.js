var TextBox = function(_el, _id){
    id = _id;
    el = _el;

    el.innerHTML += document.getElementById('textbox-template').innerHTML.replace(/#id/g, id);
    menu = document.getElementById(id + '-textbox-menu');
    box = document.getElementById(id + '-textbox-field');

    document.getElementById(id + '-minus-button').onclick = function(){
        box.style.minHeight = '175px';
    }
    document.getElementById(id + '-plus-button').onclick = function(){
        box.style.minHeight = '450px';

    }
    document.getElementById(id + '-triple-img-button').onclick = function(){
        addTemplate('triple-img', ['name1', 'name2', 'name3']);
        return false;
    }
    document.getElementById(id + '-double-img-button').onclick = function(){
        addTemplate('double-img', ['name1', 'name2']);
        return false;
    }
    document.getElementById(id + '-big-img-button').onclick = function(){
        addTemplate('big-img', ['name1']);
        return false;
    }
    document.getElementById(id + '-link-button').onclick = function(){
        addTemplate('link', ['', '', '']);
        return false;
    }
}

TextBox.prototype.addTemplate = function(type, names){
    let l = box.value.length;
    let ss = box.selectionStart;
    box.value = box.value.substring(0, box.selectionStart) +
        document.getElementById(type + '-template').innerHTML.replace(/#topic/g, document.getElementById('posts-crud-topic').value)
            .replace(/#id/g, document.getElementById('posts-crud-id').value)
            .replace(/#name1/g, names[0]).replace(/#name2/g, names[1]).replace(/#name3/g, names[2]) +
        box.value.substring(box.selectionStart, l);
    l = box.value.length - l;
    box.selectionStart = ss + l;
    box.selectionEnd = ss + l;
    box.focus();
}

TextBox.prototype.getValue = function(){
    return document.getElementById('test-textbox-field').value;
}