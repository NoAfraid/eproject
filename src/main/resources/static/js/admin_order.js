var vm = new Vue({
    el: '#adminOrders',
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        total: 0,
        orderList: [],
        msg : [],
        order: [{
            deliveryCompany:"",
            orderId:"",
        }],
        num:{
            orderIds:[]
        },
        searchOrderList: {
            orderNo:'',
            userId:'',
            productId:'',
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
        this.ordersList(1);
        // this.updateProduct(17)
    },
    created: function() {
    },
    methods:{
        ordersList: function (page) {
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page
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
                url:  "http://localhost:8080/adminOrder/getList",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.data.currPage;
                        vm.pages = result.data.pageSize;
                        vm.total = result.data.totalCount;
                        vm.orderList = result.data;
                        // console.log(vm.orderList)
                    } else {
                        alert(result.msg);
                    }
                }
            })
        },

        /**
         * 查看详情
         */
        LookUserInfo: function () {
            window.location.href ="orderDetail.html" ;
        },
        /**
         * 添加商品
         */

        /**
         * 修改商品
         */
        updateOrder: function () {
            if (this.msg == undefined || this.msg.length <= 0 || this.msg.length >1){
                alert("请选择一条记录");
                window.location.reload();
            } else {
                window.location.href = "orderDetail.html?id="+ this.msg ;
            }
        },

        /**
         * 批量配货
         */
        ordersInfo: function () {
            var i = 0 ;
            for(i;i< this.msg.length; i++){
                var t = [{
                    orderId: this.msg,
                }]
            }
            // alert(t.orderId)
            if (this.msg.length <0){
                alert("请选择一条记录")
            }
            var formData = JSON.stringify(this.msg);
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
                url:  "http://localhost:8080/adminOrder/checkDone",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.msg)
                        console.log(t)
                        window.location.reload();
                    } else {
                        alert(result.msg);
                        window.location.reload();
                    }
                }
            })
        },

        /**
         * 弹出模态框
         */
        reModal: function(){
            $('#putDelivery').modal('show');
        },
        /**
         * 批量出库
         */

        delivery: function () {
            var i = 0 ;
            for(i;i< this.msg.length; i++){
                var t = [{
                    orderId: this.msg[i],
                    deliveryCompany:this.order.deliveryCompany,
                }]
            }
            // alert(t.orderId)
            if (this.msg.length <0){
                alert("请选择一条记录")
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
                url:  "http://localhost:8080/adminOrder/delivery",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.msg)
                        console.log(t)
                        // window.location.reload();
                    } else {
                        alert(result.msg);
                        window.location.reload();
                    }
                }
            })
            // $('#putDelivery').modal('show');
        },

        /**
         * 批量关闭订单
         */
        closeOrder: function () {
            var i = 0 ;
            for(i;i< this.msg.length; i++){
                var t = [{
                    orderId: this.msg,
                }]
            }
            // alert(t.orderId)
            if (this.msg.length <0){
                alert("请选择一条记录")
            }
            if (this.msg == -3){
                alert("该商品已关闭")
            }
            var formData = JSON.stringify(this.msg);
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
                url:  "http://localhost:8080/adminOrder/closeOrder",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.msg)
                        console.log(t)
                        window.location.reload();
                    } else {
                        alert(result.msg);
                        window.location.reload();
                    }
                }
            })
        },

        /**
         * 模糊查询订单（orderNo，userId，productId）
         */
        searchOrder: function () {
            var t = {
                orderNo:this.searchOrderList.orderNo,
                userId: this.searchOrderList.userId,
                productId:this.searchOrderList.productId,
                limit: this.limit,
                // page: page == null ? this.current : page
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/adminOrder/searchOrder",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.orderList = result.data
                        console.log(vm.orderList)
                    } else {
                        alert(result.msg)
                    }
                }
            })
        }
    },
    computed: {
        indexs: function(){
            var left = 1;
            var right = this.pages;   //总的页数
            var ar = [];
            if(this.pages>= 5){
                if(this.current > 3 && this.current < this.pages-2){
                    left = this.current - 2;
                    right = this.current + 2
                }else{
                    if(this.current<=3){
                        left = 1;
                        right = 5
                    }else{
                        right = this.pages;
                        left = this.pages -4
                    }
                }
            }
            while (left <= right){
                ar.push(left);
                left ++
            }
            return ar
        }
    }
});

var id = getRequest();
var id = id['id']
console.log(id)
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

// var vue = new Vue({
//     el:"#adminOrders",//
//     data: {
//         deliveryCompany:'',
//         id:'',
//     },
//     mounted: function () {
//         // this.findList(1);
//         // this.advice();
//         // this.updateProduct(17)
//     },
//     created: function() {
//     },
//     methods:{
//
//     }
//
// });