new Vue({
    el:'#app',
    data:{
        userName:''
    },
    methods:{
        getLoginName:function () {
            let _this = this;
            axios.post('/login/loginName').then(function (response) {
                _this.userName = response.data.username;
                console.log(response.data.username);
            }).catch(function (reason) {
                console.log(reason);
            })
        }
    },
    created:function () {
        this.getLoginName();
    }
})