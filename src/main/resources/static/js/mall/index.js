var vm = new Vue({
    el:"#content",
    data:{
        productList:{
            productName:'',
            productImg:'',
            promotePrice:'',
            description:'',
        },
        hotProductList:[],
        carouselList:[],
        mark: 0,
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
         * 轮播图配置
         */
        carousel:function () {
            // var t = {
            //     id:1
            // };
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/getCarouseForIndex",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
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
        product:function () {
            // var t = {
            //     id:1
            // };
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/getProductForIndex",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
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
        hotProduct:function () {
            // var t = {
            //     id:1
            // };
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/index/getProductSaleForIndex",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.hotProductList = result.data
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        updateUser:function () {
            var t = {
                id:1
            }
            var formData = JSON.stringify(this.userInfo);
            $.ajax({
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
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page,
                userId: 3
            };
            var formData = JSON.stringify(t);
            $.ajax({
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
    }
});