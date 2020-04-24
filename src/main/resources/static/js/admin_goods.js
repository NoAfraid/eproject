var id = getRequest();
var id = id['id']
console.log(id)
// $(function () {
    var goodsUrl = "http://localhost:8080/product/list"; //登录地址
    var vm = new Vue({
        el: '#goodsList',
        data: {
            current: 1,
            limit: 10,
            pages: 1,
            total: 0,
            goodsList: [],
            msg : [],
            product: [],
        },
        mounted: function () {
            var accessToken = getCookie("accessToken");
            if (isEmpty(accessToken)) {
                alert("请登录");
                window.location.href="login.html"
            } else {
                this.accessToken = accessToken;
            }
            this.productList(1);
            // this.updateProduct(17)
        },
        created: function() {
        },
        methods:{
            productList: function (page) {
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
                    url:  goodsUrl,
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            vm.current = result.data.currPage;
                            vm.pages = result.data.pageSize;
                            vm.total = result.data.totalCount;
                            vm.goodsList = result.data;
                        } else {
                            alert(result.msg);
                        }
                    }
                })
            },

            /**
             * 添加商品
             */
            addProduct: function () {
                window.location.href ="goods_get.html" ;
            },
            /**
             * 修改商品
             */
            updateProduct: function () {
                if (this.msg == undefined || this.msg.length <= 0 || this.msg.length >1){
                    alert("请选择一条记录");
                    window.location.reload();
                } else {
                    window.location.href = "goods_edit.html?id="+ this.msg ;
                }
            },

            /**
             * 批量上下架
             */
            upProduct: function () {
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
                    traditional: true,
                    url:  "http://localhost:8080/product/update/publicStatus/1",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            alert("上架成功")
                            window.location.reload();
                        } else {
                            alert(result.msg);
                            window.location.reload();
                        }
                    }
                })
            },
            dnProduct: function () {
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
                    traditional: true,
                    url:  "http://localhost:8080/product/update/publicStatus/0",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            alert("下架成功")

                        } else {
                            alert(result.msg);
                            window.location.reload();
                        }
                    }
                })
            },

            /**
             * 批量上下架
             */
            putProduct: function () {
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
                    traditional: true,
                    url:  "http://localhost:8080/product/update/recomandStatus/1",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            alert("推荐成功")
                            window.location.reload();
                        } else {
                            alert(result.msg);
                            window.location.reload();
                        }
                    }
                })
            },
            downProduct: function () {
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
                    traditional: true,
                    url:  "http://localhost:8080/product/update/recomandStatus/0",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            alert("修改成功");
                            window.location.reload();
                        } else {
                            alert(result.msg);
                            window.location.reload();
                        }
                    }
                })
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
// })

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