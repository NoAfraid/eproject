var vm = new Vue({
    el:"#center",
    data:{
        userInfo:{
            username:'',
            phone:'',
            sex:'',
            adress:'',
            nick:'',
            personalizedSignature:'',
        },
        current: 1,
        limit: 10,
        pages: 1,
        followList: [],
        total: 0
    },
    mounted: function () {
        this.getUser()
        this.findList(1)
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
    },
    methods: {
        getUser:function () {
            var t = {
                id:1
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/user/selectInfo",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.userInfo = result.data
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        updateUser:function () {
            var t = {
                id:1
            }
            var formData = JSON.stringify(this.userInfo);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/user/updateUserInfo",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.userInfo = result.data;
                        alert(result.msg)
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        findList: function (page) {
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page,
                userId: 3
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/user/searchFollow",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.current = result.current;
                        vm.pages = result.pages;
                        vm.total = result.total;
                        vm.followList = result.data;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
    }
});



var id = getRequest();
var id = id['id']
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