var vm = new Vue({
    el:"#detail",
    data:{
        commentList:{
            memberNickName:'',
            pics:[],
        },
        imgs:{
           off:' ../../upload/20200522_13521855.png',
            on:'../../upload/20200522_13521855.png',
        }
    },
    mounted: function () {
        this.getCommentList();
    },
    methods:{
        // getCommentList: function () {
        //     var userId = getCookie("sessionId");
        //     var formData = JSON.stringify();
        //     var now =  getNow("yyyyMMddHHmmss");
        //     var sign  = signString(formData,now);
        //     $.ajax({
        //         headers:{
        //             client:client,
        //             version:version,
        //             requestTime:now,
        //             sign:sign
        //         },
        //         type: "post",
        //         url: "http://localhost:8080/comment/commentList?userId="+ userId,
        //         contentType: "application/json;charset=utf-8",
        //         dataType: "json",
        //         data: formData,
        //         success: function (result) {
        //             if (result.code == 0) {
        //                 vm.commentList = result.data;
        //                 console.log(this.commentList)
        //             } else {
        //                 return;
        //             }
        //         }
        //     });
        //     // this.picsSplit()
        // },
        // /**
        //  * 截取字符串第一个和最后一个
        //  * @param memberNickName
        //  * @returns {string}
        //  */
        // substrNick:function(memberNickName){
        //     return  this.list.memberNickName = memberNickName.substring(0,1)
        // },
        // substrMemberNickName: function(memberNickName){
        //     return this.list.memberNickName = memberNickName.substr(vm.list.memberNickName.length-1,1)
        // },
        //
        // picsSplit: function(pics){
        //     console.log(pics)
        //     var pic =pics.split(",")
        //     console.log(pic)
        //     return pic
        //
        // },
        // /** 
        //  * 时间格式化
        //  * @param time
        //  * @returns {string}
        //  */
        // dateFormat: function (time) {
        //     var date = new Date(time);
        //     var year = date.getFullYear();
        //     /* 在日期格式中，月份是从0开始的，因此要加0
        //      * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
        //      * */
        //     var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        //     var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        //     var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        //     var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        //     var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        //     // 拼接
        //     return  month + "-" + day ;
        // },
    }
})