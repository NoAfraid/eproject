var id = getRequest();
var keyword = ((id['keyword']));
var categoryId = id['categoryId'];
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
    el: "#aaaaLLLL",
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        page:1,
        total: 0,
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
        keyword: null,
        allProduct: [],
        promotePrice: "promotePrice",
        sale: "sale",
        id: "id",
        categoryId: '',
        historySearchList: {},
        searchPageCategoryVO: {
            currentCategoryName:'',
            secondLevelCategoryName:'',
            thirdLevelCategoryParam:[],
        },
    },
    mounted: function () {
        this.CategorySearch();
        this.searchProduct();
        this.getUserInfo();
        this.getCartInfo();
        this.allProductList(1);
        this.HotSearchProduct();
        this.historySearch();
    },
    methods: {
        /**
         * 获取用户信息
         */
        getUserInfo: function () {
            this.vip = getCookie("loginUser");
            if (this.vip == null) {
                return;
            } else {
                var userId = getCookie("sessionId");
                if (userId == null || userId == '') {
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
            if (this.vip == null) {
                return;
            } else {
                var userId = getCookie("sessionId");
                if (userId == null || userId == '') {
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
        /**
         * 模糊查询商品
         */
        searchProduct: function () {
            var userId = getCookie("sessionId");
            // if (userId == null || userId == '') {
            //     return;
            // } else {
                this.productL.userId = userId;
            if (keyword == null || keyword == undefined) {
                return;
            } else {
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
            }

            // }
        },

        /**
         * 分类搜索
         */
        CategorySearch: function () {
            // var userId = getCookie("sessionId");
            // if (userId == null || userId ==''){
            //     return;
            // } else {
            //     this.productL.userId = userId
            // console.log(this.searchPageCategoryVO)
            if (categoryId == null ||  categoryId == undefined){
                return;
            }
            var t = {
                keyword: categoryId,
                categoryId: categoryId,
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/category/searchCategory",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.productL = result.data.list;
                        console.log(vm.keyword)
                        vm.searchPageCategoryVO = result.searchPageCategoryVO;
                    } else {
                        alert(result.msg)
                    }
                }
            })
            // }
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

        downSale: function () {
            function sortData(a, b) {
                return b.sale - a.sale;
            }

            this.productL.sort(sortData);
        },
        downByid: function () {
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
        allProductList: function (page) {
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/selectAllProduct",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.data.currPage;
                        vm.pages = result.data.pageSize;
                        vm.total = result.data.totalCount;
                        vm.allProduct = result.data.list;
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

        /**
         * 热搜商品
         */
        HotSearchProduct: function () {
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/getProductForHotSearch",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.hotProductList = result.data
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        /**
         * 历史搜索
         */
        historySearch: function () {
            var userId = getCookie("sessionId");
            if (userId ==null || userId == ''){
                return;
            }else {
                var t = {
                    id: userId
                };
                var formData = JSON.stringify(t);
                $.ajax({
                    type: "post",
                    url: "http://localhost:8080/index/historySearch",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            vm.historySearchList = result.data
                        } else {
                            // alert(result.msg)
                        }
                    }
                })
            }

        },


        /**
         * 模糊查询商品
         */
        searchProductList: function (productName) {

            // this.productL.productName = productName;
            window.location.href = encodeURI("search.html?keyword=" + (this.productL.productName));

        },

        searchProduc: function (productName) {
            this.productL.productName = productName;
            window.location.href = encodeURI("search.html?keyword=" + (this.productL.productName));

        },
    },
    computed: {
        indexss: function(){
            var left = 1;
            var right = this.pages;   //总的页数
            var ar = [];
            if(this.pages>= 5){
                if(this.current > 3 && this.current < this.pages-2){
                    left = this.current - 2;
                    right = this.current + 2
                }else{
                    if(this.current<=3){
                        left = 1;
                        right = 5
                    }else{
                        right = this.pages;
                        left = this.pages -4
                    }
                }
            }
            while (left <= right){
                ar.push(left);
                left ++
            }
            return ar
        }
    }
})