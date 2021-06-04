window.onload = function () {
    var articleUrl = "http://localhost:81/article/"


    var like = document.getElementById("like"); // 方式1 a标签.
    var collection2 = document.getElementById("collection2");
    like.onclick = function () {
        // alert("手动绑定click事件.");

        var liketext = document.getElementById("liketext");
        console.log(liketext.innerText)
        if (liketext.innerText==="点赞"){
            $.ajax({
                url: articleUrl+'like',
                type: 'GET',
                // dataType: 'jsonp',
                processData: false,
                // data:JSON.stringify(productCategoryList),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        alert("点赞成功");
                        liketext.innerText="已点赞";
                    } else {
                        alert("请先登录");
                    }
                }
            });
        }else{
            $.ajax({
                url: articleUrl+'unlike',
                type: 'GET',
                // dataType: 'jsonp',
                processData: false,
                // data:JSON.stringify(productCategoryList),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        alert("取消点赞");
                        liketext.innerText="点赞";
                    } else {
                        // alert("点赞失败");
                    }
                }
            });
        }
    }

    collection2.onclick = function () {
        // alert("手动绑定click事件.");

        var liketext = document.getElementById("collectiontext2");
        console.log(liketext.innerText)
        if (liketext.innerText==="收藏"){
            $.ajax({
                url: articleUrl+'collection',
                type: 'GET',
                // dataType: 'jsonp',
                processData: false,
                // data:JSON.stringify(productCategoryList),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        alert("收藏成功");
                        liketext.innerText="已收藏";
                    } else {
                        alert("请先登录");
                    }
                }
            });
        }else{
            $.ajax({
                url: articleUrl+'uncollection',
                type: 'GET',
                // dataType: 'jsonp',
                processData: false,
                // data:JSON.stringify(productCategoryList),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    if (data.success) {
                        alert("取消收藏");
                        liketext.innerText="收藏";
                    } else {
                        // alert("点赞失败");
                    }
                }
            });
        }





    }
}