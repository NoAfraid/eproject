var id = getRequest();
var id = id['id']
console.log(id)
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

};

var vm = new Vue({
    el: '#detail',
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        detail: {
            number:1,
            productCount: 1,
            price:'',
        },
        total: 0,
        user:{nick:''},
        cart:{count:0},
        vip: [],
    },
    mounted: function () {
        // var accessToken = getCookie("accessToken");
        // if (isEmpty(accessToken)) {
        //     alert("请登录");
        //     window.location.href="login.html"
        // } else {
        //     this.accessToken = accessToken;
        // }
        this.productDetail();
        this.getUserInfo();
        this.getCartInfo();
    },
    create:{},
    methods: {

        /**
         * 获取用户信息
         */
        getUserInfo: function () {
            this.vip = getCookie("loginUser");
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
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            vm.user = result.data
                            console.log(vm.user.nick)
                        } else {
                            // alert(result.msg)
                        }
                    }
                })
            }

        },

        /**
         * 获取购物车信息
         */
        getCartInfo: function () {
            this.vip = getCookie("loginUser");
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

        },

        productDetail: function () {
            $.ajax({
                type: "post",
                url: "http://localhost:8080/product/product/detail/?id="+id,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        // window.location.href = "product_detail.html";
                        vm.detail = result.data;
                        vm.detail.productCount = 1;
                        // console.log(vm.detail)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 加入购物车
         */
        saveToCart: function (detail) {
            var userId = getCookie("sessionId");
            if (userId == null || userId =='') {
                alert("您未登录，请登录");
                window.location.href = "login.html";
            }
            if (this.detail.productCount == null || this.detail.productCount == 0
                || this.detail.productCount ==''){
                alert("请选择购买数量");
                return;
            }
            this.detail.price = this.detail.promotePrice;
            this.detail.userId = userId;
            this.detail = detail
            var formData = JSON.stringify(this.detail);
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
                url: "http://localhost:8080/cart/insert",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        // window.location.href = "product_detail.html";
                        alert(result.data)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 加入购物车
         */
        saveAndGoToCart: function (detail) {
            var userId = getCookie("sessionId");
            if (userId == null || userId =='') {
                alert("您未登录，请登录");
                window.location.href = "login.html";
            }
            if (this.detail.productCount == null || this.detail.productCount == 0
                || this.detail.productCount ==''){
                alert("请选择购买数量");
                return;
            }
            this.detail.price = this.detail.promotePrice;
            this.detail.userId = userId;
            this.detail = detail
            var formData = JSON.stringify(this.detail);
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
                url: "http://localhost:8080/cart/insert",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.data);
                        window.location.href = "cart.html";
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
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
});
