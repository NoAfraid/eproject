var id = getRequest("id");
$(function () {
    var editGoods = "http://localhost:8080/product/add"; //添加商品地址
    var vm = new Vue({
        el: '#editGoods',
        data: {
            product:{
                productName: '',
                productImg: '',
                productSn: '',
                detail: '',
                stock: '',
                price: '',
                promotePrice: '',
                description: '',
                unit: '',
                publishStatus: '',
            },
            // product:[]
        },

        mounted: function () {
            var accessToken = getCookie("accessToken");
            if (isEmpty(accessToken)) {
                alert("请登录");
                window.location.href="login.html"
            } else {
                this.accessToken = accessToken;
            }
            // KindEditor.ready(function(K){
                window.editor = KindEditor.create('textarea[id="editor"]',{
                    items: ['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'multiimage',
                        'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                        'anchor', 'link', 'unlink'],
                    uploadJson: '/admin/upload/file',
                    filePostName: 'file',
                    // allowFileManager : true,
                    allowImageUpload:true,//允许上传图片
                    resizeType: 1,
                    urlType: 'domain',
                    // formatUploadUrl:false,
                    // // dir : "image",
                    // // fileManagerJson : '',
                    // // formatUploadUrl: false,
                    afterCreate : function() {
                        this.sync();
                    },
                    afterBlur:function(){
                        this.sync();
                    },
                    // afterUpload : function(data) {
                    //
                    // }
                })
            // });
            // this.AjaxUpload()
            // this.productList()
        },
        created: function() {},
        methods: {
            addProduct: function () {
                this.product.detail=  editor.html();
                if ($.trim(this.product.productSn) == '') {
                    alert("请输入商品编号");
                    window.location.reload();
                }
                if ($.trim(this.product.productName) == '') {
                    alert("请输入商品名称");
                    window.location.reload();
                }
                if ($.trim(this.product.description) == '') {
                    alert("请输入商品简介");
                    window.location.reload();
                }
                if ($.trim(this.product.price) == '') {
                    alert("请输入商品价格");
                    window.location.reload();
                }
                if ($.trim(this.product.promotePrice) == '') {
                    alert("请输入商品销售价格");
                    window.location.reload();
                }
                if ($.trim(this.product.stock) == '') {
                    alert("请输入商品库存");
                    window.location.reload();
                }
                if ($.trim(this.product.unit) == '') {
                    alert("请输入商品单位");
                    window.location.reload();
                }
                if ($.trim(this.product.publishStatus) == '') {
                    alert("请选择是否上架");
                    window.location.reload();
                }
                if ($.trim(this.product.detail) == '') {
                    alert("请输入商品详情");
                    window.location.reload();
                }
                var formData = JSON.stringify(this.product);
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
                    url:  editGoods,
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.msg);
                        } else {
                            alert(result.msg);
                        }
                    }
                })
            },
            openImg: function(){
                $('#file').click();
            },
            preview: function() {
                this.productImg = '';
                var file = document.getElementById('file').files[0];
                var url = URL.createObjectURL(file);
                this.productImg = url;
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
                        $('#progress').show();
                    },
                    success: function (result) {
                        // layer.closeAll('loading');
                        if (result != null && result.resultCode === 200) {
                            vm.product.productImg = result.data;
                            return false;
                        } else {
                            alert("error");
                        }
                    }
                };
                $('#fileForm').ajaxSubmit(option);
            },
            productList: function () {
                var t = {
                    id: id
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
                    url:  "http://localhost:8080/product/get"+id,
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: formData,
                    success: function (result) {
                        if (result.code == 0) {
                            vm.current = result.current;
                            vm.pages = result.pages;
                            vm.total = result.total;
                            vm.product = result.data;
                            console.log(vm.product.detail)
                        } else {
                            alert(result.msg);
                        }
                    }
                })
            },

            /**
             * 返回商品列表
             */
            returnProduct: function () {
                window.location.href ="my_orders.html" ;
            },
        }
    });
});

function getRequest(id) {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return url;

}