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
        var accessToken = getCookie("accessToken");
        if (isEmpty(accessToken)) {
            alert("请登录");
            window.location.href="login.html"
        } else {
            this.accessToken = accessToken;
        }
        this.addressList();
    },
    create:{},
    methods:{
        addressList:function () {
            var userId = getCookie("sessionId");
            var t={
                userId: userId
            };
            var formData = JSON.stringify(t);
            var now =  getNow("yyyyMMddHHmmss");
            var sign  = signString(formData,now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
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

        saveOrder:function () {
            var userId = getCookie("sessionId");
            var t={
                userId:userId
            }
            var formData = JSON.stringify(t);
            var now =  getNow("yyyyMMddHHmmss");
            var sign  = signString(formData,now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
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