/*  menu style source: http://codepen.io/TimLamber/pen/FeEfn   */

var currentDiv = '';
var orderTotal = 0; 
var accTotal = 0;
var completedOrdersVal = 0;
var productList = [];
var productsBought = [];
var productTemp = {pname:"", pid:"", pprice:"0", pquantity:"0"};

productList.push(productTemp);


$(function(){

  $("#accordian h3").click(function(){
  $("#accordian ul ul").slideUp();
    if ($(this).next().is(":hidden")){
    $(this).next().slideDown();
    }
  
  
  });
  

});

/*sliding down orders menu*/

$(function()
{
     $("#orders").click(function()
     {
         $("#orderContent").slideToggle();
         return false;
     }); 
});


function toggleOrders(){
    if($("#orderContent").is(":hidden")){
        $("#orderContent").slideToggle();
        return false;
    }
};


function showDiv(string){
    
    $(function(){
        if(currentDiv.length > 0){
            closeKeyboard();
            closeHelp();
            $(currentDiv).hide();
            $(string).show();
            currentDiv = string;
            return;
        }else{
            closeKeyboard();
            closeHelp();
            $(string).show();
            currentDiv = string;
            return;
        }
    });
    
};

/*falta criar vetor global para colocar o item e a sua quantidade la,
  depois verificar na funçao se ja existe, caso exista, incrementar apenas a 
   quantidade e atualizar apenas no elemento do append com o respectivo id,
   a quantidade e o preco*/
function addItem(name, price, id){
    
    
    for(var i = 0; i<productList.length; i++){
        if(productList[i].pname === name){
            productList[i].pquantity = productList[i].pquantity + 1;
            productList[i].pprice = price * productList[i].pquantity;
            priceN = productList[i].pprice.toFixed(2);
            $("#"+id).html(productList[i].pquantity + "x  " + name + ' - '+priceN +"€ "+ "<button id='sumB' onclick=" + "addQuant(" + id + ',' + price + ");></button> "+"<button id='minusB' onclick=" + "minusQuant(" + id + ',' + price + ");></button> "+"<button id='cancelB' onclick=" + "removeItem(" + id + ',' + price + ");></button>");
            orderTotal = orderTotal + price;
            orderTotalN = orderTotal.toFixed(2);
            text = orderTotalN + "€";
            priceN = price.toFixed(2);
            $("#total").text(text);
            return;
    
        }
    }
    var product = {pname:name, pid:id, pprice:price, pquantity:1};
    productList.push(product);
    orderTotal = orderTotal + price;
    orderTotalN = orderTotal.toFixed(2);
    text = orderTotalN + "€";
    priceN = price.toFixed(2);
    $("#currentOrder").append("<p id=" +id+">" + product.pquantity + "x  " + name + ' - '+priceN +"€ "+ "<button id='sumB' onclick=" + "addQuant(" + id + ',' + price + ");></button> "+"<button id='minusB' onclick=" + "minusQuant(" + id + ',' + price + ");></button> "+"<button id='cancelB' onclick=" + "removeItem(" + id + ',' + price + ");></button>" + "</p>");
    $("#total").text(text);
    if(productList.length==2){
        toggleOrders();
    }
    
    
};

function removeItem(id, price){
    
    var idde = id.id;
    var totalDiscount = 0;
    
    for(var i = 0; i<productList.length; i++){
        if(productList[i].pid === idde ){
            totalDiscount = productList[i].pprice;
            productList.splice(i, 1);
            
        }
    }
    
    orderTotal = orderTotal - totalDiscount;
    orderTotalN = orderTotal.toFixed(2);
    text = orderTotalN + "€";
    idd = '#' + id;
    $(id).remove();
    $("#total").text(text);
};

function addQuant(id, price){
    var idde = id.id;
    idd = "#" + idde;
    
    
    for(var i = 0; i<productList.length; i++){
        if(productList[i].pid === idde){
            productList[i].pquantity = productList[i].pquantity + 1;
            productList[i].pprice = price * productList[i].pquantity;
            priceN = productList[i].pprice.toFixed(2);
            name = productList[i].pname;
            $(idd).html(productList[i].pquantity + "x  " + name + ' - '+priceN +"€ "+ "<button id='sumB' onclick=" + "addQuant(" + idde + ',' + price + ");></button> "+"<button id='minusB' onclick=" + "minusQuant(" + idde + ',' + price + ");></button> "+"<button id='cancelB' onclick=" + "removeItem(" + idde + ',' + price + ");></button>");
            orderTotal = orderTotal + price;
            orderTotalN = orderTotal.toFixed(2);
            text = orderTotalN + "€";
            priceN = price.toFixed(2);
            $("#total").text(text);
        }
    }
    
};

