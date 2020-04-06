$(function () {
    var loginUrl = "http://localhost:8080/manager/login"; //登录地址
    var vm = new Vue({
        el: '#login',
        data: {
           manager:{
               loginName: null,
               password: null,
           },
            error: null,

        },

        mounted: function () {

        },
        computed: {},
        methods: {
            loginDo: function () {

                // if ((this.manager.userName) == ''){
                    alert(this.manager.loginName)
                //     this.error = '请输入登录名/账号'; return;
                // }
                // if ((this.manager.password) == ''){
                //     this.error = '请输入密码'; return;
                // }
                alert(this.manager.password)
                var formData = JSON.stringify(this.manager);
                $.ajax({
                    type: "post",
                    url: loginUrl,
                    contentType: "application/json;charset=utf-8",
                    dataType : "json",
                    data: formData,
                    // success: function (result) {
                    //     if(result.code == 0) {
                    //         setCookie("manager", JSON.stringify(result.data));
                    //         setCookie("accessToken", result.accessToken);
                    //         //window.location.href= '/center.html';
                    //         vm.goBack();
                    //     } else {
                    //         vm.error = result.msg;
                    //     }
                    // }
                })
            }
        }
    })
})