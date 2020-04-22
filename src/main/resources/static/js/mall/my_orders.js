var vm = new Vue({
    el: '#app',
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        orderList: [],
        total: 0
    },
    mounted: function () {
        // var accessToken = getCookie("accessToken");
        // if (isEmpty(accessToken)) {
        // } else {
        //     this.accessToken = accessToken;
        // }
        this.findList(1);
    },
    create:{},
    methods: {
        findList: function (page) {
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page,
                userId: 3
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/order/getOrderInfo",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.data.currPage;
                        vm.pages = result.data.pageSize;
                        vm.total = result.data.totalCount;
                        vm.orderList = result.data;
                        console.log(vm.pages)
                    } else {
                        alert(result.pages);
                    }
                }
            });
        },

        dateFormat:function(time) {
            var date=new Date(time);
            var year=date.getFullYear();
            /* 在日期格式中，月份是从0开始的，因此要加0
             * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
             * */
            var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
            var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
            var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
            var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
            var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
            // 拼接
            return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
        },
        cancelFollow: function (id) {
            var result = confirm("确认取消关注？");
            if (!result) return;
            var t = {
                // followId: followId
                id: id
            };
            var formData = JSON.stringify(t);
            // var now = getNow("yyyyMMddHHmmss");
            // var sign = signString(formData, now);
            $.ajax({
                // headers: {
                //     client: client,
                //     version: version,
                //     requestTime: now,
                //     sign: sign,
                //     accessToken: this.accessToken
                // },
                type: "post",
                url: "http://localhost:8080/user/cancelFollow",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.findList();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    },
    computed: {
        indexs: function () {
            var left = 1;
            var right = this.pages;   //总的页数
            var ar = [];
            if (this.pages >= 5) {
                if (this.current > 3 && this.current < this.pages - 2) {
                    left = this.current - 2;
                    right = this.current + 2
                } else {
                    if (this.current <= 3) {
                        left = 1;
                        right = 5
                    } else {
                        right = this.pages;
                        left = this.pages - 4
                    }
                }
            }
            while (left <= right) {
                ar.push(left);
                left++
            }
            return ar
        }
    }
});