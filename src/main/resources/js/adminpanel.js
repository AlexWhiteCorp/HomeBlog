
var Options = new Map();

var optionTemplate = document.getElementById('option-template');

var Explorer = function () {
    this.div = document.getElementById('explorer');
    this.body = document.getElementById('explorer-body');

    this.options = [];

    this.addOption = function(option){
        this.options[this.options.length] = option;
    }
};

var Option = function(parentNodesDiv, name, title){
    var option = this;
    this.name = name;

    parentNodesDiv.innerHTML += optionTemplate.innerHTML.replace(/#name/g, name).replace(/#title/g, title);

    this.div = document.getElementById(name + '-option-div');
    this.title = document.getElementById(name + '-option');
    this.nodesDiv = document.getElementById(name + '-option-nodes');

    this.nodes = [];

    this.isOpen = false;

    this.addNewNode = function(name, title){
        this.nodes[this.nodes.length] = new Option(this.nodesDiv, name, title);
    }

    Options.set(name + '-option', option);

    this.handler = function () {
        title = document.getElementById(option.name + '-option-title');
        if(!option.isOpen){
            option.isOpen = true;
            if(option.nodes.length != 0){
                option.nodesDiv.style.display = 'block';
                title.innerText = title.innerText.replace('▶', '▼');
                title.style.marginLeft = '-3px';
            }
            else{
                title.innerText = title.innerText.replace('▶', '');
                title.style.marginLeft = '19px';
            }
        }
        else{
            option.isOpen = false;
            if(option.nodes.length != 0){
                option.nodesDiv.style.display = 'none';
                title.innerText = title.innerText.replace('▼', '▶');
            }
            else{
                title.innerText = '▶ ' + title.innerText;
            }
            title.style.marginLeft = '0px';
        }
    }

}

var WorkSpace = function(title){
    this.title = title;
    var self = this;
    this.titleDiv = document.getElementById('workspace-bar-menu-title');
    this.buttonsDiv = document.getElementById('workspace-bar-menu-buttons');
    this.closeButton = document.getElementById('workspace-close-button');
    this.body = document.getElementById('workspace-body');

    this.titleDiv.innerText = title;
    this.body.style.minHeight = window.innerHeight - 130 + 'px';

    this.closeButton.onclick = close;

    this.setTitle = function(title){
        this.titleDiv.innerText = title;
    }

    this.load = function(repo){
        this.setTitle(repo.title);
        this.buttonsDiv.innerHTML = repo.buttonsDiv.innerHTML;
        this.body.innerHTML = repo.body.innerHTML;
    }

    function close(){
        self.setTitle(self.title);
        self.buttonsDiv.innerHTML = '';
        self.body.innerHTML = '';
    }

}

var PostCRUDRepo = function(title){
    this.title = title;
    this.buttonsDiv = document.getElementById('post-crud-repo-menu');
    this.body = document.getElementById('post-crud-repo-body');

    var buttonTamplate = document.getElementById('workspace-bar-menu-button-template');

    this.addButton = function(id, text, handler){
        this.buttonsDiv.innerHTML += buttonTamplate.innerHTML.replace(/#id/g, id).replace(/#text/g, text);
    }

}


document.body.style.minHeight = window.innerHeight - 50 + 'px';
document.getElementsByClassName('desktop-header-logo-div')[0].style.position = 'absolute';


