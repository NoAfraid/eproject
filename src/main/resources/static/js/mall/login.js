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
            src:'',
            error: null
        },

        mounted: function () {
            this.updateCode()
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
                var now =  getNow("yyyyMMddHHmmss");
                var sign  = signString(formData,now);
                $.ajax({
                    headers:{
                        client:client,
                        version:version,
                        requestTime:now,
                        sign:sign
                    },
                    type: "post",
                    url: loginUrl,
                    contentType: "application/json;charset=utf-8",
                    dataType : "json",
                    data: formData,
                    success: function (result) {
                        if(result.code == 0) {
                            setCookie("loginUser", JSON.stringify(result.data));
                            setCookie("accessToken", result.accessToken);
                            setCookie("sessionId",result.sessionId);
                            window.location.href= 'index.html';
                        } else {
                            alert(result.msg)
                        }
                    }
                })
            },


            /**
             * 验证码
             */
            updateCode: function () {
                this.src='http://localhost:8080/common/mall/kaptcha?d='+new Date()*1
            },
            /**
             * 注册
             */
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
                            alert(result.msg);
                            window.location.href= 'login.html';
                        } else {
                            alert(result.msg)
                        }
                    }
                })
            },
        }
    })
})