var id = getRequest();
var id = id['id']
console.log(id)
// $(function () {
// var carouselUrl = "http://localhost:8080/product/list"; //登录地址
var vm = new Vue({
    el: '#app',
    data: {
        current: 1,
        limit: 10,
        pages: 1,
        total: 0,
        goodsList: [],
        msg : [],
        product: [],
        carousel:{
            id:'',
            carouselUrl:'',
            redirectUrl:'',
            carouseName:'',
            carouseRank:'',
            managerId: null,
            productId: null,
            productImg:'',
        }
    },
    mounted: function () {
        // this.findList(1);
        // this.advice();
        this.carouselList(1);
        // this.updateProduct(17)
    },
    created: function() {
    },
    methods:{
        carouselList: function (page) {
            var t = {
                limit: this.limit,
                page: page == null ? this.current : page
            };
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url:  "http://localhost:8080/carouse/getCarouseList",
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
         * 弹出模态框
         */
        carouselModal: function(){
            $('#carouselModal').modal('show');
        },
        uodateCarousel: function(){
            $('#updateCarouselModal').modal('show');
        },

        /**
         * 图片处理器
         */
        openImg: function(){
            $('#file').click();
        },
        preview: function() {
            this.productImg = '';
            var file = document.getElementById('file').files[0];
            var url = URL.createObjectURL(file);
            this.carousel.productImg = url;
            this.carousel.carouselUrl = url;
        },
        AjaxUpload: function () {
            if($.trim($('#file').val()) === '') {
                alert('请选择图片');
                return;
            }
            var formData = JSON.stringify(this.product);
            var option = {
                type: "post",
                url:  "http://localhost:8080/admin/upload/file",
                name: "file",
                // contentType: false,
                // contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                beforeSubmit: function() {
                    //layer.load();
                    $('#carouselModal').show();
                },
                success: function (result) {
                    // layer.closeAll('loading');
                    if (result != null && result.resultCode === 200) {
                        vm.carousel.productImg = result.data;
                        return false;
                    } else {
                        alert("error");
                    }
                }
            };

            console.log(vm.carousel.productImg)
            $('#fileForm').ajaxSubmit(option);
        },

        /**
         * 时间格式化
         */
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

        /**
         * 添加轮播
         */
        carouselAdd: function () {
            var formData = JSON.stringify(this.carousel);
            $.ajax({
                type: "post",
                url:  "http://localhost:8080/carouse/add",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.msg);
                        window.location.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            })
        },
        /**
         * 修改轮播
         */
        updateCarousel: function () {
            if (this.msg == undefined || this.msg.length <= 0 || this.msg.length >1){
                alert("请选择一条记录");
                window.location.reload();
            } else {
                this.carousel.id = this.msg[0];
                var formData = JSON.stringify(this.carousel);
                $.ajax({
                    type: "post",
                    url:  "http://localhost:8080/carouse/updateCarouse",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.msg);
                            window.location.reload();
                        } else {
                            alert(result.msg);
                        }
                    }
                })
            }
        },

        /**
         * 批量删除轮播图
         */
        deleteCarousel: function () {
            var formData = JSON.stringify(this.msg);
            $.ajax({
                type: "post",
                traditional: true,
                url:  "http://localhost:8080/carouse/deleteCarouse",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert("删除成功");
                        window.location.reload();
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
        // putProduct: function () {
        //     var formData = JSON.stringify(this.msg);
        //     $.ajax({
        //         type: "post",
        //         traditional: true,
        //         url:  "http://localhost:8080/product/update/recomandStatus/1",
        //         contentType: "application/json;charset=utf-8",
        //         dataType: "json",
        //         data: formData,
        //         success: function (result) {
        //             if (result.code == 0) {
        //                 alert("推荐成功")
        //                 window.location.reload();
        //             } else {
        //                 alert(result.msg);
        //                 window.location.reload();
        //             }
        //         }
        //     })
        // },
        // downProduct: function () {
        //     var formData = JSON.stringify(this.msg);
        //     $.ajax({
        //         type: "post",
        //         traditional: true,
        //         url:  "http://localhost:8080/product/update/recomandStatus/0",
        //         contentType: "application/json;charset=utf-8",
        //         dataType: "json",
        //         data: formData,
        //         success: function (result) {
        //             if (result.code == 0) {
        //                 alert("修改成功");
        //                 window.location.reload();
        //             } else {
        //                 alert(result.msg);
        //                 window.location.reload();
        //             }
        //         }
        //     })
        // },
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