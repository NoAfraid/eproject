var id = getRequest();
var parentId = id['parentId'];
var categoryLevel = id['categoryLevel'];
console.log(categoryLevel)
console.log(parentId)
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
    el: '#app',
    data: {
        current: 1,
        limit: 100,
        pages: 1,
        total: 0,
        category: {
            id:'',
            categoryLevel:'',
            parentId:'',
            backParentId:'',
            categoryName:'',
            categoryRank:'',
            creatTime:'',
        },
        parentId:'',
        msg:[],
    },
    mounted: function () {
        var accessToken = getCookie("accessToken");
        if (isEmpty(accessToken)) {
            alert("请登录");
            window.location.href = "login.html"
        } else {
            this.accessToken = accessToken;
        }
        this.categoryList(1);
        // this.updateProduct(17)
    },
    created: function () {
    },
    methods: {
        insertCategory: function () {
            if (this.category.categoryLevel == null || this.category.categoryLevel == undefined){
                this.category.categoryLevel = 1;
                this.category.parentId = 0
            }
            if (categoryLevel != null && parentId != null){
                this.category.categoryLevel = categoryLevel;
                this.category.parentId = parentId
            }
            var formData = JSON.stringify(this.category);
            var now = getNow("yyyyMMddHHmmss");
            var sign = signString(formData, now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url:  "http://localhost:8080/category/insert",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert("添加成功");
                        window.location.reload();
                    } else {
                        alert("添加出错");
                    }
                }
            })
        },

        /**
         * 分类列表
         */
        categoryList: function(page){
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page
            };
            var formData = JSON.stringify(t);
            var now = getNow("yyyyMMddHHmmss");
            var sign = signString(formData, now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url:  "http://localhost:8080/category/categories/list",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.category = result.data;
                        this.parentId = parentId;
                        console.log(this.parentId)
                    } else {
                        alert(result);
                    }
                }
            })
        },

        /**
         * 修改分类
         */
        updateCategory: function(){
            var i = 0 ;
            for(i;i< this.msg.length; i++){
                this.category.id = this.msg[i];
            }
            var formData = JSON.stringify(this.category);
            var now = getNow("yyyyMMddHHmmss");
            var sign = signString(formData, now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url:  "http://localhost:8080/category/update",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert("更新成功");
                        window.location.reload();
                    } else {
                        alert("更新出错");
                    }
                }
            })
        },

        /**
         * 下级分类管理
         */
        categoryManage:function(){
            if (this.msg == undefined || this.msg.length <= 0 || this.msg.length >1){
                alert("请选择一条记录");
                window.location.reload();
            }else {
                var i = 0;
                for (i; i < this.msg.length; i++) {
                    this.category.parentId = this.msg[i];
                }
                if (categoryLevel == undefined || categoryLevel == null){
                    window.location.href = 'categorys.html?categoryLevel=2' + '&parentId=' + this.category.parentId;
                } else if (categoryLevel == 2) {
                    window.location.href = 'categoryss.html?categoryLevel=3' + '&parentId=' + this.category.parentId;
                }

            }
        },
        /**
         * 返回上一级
         */
        categoryBack: function(){
            if (categoryLevel == 2 ){
                window.location.href = 'category.html'
            }
            if (categoryLevel == 3) {
                window.location.href = 'categorys.html?categoryLevel=2' + '&parentId=' + this.category.parentId;
            }
            else {
                swal("无上级分类", {
                    icon: "warning",
                });
            }
        },
        /**
         * 弹出模态框
         */
        returnModal: function () {
            $('.modal-title').html('分类');
            $('#categoryModal').modal('show');
        },

        ABCModal: function(){
            if (this.msg == undefined || this.msg.length <= 0 || this.msg.length >1){
                alert("请选择一条记录");
                window.location.reload();
            }else {
                var i = 0 ;
                for(i;i< this.msg.length; i++){
                    this.category.id = this.msg[i];
                }
                var formData = JSON.stringify(this.category);
                var now = getNow("yyyyMMddHHmmss");
                var sign = signString(formData, now);
                $.ajax({
                    headers:{
                        client:client,
                        version:version,
                        requestTime:now,
                        sign:sign
                    },
                    type: "post",
                    url:  "http://localhost:8080/category/selectCategoryById",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            vm.category = result.data;
                        } else {
                            alert(result);
                        }
                    }
                })
                $('.modal-title').html('分类修改');
                $('#updateCategory').modal('show');
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