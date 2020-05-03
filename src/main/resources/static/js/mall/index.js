var vm = new Vue({
    el: "#content",
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
            productName: "",
        },
        mark: 0,
        vip:[],
        user:{nick:''},
        cart:{count:0}
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
                        console.log(vm.user.nick)
                    } else {
                        alert(result.msg)
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
            // var t = {
            //     id:1
            // };
            // this. vip = getCookie("loginUser");
            // alert(this.vip)
            // var userId = this.vip.id;
            // alert(userId)
            // for (var i=0;i<this.vip.length;i++){
            //     userId = this.vip[3]
            //
            // }
            // alert(userId)
            // console.log(userId)
            // console.log(this.vip)
            // alert(this.vip)
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
                        vm.hotProductList = result.data
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

        /**
         * 模糊查询商品
         */
        searchProduct: function () {
            // var t = {
            //     this
            // };
            var formData = JSON.stringify(this.productL);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/product/update/search?page=1&limit=1",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.productL = result.data
                        console.log(vm.productL)
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

    }
});