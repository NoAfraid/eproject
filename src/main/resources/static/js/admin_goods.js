$(function () {
    var goodsUrl = "http://localhost:8080/product/list"; //登录地址
    var vm = new Vue({
        el: '#goodsList',
        data: {
            current: 1,
            limit: 10,
            pages: 1,
            total: 0,
            goodsList: []
        },
        mounted: function () {
            // this.findList(1);
            // this.advice();
            this.productList(1,10)
        },
        methods:{
            productList: function (page,limit) {
                var t = {
                    limit: this.limit,
                    page: page == null ? this.current : page
                };
                var formData = JSON.stringify(t);
                $.ajax({
                    type: "post",
                    url:  goodsUrl,
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            vm.current = result.current;
                            vm.pages = result.pages;
                            vm.total = result.total;
                            vm.goodsList = result.data;
                            console.log(vm.list)
                        } else {
                            alert(result.msg);
                        }
                    }
                })
            },

            /**
             * 添加商品
             */
            addProduct: function () {
                function addGoods() {
                    window.location.href = "http://localhost:8080/product/add";
                }
            }
        }
    })
})