var vm = new Vue({
    el:"#app",
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        orderList: [],
        total: 0,
        totalPrice:'',
        productList:{
            productId:'',
            productName:'',
            productImg:'',
            price:'',
            productCount:'',
            userId:'',
        },
        addressList:{
            name:'',
            phoneNumber:'',
            province:'',
            city:'',
            detailAddress:'',
        }
    },
    mounted: function () {
        this.addressList(3);
    },
    create:{},
    methods:{
        addressList:function (id) {
            var t={
                userId:id
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/address/addressList",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.orderList = result.data;
                        console.log(vm.orderList)
                    } else {
                        alert(result.pages);
                    }
                }
            });
        },

        saveOrder:function (id) {
            var t={
                userId:id
            }
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/generateOrder",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.orderList = result.data;
                        console.log(vm.orderList)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
})