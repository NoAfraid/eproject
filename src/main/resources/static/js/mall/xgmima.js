// $(function () {
    var vm = new Vue({
        el: '#app',
        data: {
            // accessToken: null,
            newPassword: null,
            originalPassword: null,
            confirmPassword: null,
            showPassword: true
        },
        mounted: function () {
            // var accessToken = getCookie("accessToken");
            // if (isEmpty(accessToken)) {
            // } else {
            //     this.accessToken = accessToken;
            // }
        },
        computed: {},
        methods: {
            modify: function () {
                if($.trim(this.originalPassword) == '') {
                    alert('请输入原密码');return;
                }
                if($.trim(this.newPassword) == '') {
                    alert('请输入新密码');return;
                }
                if($.trim(this.confirmPassword) == '') {
                    alert('请输入确认密码');return;
                }
                if(this.newPassword != this.confirmPassword) {
                    alert('新密码和确认密码不一致');return;
                }
                var t = {
                    newPassword: this.newPassword,
                    originalPassword: this.originalPassword
                }
                var formData = JSON.stringify(t);
                $.ajax({
                    type: "post",
                    url: "http://localhost:8080/user/updatePassword/1",
                    contentType: "application/json;charset=utf-8",
                    data: formData,
                    success: function (r) {
                        if (r.code == 0) {
                            alert("密码修改成功")
                        } else if(r.code == -1){
                            alert(r.msg)
                        }
                    }
                });
            }
        }
    })
// });
