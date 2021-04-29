$(function () {
    var emailUrl = 'http://localhost:8080/sendemail';

    $('.getcaptcha').on('click', 'a', function (e) {
        //获取邮箱
        var email = $("#email").val();
        //获取验证码
        // var captcha2 = document.getElementById("captcha").value;

        emailUrl = emailUrl + '?email=' + email;
        console.log(emailUrl);
        $.ajax({
            url: emailUrl,
            type: 'GET',
            // dataType: 'jsonp',
            processData: false,
            // data:JSON.stringify(productCategoryList),
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                if (data.success) {
                    console.log("获取验证码成功");
                    alert("获取验证码成功");
                } else {
                    console.log("获取验证码失败");
                    alert("获取验证码失败");
                }
            }
        });


    });
});