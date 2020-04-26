var vm = new Vue({
    el: '#app',
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        cartList:{
            productName:'',
            productImg:'',
            productCount:'',
            price:"",
            productAttr:'',
        },
        aList: [],
        updateAddressList:{
            //暂时赋值给userId
            userId:3,
            name:'',
            phoneNumber:'',
            province:'',
            city:'',
            region:'',
            detailAddress:''
        },
        total: 0,
        productCount:'',
        count:'',
        item:{
            itemsTotal:'',
            totalAmount:'',
        },
        order:{
            orderNo:''
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
        //先自启动
        this.findList(1)
        this.itemList(1)
        this.addressList(3)
    },
    create:{},
    methods: {

        /**
         * 购物车信息
         * @param page
         */
        findList: function (page) {
            var userId = getCookie("sessionId");
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page,
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
                url: "http://localhost:8080/cart/selectCartInfo",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.data.currPage;
                        vm.pages = result.data.pageSize;
                        vm.total = result.data.totalCount;
                        vm.cartList = result.data;
                    } else {
                        vm.cartList = result.data;
                        alert(vm.cartList)
                        alert(result.msg);
                    }
                }
            });
        },

        itemList: function (page) {
            var userId = getCookie("sessionId");
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page,
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
                url: "http://localhost:8080/cart/selectCartList",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.data.currPage;
                        vm.pages = result.data.pageSize;
                        vm.total = result.data.totalCount;
                        vm.item = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 更新购物车
         */
        updateItem: function (id, userId, productId, index) {
            var t = {
                id: id,
                productId: productId,
                userId: userId,
                productCount: this.cartList[index].productCount
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
                url: "http://localhost:8080/cart/updateQuantity",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.count = result.data;
                        console.log(vm.count);
                        window.location.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 删除购物车信息
         */
        deleteItem: function (id, userId, productId, index) {
            var t = {
                id: id,
                productId: productId,
                userId: userId,
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
                url: "http://localhost:8080/cart/deleteCartInfo?ids=" + id,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.count = result.data;
                        console.log(vm.count);
                        window.location.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 提交订单
         */
        settle: function () {
            window.location.href = 'order-settle.html'
        },

        /**
         *用户地址信息
         */
        addressList:function (id) {
            var t={
                userId:id
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
                url: "http://localhost:8080/address/addressList",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.aList = result.data;
                        console.log(vm.aList)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 弹出模态框
         */
        Rmodel: function(){
            $('#personalInfoModal').modal('show');
        },

        /**
         * 修改用户地址信息
         */
        updateAddress: function (id) {
            // var t={
            //     address: this.updateAddressList
            // }
            // console.log(t.address)
            // this.updateAddressList.userId = "${session.userId}"
            var formData = JSON.stringify(this.updateAddressList);
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
                url: "http://localhost:8080/address/update?id="+id,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.updateAddressList = result.data;
                        console.log(vm.updateAddressList)
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 提交订单
         * @param id
         * @constructor
         */
        // subOrder:function () {
        //     window.location.href = "order-detail.html";
        // }
        SuOrder:function (orderNo) {
            var userId = getCookie("sessionId");
            var t={
                userId:userId
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
                        window.location.href = "order-detail.html?orderNo="+vm.order.orderNo;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }

    }

})