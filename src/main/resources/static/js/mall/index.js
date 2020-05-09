var vm = new Vue({
    el: "#content",
    data: {
        productList: {
            productName: '',
            productImg: '',
            promotePrice: '',
            description: '',
        },
        saleProductList: [],
        carouselList: [],
        productL: {
            productName: "",

        },
        mark: 0,
        vip: [],
        user: {nick: ''},
        cart: {count: 0},
        hotProductList: {},
        historySearchList: {},
        recommendGoodses: [],
        hotGoodses: [],
        newGoodses: [],
    },
    mounted: function () {
        var swiper = new Swiper('.swiper-container', {
            spaceBetween: 30,
            // effect: 'fade',
            // observer:true,
            observer: true,
            observeParents: true,
            debugger: true,
            autoplay: {
                delay: 3000,
                disableOnInteraction: false, // 取消鼠标操作后的轮播暂停【无操作轮播继续】【参考链接1】
                // stopOnLastSlide: false, // 在最后一页停止
                //设置无限循环播放
                loop: true,
            },

            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },

        });
        this.carousel();
        this.product();
        this.hotProduct();
        this.getUserInfo();
        this.getCartInfo();
        this.Hotproduct();
        this.historySearch();
        this.productConfig()
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
                        console.log(vm.user.nick)
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
         * 轮播图配置
         */
        carousel: function () {
            // var t = {
            //     id:1
            // };
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/getCarouseForIndex",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.carouselList = result.data
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        /**
         * 新品商品配置
         */
        product: function () {
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/getProductForIndex",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.productList = result.data
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        /**
         * 热搜商品
         */
        Hotproduct: function () {
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
         * 新品商品配置
         */
        hotProduct: function () {
            // var t = {
            //     id:1
            // };
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/getProductSaleForIndex",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.saleProductList = result.data
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        /**
         * 热门推荐、新品上线、热销商品这三者合一
         */
        productConfig: function () {
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/indexPage",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.recommendGoodses = result.recommendGoodses;
                        vm.hotGoodses = result.hotGoodses;
                        vm.newGoodses = result.newGoodses;
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        /**
         * 跳转到商品详情页
         * @param id
         */
        productdetail: function (id) {
            window.location.href = "product_detail.html?id=" + id;
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

        /**
         * 历史搜索
         */
        historySearch: function () {
            var userId = getCookie("sessionId");
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
        },

        /**
         * 查找更多
         */
        lookMore: function(){
            window.location.href = "product_list.html"
        }
    }

});