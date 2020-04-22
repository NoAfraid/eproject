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
        },
        order:{
            orderNo:'',
            orderStatus:'',
            totalPrice:''

        },
        cartList:[],
        orderItemList:[],
    },
    mounted: function () {
        this.SuOrder();
    },
    create:{},
    methods:{
        SuOrder:function (id) {
            var t={
                userId:3
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
                        vm.order = result.data.order;
                        vm.cartList = result.data.orderItemList;
                        console.log(vm.order)
                        console.log(vm.cartList)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    }
})