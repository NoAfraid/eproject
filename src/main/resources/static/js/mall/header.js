// var vm = new Vue({
//     el: "#content",
//     data: {
//         vip:[],
//         user:{nick:''},
//         cart:{count:''},
//     },
//     mounted: function () {
//         // this.header();
//         this.getUserInfo();
//         this.getCartInfo();
//     },
//     methods:{
//         /**
//          * 获取用户信息
//          */
//         getUserInfo:function () {
//             this.vip = getCookie("loginUser");
//             var userId = getCookie("sessionId");
//             var t = {
//                 id:userId
//             };
//             var formData = JSON.stringify(t);
//             $.ajax({
//                 type: "post",
//                 url: "http://localhost:8080/user/selectInfo",
//                 contentType: "application/json;charset=utf-8",
//                 dataType : "json",
//                 data: formData,
//                 success: function (result) {
//                     if(result.code == 0) {
//                         vm.user = result.data
//                         console.log(vm.user.nick)
//                     } else {
//                         alert(result.msg)
//                     }
//                 }
//             })
//         },
//
//         /**
//          * 获取购物车信息
//          */
//         getCartInfo: function () {
//             this.vip = getCookie("loginUser");
//             var userId = getCookie("sessionId");
//             var t = {
//                 userId:userId
//             };
//             var formData = JSON.stringify(t);
//             $.ajax({
//                 type: "post",
//                 url: "http://localhost:8080/cart/count",
//                 contentType: "application/json;charset=utf-8",
//                 dataType : "json",
//                 data: formData,
//                 success: function (result) {
//                     if(result.code == 0) {
//                         vm.cart = result.data
//                         console.log(vm.cart.count)
//                     } else {
//                         alert(result.msg)
//                     }
//                 }
//             })
//         }
//     }
// })