function minusQuant(id, price){
    var idde = id.id;
    idd = "#" + idde;
    
    
    for(var i = 0; i<productList.length; i++){
        if(productList[i].pid === idde){
            if(productList[i].pquantity == 1){
                removeItem(id, price);
                return;
            }else{
            productList[i].pquantity = productList[i].pquantity - 1;
            productList[i].pprice = price * productList[i].pquantity;
            priceN = productList[i].pprice.toFixed(2);
            name = productList[i].pname;
            $(idd).html(productList[i].pquantity + "x  " + name + ' - '+priceN +"€ "+ "<button id='sumB' onclick=" + "addQuant(" + idde + ',' + price + ");></button> "+"<button id='minusB' onclick=" + "minusQuant(" + idde + ',' + price + ");></button> "+"<button id='cancelB' onclick=" + "removeItem(" + idde + ',' + price + ");></button>");
            orderTotal = orderTotal - price;
            orderTotalN = orderTotal.toFixed(2);
            text = orderTotalN + "€";
            priceN = price.toFixed(2);
            $("#total").text(text);
        }
        }
    }
    
    
};




function clearOrder(){
    
  
    productList.length = 0;
    productList.push(productTemp);
    $("#currentOrder").empty();
    
    orderTotal = 0;
    text = "0.00€"
    $("#total").text(text);
    closeCancelConfirmation()
};


function addToAccTotal(){
    
    if(productList.length > 1){
    accTotal = accTotal + orderTotal;
    completedOrdersVal = completedOrdersVal + 1;
    addProductToClass();
    productsBought = productsBought.concat(productList);
    $("#completedOrders").append("<h3> Pedido nº" + completedOrdersVal + "</h3>");
    for(var i = 1; i<productList.length; i++){
        value = productList[i].pprice.toFixed(2);
        $("#completedOrders").append("<p>" + productList[i].pquantity + "x  " + productList[i].pname + ' - '+value +"€ "+ "</p>");
    }
    accTotalT = accTotal.toFixed(2);
    accTotalT = accTotalT + "€";
    $("#accTotalV").text(accTotalT);
    clearOrder();
    closeConfirmation();
    checkOrders();
}
};

function checkOrders(){
    
    showDiv("#completedOrders");
    
};

function showConfirmation(){
    if(productList.length > 1){
        $(".popupBackground").show();
        $("#cd-popup").show();
        return;
    }
}

function closeConfirmation(){
    $("#cd-popup").hide();
    $(".popupBackground").hide();
    return;
}

function showCancelConfirmation(){
    if(productList.length > 1){
        $(".popupBackground").show();
        $("#cancelpopup").show();
        return;
    }
}

function closeCancelConfirmation(){
    $("#cancelpopup").hide();
    $(".popupBackground").hide();
    return;
}


function showInfo(string){
    if(string === '#paymentpopup'){
        accTotalT = accTotal.toFixed(2);
        accTotalT = accTotalT + "€";
        $('#accTotalPopup').text(accTotalT);
    }
    $(".popupBackground").show();
    $(string).show();
    return;
}


function closeInfo(string){
    $(string).hide();
    $(".popupBackground").hide();
    return;
}

function showKeyboard(){
    $('#keyboardpopup').show();
}

function closeKeyboard(){
    $('#keyboardpopup').hide();
}


function reload_js(src) {
        $('script[src="' + src + '"]').remove();
        $('<script>').attr('src', src).appendTo('head');
    }



/* funçao para os comentarios */
var nrComment = 1;
function submit(){

var commentId = "comment" + nrComment;
nrComment = nrComment + 1;
var name = "João";
var userText = document.getElementById("userText").value
var myDiv = document.getElementById("myDiv")


 var    h1 = document.createElement("h1"),
		p = document.createElement("p"),
        br = document.createElement("br"),
    d = new Date();
  
    var dd = d.getDate();
    var mm = d.getMonth()+1; //January is 0!

    var yyyy = d.getFullYear();
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
    var today = dd+'/'+mm+'/'+yyyy;
    
    barClass = $('#commentBarClassi').rateit('value');
    
    if(barClass==0){
        closeInfo('#confirmCommentPopup');
        showInfo('#errorPopupComments');
        return;
    }
    
    ratingDiv = " <div id='commentBarClassi' class='rateit' data-rateit-value=" + barClass + " data-rateit-ispreset='true' data-rateit-readonly='true'></div>";
    
    h1.className = commentId;
    h1.innerHTML = name + " - " + today + ratingDiv + " <button class='removeCommentBtn' onclick=" + "removeComment('" + commentId + "');>Remover Comentário</button>";
    p.className = commentId;
    p.innerHTML = userText;
    br.className = commentId;
    
    
    document.getElementById("myDiv").insertBefore(p, document.getElementById("myDiv").childNodes[0]);
    document.getElementById("myDiv").insertBefore(h1, document.getElementById("myDiv").childNodes[0]);
    document.getElementById("myDiv").insertBefore(br, document.getElementById("myDiv").childNodes[0]);
    $('#commentBarClassi').rateit('value', 0);
    reload_js('js/jquery.rateit.min.js');
    closeInfo('#keyboardpopup');
    closeInfo('#confirmCommentPopup');
    showInfo('#tyPopupComments');
    
    $('#userText').val('');

};

