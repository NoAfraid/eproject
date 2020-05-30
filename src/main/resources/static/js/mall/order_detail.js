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
            totalPrice: '',
            productName: '',
        },
        comment: {
            orderId: '',
            productId: '',
            userId: '',
            memberNickName:'',
            productName:'',
            pics: '',
            showStatus: '',
            content: '',
            star: ''
        },
        user: {nick: ''},
        cart: {count: 0},
        form: '',
        refundReason: '',
        sendDelayMessage: '',
        creatTime: '',
        isEnd: false,
        day: 0,
        hr: 0,
        min: 0,
        sec: 0
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
        // this.sendDelayMessageCancelOrder()
        // this.setEndTime()
        // this.payByzhifubao();
        // this.paySuccess();
        // this.getUserInfo();
        // this.getCartInfo();
        // this.countdown()
    },
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
            this.sendDelayMessageCancelOrder()
        },

        /**
         * 设置延时取消订单
         */
        sendDelayMessageCancelOrder: function () {
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/cancelTimeOrder?orderId=" + 244,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.sendDelayMessage = result.data;
                        vm.creatTime = result.creatTime;
                    } else {
                        alert(result.msg);
                    }
                }
            });
            this.countdown()
        },
        countdown: function () {
            // 定义结束时间戳
            this.sendDelayMessage = this.sendDelayMessage - 1000
            // console.log(this.creatTime)  //+ this.sendDelayMessage
            const end = Date.parse(new Date('2020-5-22 00:00:00'));
            // 定义当前时间戳
            const now = Date.parse(new Date())
            // console.log(now)
            // console.log(end)
            // // 做判断当倒计时结束时都为0
            // if (now >= end) {
            //     this.day = 0
            //     this.hr = 0
            //     this.min = 0
            //     this.sec = 0
            //     return
            // }
            // 用结束时间减去当前时间获得倒计时时间戳
            const msec = this.sendDelayMessage;
            let day = parseInt(msec / 1000 / 60 / 60 / 24) //算出天数
            let hr = parseInt(msec / 1000 / 60 / 60 % 24)//算出小时数
            let min = parseInt(msec / 1000 / 60 % 60)//算出分钟数
            let sec = parseInt(msec / 1000 % 60)//算出秒数
            //给数据赋值
            this.day = day
            this.hr = hr > 9 ? hr : '0' + hr
            this.min = min > 9 ? min : '0' + min
            this.sec = sec > 9 ? sec : '0' + sec
            //定义this指向
            const that = this
            // 使用定时器 然后使用递归 让每一次函数能调用自己达到倒计时效果
            setTimeout(function () {
                that.countdown()
            }, 1000)
        },

        /**ajax, jq vue js
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
            if (userId == null || userId == '') {
                alert("您未登录，请登录");
                window.location.href = "login.html";
            }
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
                        alert("已确认");
                        window.location.reload();
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
            if (userId == null || userId == '') {
                alert("您未登录，请登录");
                window.location.href = "login.html";
            }
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
                        window.location.reload();
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
            var userId = getCookie("sessionId");
            if (userId == null || userId == '') {
                alert("您未登录，请登录");
                window.location.href = "login.html";
            }
            if (orderNo == '') {
                alert("订单号为null！");
                return;
            } else {
                window.location.href = "pay-select.html?orderNo=" + orderNo
            }
        },
        payByzhifubao: function () {

            window.location.href = "http://localhost:8080/order/pay?orderNo=" + orderNo;
            // this.paySuccess();
        },
        refundModal: function () {
            $('#personalInfoModal').modal('show');
        },
        commentModal: function () {
            $('#personalInfoModal2').modal('show');
        },
        openImg: function () {
            $('#file').click();
        },
        preview: function () {
            this.comment.pics = '';
            var file = document.getElementById('file').files[0];
            var url = URL.createObjectURL(file);
            this.comment.pics = url;
        },
        CommentUpload: function () {
            if ($.trim($('#file').val()) === '') {
                alert('请选择图片');
                return;
            }
            // var file = $.trim($('#file').val());
            // var formData = JSON.stringify(file);
            var option = {
                type: "post",
                url: "http://localhost:8080/admin/upload/file",
                name: "file",
                // contentType: false,
                // contentType: "application/json;charset=utf-8",
                dataType: "json",
                // data: formData,
                beforeSubmit: function () {
                    //layer.load();
                    $('#progress').show();
                },
                success: function (result) {
                    // layer.closeAll('loading');
                    if (result != null && result.resultCode === 200) {
                        vm.comment.pics = result.data;
                        return false;
                    } else {
                        alert("error");
                    }
                }
            };
            $('#fileForm').ajaxSubmit(option);
        },
        saveComment: function (orderId, productId,productName) {
            this.comment.orderId = orderId;
            this.comment.productId = productId;
            this.comment.productName = productName,
            this.comment.userId = getCookie("sessionId");
            console.log(this.user.nick)
            this.comment.memberNickName = this.user.nick;
            var formData = JSON.stringify(this.comment);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/comment/add",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert("评价成功")
                        window.location.reload();
                    } else {
                        alert("评价失败")
                        window.location.reload();
                    }
                }
            });
        },
        refund: function (refundAmount) {
            var t = {
                orderNo: orderNo,
            }
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/alipayRefundOrder?orderNo=" + orderNo + "&refundReason=" + this.refundReason + "&refundAmount=" + refundAmount,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.pay = result.data;
                        console.log(vm.pay);
                        alert("您已退款成功！将为你跳转到订单详情页")
                        window.location.href = "order-detail.html?orderNo=" + orderNo
                        // alert(result.msg);
                    } else {
                        // alert(result.msg);
                    }
                }
            });
        },
        /**
         * 选择支付方式
         */
        payOrderType: function (payType) {
            var userId = getCookie("sessionId");
            if (userId == null || userId == '') {
                alert("您未登录，请登录");
                window.location.href = "login.html";
            }
            if (payType == 1) {
                this.payByzhifubao();
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
                payType: 1,
                totalPrice: this.pay.totalPrice,
                productName: this.pay.productName

            }
            console.log(t)
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
                        alert("您已支付成功！将为你跳转到订单详情页")
                        window.location.href = "order-detail.html?orderNo=" + orderNo
                        alert(result.msg);
                    } else {
                        alert(result.msg);
                    }
                }
            });

        }
    }
})