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
                KindEditor.create('textarea[id="editor"]',{
                    uploadJson: '/admin/upload/file',
                    filePostName: 'file',
                    allowFileManager : true,
                    themeType: 'simple',
                    resizeType: 1,
                    afterCreate : function() {
                        this.sync();
                    },
                    afterBlur:function(){
                        this.sync();
                    }
                })
            })

        },
        created: function() {},
        methods: {
            addProduct: function () {
                this.product.detail=  $("#editor").val();
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