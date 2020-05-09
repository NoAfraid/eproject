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
            description: '',
            userId: '',
        },
        mark: 0,
        vip: [],
        user: {nick: ''},
        cart: {count: 0},
        keyword: '',
        allProduct: [],
        promotePrice: "promotePrice",
        sale: "sale",
        id: "id",
    },
    mounted: function () {
        this.searchProduct();
        this.getUserInfo();
        this.getCartInfo();
        this.aLlProductList();
    },
    methods: {
        /**
         * 获取用户信息
         */
        getUserInfo: function () {
            this.vip = getCookie("loginUser");
            var userId = getCookie("sessionId");
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
        /**
         * 前端排序
         */
        downchange: function () {
            //写法二
            function sortData(a, b) {
                return b.price - a.price;
            }

            this.productL.sort(sortData);
        },
        upchange: function () {
            function sortData(a, b) {
                return a.price - b.price;
            }

            this.productL.sort(sortData);
        },

        downSale:function(){
            function sortData(a, b) {
                return b.sale - a.sale;
            }
            this.productL.sort(sortData);
        },
        downByid:function(){
            function sortData(a, b) {
                return b.id - a.id;
            }
            this.productL.sort(sortData);
        },
        productD: function (id) {
            console.log(id)
            window.location.href = "product_detail.html?id=" + id;
        },

        /**
         * 所有商品
         */
        aLlProductList: function () {
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/selectAllProduct",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.allProduct = result.data;
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        /**
         * 后端动态排序
         */
        orderByNewProduct: function (orderField) {
            var t = {
                orderField: orderField,
                orderType: "desc"
            }
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/orderBy",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.allProduct = result.data;
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },
        orderByPrice: function (orderField) {
            var t = {
                orderField: orderField,
                orderType: "asc"
            }
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/orderBy",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.allProduct = result.data;
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },
    }
})