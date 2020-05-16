var vm = new Vue({
    el:'#app',
    data:{
        current: 1,
        limit: 10,
        pages: 1,
        total: 0,
        userList:[],
        msg : [],
    },
    mounted: function () {
        var accessToken = getCookie("accessToken");
        if (isEmpty(accessToken)) {
            alert("请登录");
            window.location.href="login.html"
        } else {
            this.accessToken = accessToken;
        }
        this.getUserList(1);
        // this.updateProduct(17)
    },
    created: function() {
    },
    methods: {
        getUserList: function (page) {
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
                url:  "http://localhost:8080/adminUser/users/list",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.data.currPage;
                        vm.pages = result.data.pageSize;
                        vm.total = result.data.totalCount;
                        vm.userList = result.data;
                        // console.log(vm.total)
                    } else {
                        alert(result.msg);
                    }
                }
            })
        },

        lockUser: function(disableStatus){
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
                url:  "http://localhost:8080/adminUser/users/lock/"+disableStatus,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert("操作成功")
                        window.location.reload();
                    } else {
                        alert("操作失败")
                        window.location.reload();
                    }
                }
            })
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
})