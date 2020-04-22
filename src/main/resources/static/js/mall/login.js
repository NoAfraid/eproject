$(function () {
    var loginUrl = "http://localhost:8080/user/loginUser"; //登录地址
    var registerUser = "http://localhost:8080/user/registerUser"; //登录地址
    var vm = new Vue({
        el: '#login',
        data: {
            user:{
                username: null,
                password: null
            },
            register:{
                username: null,
                password: null,
                phone: '',
            },
            error: null
        },

        mounted: function () {

        },
        computed: {},
        methods: {
            loginDo: function () {

                if ($.trim(this.user.username) == ''){
                    alert(this.user.username)
                    this.error = '请输入登录名/账号'; return;
                }
                if ($.trim(this.user.password) == ''){
                    this.error = '请输入密码'; return;
                }
                var formData = JSON.stringify(this.user);
                $.ajax({
                    type: "post",
                    url: loginUrl,
                    contentType: "application/json;charset=utf-8",
                    dataType : "json",
                    data: formData,
                    success: function (result) {
                        if(result.code == 0) {
                            alert(result.msg)
                        } else {
                            alert(result.msg)
                        }
                    }
                })
            },

            registerDo: function () {

                if ($.trim(this.register.username) == ''){
                    this.error = '请输入登录名/账号'; return;
                }
                if ($.trim(this.register.phone) == ''){
                    this.error = '请输入手机号'; return;
                }
                if ($.trim(this.register.password) == ''){
                    this.error = '请输入密码'; return;
                }
                var formData = JSON.stringify(this.register);
                $.ajax({
                    type: "post",
                    url: registerUser,
                    contentType: "application/json;charset=utf-8",
                    dataType : "json",
                    data: formData,
                    success: function (result) {
                        if(result.code == 0) {
                            alert(result.msg)
                        } else {
                            alert(result.msg)
                        }
                    }
                })
            },
        }
    })
})