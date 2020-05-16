var vm = new Vue({
    el:"#center",
    data:{
        userInfo:{
            username:'',
            phone:'',
            sex:'',
            adress:'',
            nick:'',
            personalizedSignature:'',
        },
        current: 1,
        limit: 10,
        pages: 1,
        followList: [],
        total: 0,
        vip: [],
        user: {nick: ''},
        cart: {count: 0},
    },
    mounted: function () {
        this.vip = getCookie("loginUser");
        var accessToken = getCookie("accessToken");
        if (isEmpty(accessToken)) {
            alert("请登录");
            window.location.href="login.html"
        } else {
            this.accessToken = accessToken;
        }
        this.getUser();
        this.findList(1)
        this.getUserInfo();
        this.getCartInfo();
    },
    computed: {
        indexs: function () {
            var left = 1;
            var right = this.pages;   //总的页数
            var ar = [];
            if (this.pages >= 5) {
                if (this.current > 3 && this.current < this.pages - 2) {
                    left = this.current - 2;
                    right = this.current + 2
                } else {
                    if (this.current <= 3) {
                        left = 1;
                        right = 5
                    } else {
                        right = this.pages;
                        left = this.pages - 4
                    }
                }
            }
            while (left <= right) {
                ar.push(left);
                left++
            }
            return ar
        }
    },
    methods: {
        getUser:function () {
            var userId = getCookie("sessionId");
            var t = {
                id:userId
            };
            var formData = JSON.stringify(t);
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
                url: "http://localhost:8080/user/selectInfo",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.userInfo = result.data
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        updateUser:function () {
            var userId = getCookie("sessionId");
            var t = {
                id:userId
            }
            var formData = JSON.stringify(this.userInfo);
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
                url: "http://localhost:8080/user/updateUserInfo",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.userInfo = result.data;
                        alert(result.msg)
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        findList: function (page) {
            var userId = getCookie("sessionId");
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page,
                userId: userId
            };
            var formData = JSON.stringify(t);
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
                url: "http://localhost:8080/user/searchFollow",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.current;
                        vm.pages = result.pages;
                        vm.total = result.total;
                        vm.followList = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 获取用户信息
         */
        getUserInfo: function () {
            this.vip = getCookie("loginUser");
            if (this.vip == null){
                return;
            } else {
                var userId = getCookie("sessionId");

                if (userId == null || userId ==''){
                    return;
                } else {
                    var t = {
                        id: userId
                    };
                    var formData = JSON.stringify(t);
                    $.ajax({
                        type: "post",
                        url: "http://localhost:8080/user/selectInfo",
                        contentType: "application/json;charset=utf-8",
                        dataType : "json",
                        data: formData,
                        success: function (result) {
                            if(result.code == 0) {
                                vm.user = result.data
                                console.log(vm.user.nick)
                            } else {
                                alert(result.msg)
                            }
                        }
                    })
                }
            }


        },

        /**
         * 获取购物车信息
         */
        getCartInfo: function () {
            this.vip = getCookie("loginUser");
            if (this.vip == null){
                return;
            } else {
                var userId = getCookie("sessionId");
                if (userId == null || userId ==''){
                    return;
                } else {
                    var t = {
                        userId: userId
                    };
                    var formData = JSON.stringify(t);
                    $.ajax({
                        type: "post",
                        url: "http://localhost:8080/cart/count",
                        contentType: "application/json;charset=utf-8",
                        dataType: "json",
                        data: formData,
                        success: function (result) {
                            if (result.code == 0) {
                                vm.cart.count = result.data
                                console.log(vm.cart.count)
                            } else {
                                // alert(result.msg)
                            }
                        }
                    })
                }
            }

        },
    }
});



var id = getRequest();
var id = id['id']
function getRequest() {
    var url = location.search; //获取url中"?"符后的字串
    // alert(url)
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        // alert(strs)
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            // alert(theRequest)
        }
    }
    // alert(theRequest)
    return theRequest;

}