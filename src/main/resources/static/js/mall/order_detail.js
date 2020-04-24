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
        pay:{
            orderNo:'',
            orderStatus:'',
            totalPrice:''
        },
    },
    mounted: function () {
        var accessToken = getCookie("accessToken");
        if (isEmpty(accessToken)) {
            alert("请登录");
            window.location.href="login.html"
        } else {
            this.accessToken = accessToken;
        }
        // this.SuOrder(3);
    },
    create:{},
    methods:{
        /**
         * 提交订单
         * @param id
         * @constructor
         */
        // SuOrder:function (id) {
        //     var userId = getCookie("sessionId");
        //     var t={
        //         userId:userId
        //     }
        //     var formData = JSON.stringify(t);
        //     $.ajax({
        //         type: "post",
        //         url: "http://localhost:8080/order/generateOrder",
        //         contentType: "application/json;charset=utf-8",
        //         dataType: "json",
        //         data: formData,
        //         success: function (result) {
        //             if (result.code == 0) {
        //                 vm.order = result.data.order;
        //                 vm.cartList = result.data.orderItemList;
        //                 console.log(vm.order)
        //                 console.log(vm.cartList)
        //             } else {
        //                 alert(result.msg);
        //             }
        //         }
        //     });
        // },

        /**
         * 去支付
         */
        payOrder: function (orderNo) {
            // alert(orderNo)
            if (orderNo == ''){
                alert("订单号为null！")
            }else {
                window.location.href = "pay-select.html"
                // var t={
                //     orderNo:orderNo
                // }
                // var formData = JSON.stringify(t);
                // $.ajax({
                //     type: "post",
                //     url: "http://localhost:8080/order/pay",
                //     contentType: "application/json;charset=utf-8",
                //     dataType: "json",
                //     data: formData,
                //     success: function (result) {
                //         if (result.code == 0) {
                //             vm.pay = result.data;
                //             console.log(vm.pay)
                //
                //             // vm.cartList = result.data;
                //         } else {
                //             alert(result.msg);
                //         }
                //     }
                // });
            }
        },

        /**
         * 选择支付方式
         */
        payOrderType: function (payType) {
            if (payType == 1){
                window.location.href = "pay.html"
            }
            if (payType == 2){
                window.location.href = "wxpay.html"
            }
        },

        /**
         * 支付成功
         */
        paySuccess: function () {
            alert("支付成功")
            window.location.href = "order-detail.html"
        }
    }
})