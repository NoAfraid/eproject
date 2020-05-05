var id = getRequest();
var keyword = ((id['keyword']));
function getRequest() {
    var url = decodeURI(location.search); //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        // alert(strs)
        for (var i = 0; i < strs.length; i++) {
            (theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]));
            // alert(theRequest)
        }
    }
    return theRequest;

};

var vm = new Vue({
    el: "#app",
    data: {
        productList: {
            productName: '',
            productImg: '',
            promotePrice: '',
            description: '',
        },
        hotProductList: [],
        carouselList: [],
        productL: {
            productName: '',
            description:'',
            userId:'',
        },
        mark: 0,
        vip:[],
        user:{nick:''},
        cart:{count:0},
        keyword:'',
    },
    mounted: function () {
        this.searchProduct();
        this.getUserInfo();
        this.getCartInfo();
    },
    methods: {
        /**
         * 获取用户信息
         */
        getUserInfo:function () {
            this.vip = getCookie("loginUser");
            var userId = getCookie("sessionId");
            var t = {
                id:userId
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
                    } else {
                        // alert(result.msg)
                    }
                }
            })
        },

        /**
         * 获取购物车信息
         */
        getCartInfo: function () {
            this.vip = getCookie("loginUser");
            var userId = getCookie("sessionId");
            var t = {
                userId:userId
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/cart/count",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.cart.count = result.data
                        console.log(vm.cart.count)
                    } else {
                        // alert(result.msg)
                    }
                }
            })
        },
        /**
         * 模糊查询商品
         */
        searchProduct: function () {
            var userId = getCookie("sessionId");
            this.productL.userId = userId
            this.keyword = keyword;
            this.productL.productName = keyword;
            var formData = JSON.stringify(this.productL);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/searchProductForIndex",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.productL = result.data;
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },
        productD: function (id) {
            // var userId = getCookie("sessionId");
            // var t = {
            //     limit: this.limit,
            //     page: page == null ? this.current : page,
            //     userId: userId
            // };
            // var formData = JSON.stringify(t);
            // var now =  getNow("yyyyMMddHHmmss");
            // var sign  = signString(formData,now);
            // $.ajax({
            // headers:{
            //     client:client,
            //     version:version,
            //     requestTime:now,
            //     sign:sign
            // },
            // type: "post",
            // url: "http://localhost:8080/product/product/detail/"+id,
            // contentType: "application/json;charset=utf-8",
            // dataType: "json",
            // // data: formData,
            // success: function (result) {
            //     if (result.code == 0) {
            console.log(id)
            window.location.href = "product_detail.html?id="+id;

            //     vm.detail = result.data;
            //     console.log(vm.detail)
            // } else {
            //     alert(result.msg);
            // }
            // }
            // });
        },
    }
})