function removeComment(string){
    $("." + string).remove();
};
/*--------------------------------------------------*/

/* adicionar os items para classificar */

var productsToClassification = [];
var nrProductsAdded = 0;
var nextGroupId = 100;
var nextClassiPos = 15;

function addProductToClass(){
    var flag = 0;
    
    for (var i = 0; i < productList.length; i++){
        for(var j = 0; j<productsBought.length; j++){
            if(productList[i].pid === productsBought[j].pid){
                flag = 1;
            }
        }
        if(flag == 0){
            productsToClassification.push(productList[i].pid);
            nrProductsAdded = nrProductsAdded + 1;
            
        }else{
            flag = 0;
        }
    }
    lala();
    nrProductsAdded = 0;
};

function lala(){
    if(productsToClassification.length == nrProductsAdded){
        $('#classiConsumiveisInfo').remove();
        for (i = productsToClassification.length - nrProductsAdded+1; i < productsToClassification.length; i++){
            
            $("#classiConsumiveis").append("<img src=img/" + productsToClassification[i] + ".png style='width:20%; height:20%';>"+createClassificationDiv(productsToClassification[i]) + getProductClassi(productsToClassification[i]) + "<br> ");
        }
    }else{
        for (i = productsToClassification.length - nrProductsAdded; i < productsToClassification.length; i++){
            $("#classiConsumiveis").append("<img src=img/" + productsToClassification[i] + ".png style='width:20%; height:20%';>"+createClassificationDiv(productsToClassification[i]) +
                                          getProductClassi(productsToClassification[i]) + "<br> ");
            
        }
    }
    
    reload_js('js/jquery.rateit.min.js');

}

function createClassificationDiv(string){
        id = string + "userClassi";
        gname = "group-" + nextGroupId;
        position = nextClassiPos + "%";
        nextClassiPos = nextClassiPos + 20.5;
        nextGroupId = nextGroupId + 1;
        estrelasClass = "rateit";
        var divclassi = "<div id=" + id +" style='position:absolute; top:" +position+"'" + " class=" + estrelasClass +" data-rateit-step='1' onclick="+"calcRating('"+string+"');" +">" + " Sua  Classificação  - </div>";
        return divclassi;
}

function getProductClassi(string){
    position = nextClassiPos-10 + "%";
    productClass = $('#'+string+"class").rateit('value');
    var productclassi = "<div id=" + string +"classlala" + " style='position:absolute; top:" +position+"; left:22.5%'" + " class='rateit' data-rateit-value="+ productClass + " data-rateit-ispreset='true' data-rateit-readonly='true'>  Classificação Atual - </div>";
    return productclassi;
}


var amostraPessoas = 2;

function calcRating(string){
    test();
    userRating = $('#' + string + "userClassi").rateit('value');
    productRating = $('#' + string + "class").rateit('value');
    console.log("" + productRating);
    newRating = ((productRating * amostraPessoas)+userRating)/(amostraPessoas+1);
    newRating = Math.round(newRating);
    console.log("" + newRating);
    $('#' + string + "class").rateit('value', newRating);
    $('#' + string + "classlala").rateit('value', newRating);
    $('#' + string + "classz").rateit('value', newRating);
    $('#' + string + "classp").rateit('value', newRating);
    $('#' + string + "classpi").rateit('value', newRating);
    updatedV = $('#' + string + "class").rateit('value');
    console.log(""+updatedV);
};

function showHelp(){
    test();
    if(currentDiv === '#maintenancePage' || currentDiv === '#eventosdobar' || currentDiv === '#topClassificacoes' || currentDiv === '#classiConsumiveis' || currentDiv === '#empregado' || currentDiv === '#completedOrders'){
        imgSrc = "img/undefinedhelp.png";
        document.getElementById("helpImage").src=imgSrc;
        $(".popupBackground").show();
        $("#helpPopup").show();
        return;
    }
    var imgName = currentDiv.split('#');
    imgSrc = "img/" + imgName[1] + "help.png";
    console.log(imgSrc);
    document.getElementById("helpImage").src=imgSrc;
    $(".popupBackground").show();
    $("#helpPopup").show();
}

function closeHelp(){
    $("#helpPopup").hide();
    $(".popupBackground").hide();
}


function test(){
    console.log("test");
}