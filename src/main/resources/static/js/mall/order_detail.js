var id = getRequest();
var orderNo = id['orderNo'];
var payType = id['payType'];
console.log(payType)

function getRequest() {
    var url = location.search; //获取url中"?"符后的字串
    // alert(url)
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        // alert(strs)
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            // alert(theRequest)
        }
    }
    // alert(theRequest)
    return theRequest;

};

var vm = new Vue({
    el: "#app",
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        orderList: [],
        total: 0,
        totalPrice: '',
        productList: {
            productId: '',
            productName: '',
            productImg: '',
            price: '',
            productCount: '',
            userId: '',
        },
        addressList: {
            name: '',
            phoneNumber: '',
            province: '',
            city: '',
            detailAddress: '',
        },
        order: [],
        cartList: [{}],
        orderItemList: [],
        pay: {
            orderNo: '',
            orderStatus: '',
            totalPrice: ''
        },
        user:{nick:''},
        cart:{count:0}
    },
    mounted: function () {
        var accessToken = getCookie("accessToken");
        if (isEmpty(accessToken)) {
            alert("请登录");
            window.location.href = "login.html"
        } else {
            this.accessToken = accessToken;
        }
        this.getOrderInfo();
        // this.paySuccess();
        // this.getUserInfo();
        // this.getCartInfo();
    },
    create: {},
    methods: {

        // getUserInfo:function () {
        //     this.vip = getCookie("loginUser");
        //     var userId = getCookie("sessionId");
        //     var t = {
        //         id:userId
        //     };
        //     var formData = JSON.stringify(t);
        //     $.ajax({
        //         type: "post",
        //         url: "http://localhost:8080/user/selectInfo",
        //         contentType: "application/json;charset=utf-8",
        //         dataType : "json",
        //         data: formData,
        //         success: function (result) {
        //             if(result.code == 0) {
        //                 vm.user = result.data
        //                 console.log(vm.user.nick)
        //             } else {
        //                 alert(result.msg)
        //             }
        //         }
        //     })
        // },

        /**
         * 获取购物车信息
         */
        // getCartInfo: function () {
        //     this.vip = getCookie("loginUser");
        //     var userId = getCookie("sessionId");
        //     var t = {
        //         userId:userId
        //     };
        //     var formData = JSON.stringify(t);
        //     $.ajax({
        //         type: "post",
        //         url: "http://localhost:8080/cart/count",
        //         contentType: "application/json;charset=utf-8",
        //         dataType : "json",
        //         data: formData,
        //         success: function (result) {
        //             if(result.code == 0) {
        //                 vm.cart = result.data
        //                 console.log(vm.cart)
        //             } else {
        //                 alert(result.msg)
        //             }
        //         }
        //     })
        // },
        /**
         * 提交订单
         * @param id
         * @constructor
         */
        getOrderInfo: function () {
            this.vip = getCookie("loginUser");
            var userId = getCookie("sessionId");
            var t = {
                orderNo: orderNo
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/getOrderByOrderNo",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.order = result.data.result;
                        vm.cartList = result.data.orderItemList;
                        // alert(vm.order.orderNo)
                        console.log(vm.order)
                        console.log(vm.cartList)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 支付成功后修改库存
         */
        updateStock: function (productId, productQuantity) {
            var t = [
                 {
                    id: productId,
                    stock: productQuantity
                },
                {
                    id: productId,
                    stock: productQuantity
                },
            ]
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/product/update/stock",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.total = result.data;
                        window.location.href = "order-detail.html?orderNo=" + orderNo
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 确认订单
         */
        finishOrder: function (orderNo) {
            var userId = getCookie("sessionId");
            var t = {
                userId: userId,
                orderNo: orderNo
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/finishOrder",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        // vm.order = result.data.result;
                        // vm.cartList = result.data.orderItemList;
                        // // alert(vm.order.orderNo)
                        // console.log(vm.order)
                        // console.log(vm.cartList)
                        alert("已确认")
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 取消订单
         */
        cancelOrder: function (orderNo) {
            var userId = getCookie("sessionId");
            var t = {
                userId: userId,
                orderNo: orderNo
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/cancelOrder",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        // vm.order = result.data.result;
                        // vm.cartList = result.data.orderItemList;
                        // // alert(vm.order.orderNo)
                        // console.log(vm.order)
                        // console.log(vm.cartList)
                        alert("已取消")
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 去支付
         */
        payOrder: function (orderNo) {
            if (orderNo == '') {
                alert("订单号为null！")
            } else {
                window.location.href = "pay-select.html?orderNo=" + orderNo
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
            if (payType == 1) {
                window.location.href = "pay.html?orderNo=" + orderNo + "&& payType =" + payType
            }
            if (payType == 2) {
                window.location.href = "wxpay.html?orderNo=" + orderNo
            }
        },

        /**
         * 时间格式化
         * @param time
         * @returns {string}
         */
        dateFormat: function (time) {
            var date = new Date(time);
            var year = date.getFullYear();
            /* 在日期格式中，月份是从0开始的，因此要加0
             * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
             * */
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            // 拼接
            return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
        },

        /**
         * 支付成功
         */
        paySuccess: function () {
            var t = {
                orderNo: orderNo,
                payType: 1
            }
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/paySuccess",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.pay = result.data;
                        console.log(vm.pay);
                        window.location.href = "order-detail.html?orderNo=" + orderNo
                        // alert(result.msg);

                    } else {
                        alert(result.msg);
                    }
                }
            });

        }
    }
})