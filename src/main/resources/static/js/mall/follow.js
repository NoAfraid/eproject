// $(function () {
    var vm = new Vue({
        el: '#app',
        data: {
            current: 1,
            limit: 10,
            pages: 1,
            followList: [],
            total: 0,
            vip: [],
            user: {nick: ''},
            cart: {count: 0},
        },
        mounted: function () {
            var accessToken = getCookie("accessToken");
            if (isEmpty(accessToken)) {
                alert("请登录");
                window.location.href="login.html"
            } else {
                this.accessToken = accessToken;
            }
            this.findList(1);
        },
        create:{},
        methods: {
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
                    url: "http://localhost:8080/user/searchFollow",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            vm.current = result.data.currPage;
                            vm.pages = result.data.pageSize;
                            vm.total = result.data.totalCount;
                            vm.followList = result.data;
                            console.log(vm.pages)
                        } else {
                            alert(result.pages);
                        }
                    }
                });
            },
            cancelFollow: function (id) {
                var result = confirm("确认取消关注？");
                if (!result) return;
                var t = {
                    // followId: followId
                    id: id
                };
                var formData = JSON.stringify(t);
                var now = getNow("yyyyMMddHHmmss");
                var sign = signString(formData, now);
                $.ajax({
                    headers: {
                        client: client,
                        version: version,
                        requestTime: now,
                        sign: sign,
                        accessToken: this.accessToken
                    },
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
// })

