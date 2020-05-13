$(function () {
    // var loginUrl = "http://localhost:8080/manager/login"; //登录地址
    var vm = new Vue({
        el: '#login',
        data: {
           manager:{
               loginName: null,
               password: null,
           },
            error: null,
            src:'',
        },

        mounted: function () {
            this.updateCode()
        },
        computed: {},
        methods: {
            loginDo: function () {

                if ((this.manager.loginName) == null){
                //     alert(this.manager.loginName)
                    alert('请输入登录名/账号'); return;
                }
                if (!validPhoneNumber(this.manager.loginName)) {
                    alert("请输入正确的登录名(手机号)")
                }
                if ((this.manager.password) == null){
                    alert( '请输入密码'); return;
                }
                var verifyCode = $("#verifyCode").val();
                if (!validLength(verifyCode, 7)) {
                    alert("请输入正确的验证码")
                }

                var formData = JSON.stringify(this.manager);
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
                    url: "http://localhost:8080/manager/login",
                    contentType: "application/json;charset=utf-8",
                    dataType : "json",
                    data: formData,
                    success: function (result) {
                        if(result.code == 0) {
                            // alert("登陆成功");
                            setCookie("manager", JSON.stringify(result.data));
                            setCookie("accessToken", result.accessToken);
                            window.location.href= 'goods.html';
                            // vm.goBack();
                        } else {
                            vm.error = result.msg;
                        }
                    }
                })
            },

            /**
             * 验证码
             */
            updateCode: function () {
                this.src='http://localhost:8080/common/kaptcha?d='+new Date()*1
            }
        }
    })
})