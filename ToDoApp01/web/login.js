/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function fieldChanged(){
    var userId = getField("user_id");
    var password = getField("password");
    var disabled = true;
    
    if (userId.value.length > 0 && password.value.length > 0) {
        disabled = false;
    }
    
    var login = getField("login");
    if (disabled) {
        login.setAttribute("disabled", "disabled");
    }
    else {
        login.removeAttribute("disabled");
    }
}

function getField(fieldName){
    var field = document.getElementById(fieldName);
    if (field == undefined) {
        throw new Error("隕∫ｴ�縺瑚ｦ九▽縺九ｊ縺ｾ縺帙ｓ: " + fieldName);
    }
    return field;
}