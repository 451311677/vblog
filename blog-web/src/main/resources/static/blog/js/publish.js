$(function(){
    $("#publishBtn").click(publish);
});

function publish() {

    var publishUrl="http://localhost:81/publish";
    // 已在action中调用
    // console.log($("#blog-title").val());
    // console.log($("#blog-content").val());
    // $.ajax({
    //     type: "POST",
    //     url: publishUrl,
    //     async: false,
    //     data: {
    //         title: $("#blog-title").val(),
    //         content: $("#blog-content").val()
    //     },
    //     dataType: "JSON",
    //     success: function(result) {
    //         console.log("发布成功");
    //     }
    // });


    // $.post(
    //     CONTEXT_PATH + "/discuss/add",
    //     {"content": content},
    //     // 处理服务端返回的数据
    //     function (data) {
    //         // String -> Json 对象
    //         data = $.parseJSON(data);
    //         // 在提示框 hintBody 显示服务端返回的消息
    //         $("#hintBody").text(data.msg);
    //         // 显示提示框
    //         $("#hintModal").modal("show");
    //         // 2s 后自动隐藏提示框
    //         setTimeout(function(){
    //             $("#hintModal").modal("hide");
    //             // 刷新页面
    //             if (data.code == 0) {
    //                 window.location.reload();
    //             }
    //         }, 2000);
    //
    //     }
    //
    // )

}