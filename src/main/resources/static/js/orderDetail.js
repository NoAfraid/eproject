var id = getRequest();
var id = id['id']

var vm= new Vue({
    el: '#OrderDetail',
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        total: 0,
        orderList: [],
        msg : [],
        order: [],
    },
    mounted: function () {
        this.orderDetail();
    },
    methods:{

        /**
         * 根据订单id获取详情
         */
        orderDetail: function () {
            var t = {
                id: id
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url:  "http://localhost:8080/adminOrder/get?id=" + id,
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
            })
        },

        /**
         * 返回订单列表
         */
        returnOrders: function () {
            window.location.href = "admin_order.html"
        },

        /**
         * 修改收货人信息和发票信息
         */
        updateOrder: function () {
            var formData = JSON.stringify(this.orderList);
            console.log(this.orderList)
            $.ajax({
                type: "post",
                url:  "http://localhost:8080/adminOrder/updateAddress?id=" +id,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.msg);
                        return;
                    } else {
                        alert(result.msg);
                    }
                }
            })
        }
    }
})

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
}
