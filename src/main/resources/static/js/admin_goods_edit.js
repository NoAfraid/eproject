// var editor;
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
                productList:[]
            },

        },

        mounted: function () {
            KindEditor.ready(function(K){
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
                    allowFileManager : true,
                    allowImageUpload:true,//允许上传图片
                    // // resizeType: 1,
                    urlType: 'absolute',
                    // formatUploadUrl:false,
                    // // dir : "image",
                    // // responseType: "json",
                    // // fileManagerJson : '',
                    // // formatUploadUrl: false,
                    afterCreate : function() {
                        this.sync();
                    },
                    afterBlur:function(){
                        this.sync();
                    },
                    afterUpload : function(data) {

                    }
                })
            })
        },
        created: function() {},
        methods: {
            addProduct: function () {
                this.product.detail=  editor.html();
                console.log(this.product)
                // var product = this.$refs.editor.getContent()
                alert(this.product.detail)
                var formData = JSON.stringify(this.product);
                $.ajax({
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
            }
        }
    });

    // var  editor = KindEditor.create('#editor',{
    //     uploadJson: '/admin/upload/file',
    //     filePostName: 'file',
    //     data: {
    //         product:{
    //             detail: null
    //         }
    //     },
    //     mounted: function () {}
    // });
})