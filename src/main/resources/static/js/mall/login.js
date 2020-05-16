$(function () {
    var loginUrl = "http://localhost:8080/user/login?verifyCode="+this.verifyCode; //登录地址
    var registerUser = "http://localhost:8080/user/registerUser"; //注册地址
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
            error: null,
            verifyCode: '',
        },

        mounted: function () {
            this.updateCode()
        },
        computed: {},
        methods: {
            loginDo: function () {
                if ($.trim(this.user.username) == ''){
                    // alert(this.user.username)
                    this.error = '请输入登录名/账号';
                    alert(this.error);
                    return;
                }
                if ($.trim(this.user.password) == ''){
                    this.error = '请输入密码';
                    alert(this.error);
                    return;
                }
                var verifyCode = $("#verifyCode").val();


                if (!validLength(this.verifyCode,7)) {
                    alert("请输入正确的验证码"); return;
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
                    url: "http://localhost:8080/user/login?verifyCode="+ this.verifyCode,
                    contentType: "application/json;charset=utf-8",
                    dataType : "json",
                    data: formData,
                    success: function (result) {
                        if(result.code == 0) {
                            setCookie("loginUser", JSON.stringify(result.data));
                            setCookie("accessToken", result.accessToken);
                            setCookie("sessionId",result.sessionId);
                            alert("登录成功");
                            window.location.href= 'index.html';
                        }
                        if (result.code==-1){
                            alert("验证码不能为空");
                            window.location.reload();
                        }
                        if (result.code==-3){
                            alert("验证码错误");
                            window.location.reload();
                        }
                        if (result.code==-4){
                            alert("账号或者密码错误");
                            window.location.reload();
                        }
                        if (result.code == -2) {
                            alert(result.msg);
                            window.location.href= 'register.html';
                        }
                        // else {
                        //     alert(result.msg);
                        // }
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