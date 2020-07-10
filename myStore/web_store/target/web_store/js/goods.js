new Vue({
    el:"#app",
    data:{
        categoryList1:[],//分类1数据列表
        categoryList2:[],//分类2数据列表
        categoryList3:[],//分类3数据列表
        grade:1,  //记录当前级别
        catSelect1:-1,   //分类1选择的id
        catSelect2:-1,   //分类2选择的id
        catSelect3:-1,   //分类3选择的id
        typeId:0,
        brandList:[],  /*品牌列表*/
        selBrand:-1,   /*当前选中的品牌*/
        imgEntitys:{
            color:'',
            url:'',
        },
        objEntitys:[],
        specList:[],   /*规格列表*/
        specListSel:[],    /*规格选项是否选中*/
        rowList:[],
        goodsEntity:{
            goods:{},
            goodsDesc:{},
            itemList:{}
        },    //最终保存商品的实体
        isEnableSpec:1 //是否启用规格

    },
    methods:{
        loadCateData: function (id) {
            let _this = this;
            axios.post("/itemCat/findByParentId?parentId="+id).then(function (response) {
                    if (_this.grade == 1){
                        //取服务端响应的结果
                        _this.categoryList1 = response.data;
                        console.log(response.data);
                    }
                    if (_this.grade == 2){
                        //取服务端响应的结果
                        _this.categoryList2 =response.data;
                    }
                    if (_this.grade == 3){
                        //取服务端响应的结果
                        _this.categoryList3 =response.data;
                    }
                }).catch(function (reason) {
                console.log(reason);
            })
        },
        getCatSelect:function (grade) {

            if (grade == 1){
                this.categoryList2 = [];
                this.catSelect2 = -1;
                this.categoryList3 = [];
                this.catSelect3 = -1;
                this.brandList = [];
                this.selBrand = -1;
                this.grade = grade + 1;  /*变为2*/
                this.loadCateData(this.catSelect1);
            }
            if (grade == 2){
                this.categoryList3 = [];
                this.catSelect3 = -1;
                this.brandList = [];
                this.selBrand = -1;
                this.grade = grade + 1;  /*变为2*/
                this.loadCateData(this.catSelect2);
            }
            if (grade == 3){
                //处理模板
                let _this = this;
                axios.post("/itemCat/findOne?id="+this.catSelect3).then(function (response) {
                    _this.typeId = response.data.typeId;
                    console.log(response.data.typeId);

                }).catch(function (reason) {
                    console.log(reason);
                })
            }
        },

        /*上传图片*/
        uploadFile:function () {
            let _this = this;
            let formData = new FormData();
            formData.append('file', file.files[0])
            const instance=axios.create({
                withCredentials: true
            });
            instance.post('/upload/uploadFile', formData).then(function (response) {
                _this.imgEntitys.url = response.data.msg;
                console.log(response.data);
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        /*保存图片到列表*/
        saveImage:function () {
            if (this.imgEntitys.color == '' || this.imgEntitys.url == ''){
                alert("请输入颜色或上传图片");
                return;
            }
            let obj = {
                color:this.imgEntitys.color,
                url:this.imgEntitys.url,
            }
            this.objEntitys.push(obj);
            this.imgEntitys.color = '';
            this.imgEntitys.url = '';
        },
        /*删除图片*/
        deleteImage:function (url,index) {
            let _this = this;
            axios.get('/upload/deleteImage?url='+url).then(function (response) {
                if (response.data.success){
                    alert(response.data.msg)
                    _this.objEntitys.splice(index,1);  /*移除*/
                }else {
                    alert(response.data.msg)
                }
            }).catch(function (reason) {
                console.log(reason);
            })
        },

        searchObject:function(list,key,value){
            for (let i = 0;i < list.length; i++){
                if (list[i][key] == value){
                    return list[i];
                }
            }
            return null;
        },

        /*规格选项*/
        updateSpec:function (event,specName,specOptionName) {
            let obj = this.searchObject(this.specListSel,"specName",specName);
            if (obj != null){
                if (event.target.checked){
                    obj.specOptionName.push(specOptionName)
                }else {
                    let number = obj.specOptionName.indexOf(specOptionName);
                    obj.specOptionName.splice(number,1);
                    if (obj.specOptionName.length == 0){
                        let idx = this.specListSel.indexOf(obj);
                        this.specListSel.splice(idx,1)
                    }
                }
                console.log("存在")
            }else {
                this.specListSel.push({"specName":specName,"specOptionName":[specOptionName]})
            }
            console.log(this.specListSel);
            this.createRowList();
        },
        createRowList:function () {
            let rowList = [
                {spec:{},price:0,num:9999,status:'0',isDefault:'0'}
            ];
            for(let i = 0; i< this.specListSel.length; i++ ){
                let specObj = this.specListSel[i];
                let specName = specObj.specName; //选择版本
                let specOptions = specObj.specOptionName; //["6G+64G","8G+128G"]
                let newRowList = [];
                for(let j=0; j<rowList.length; j++){
                    let oldRow = rowList[j]; //{spec:{选择颜色:星云紫},price:0,num:9999,status:'0',isDefault:'0'}
                    for(let k=0; k<specOptions.length; k++ ){
                        let newRow = JSON.parse(JSON.stringify(oldRow));
                        //{spec:{选择颜色:星云紫,选择版本:8G+128G},price:0,num:9999,status:'0',isDefault:'0'}
                        newRow.spec[specName] = specOptions[k];
                        newRowList.push(newRow);
                    }
                }
                rowList = newRowList;
            }
            this.rowList = rowList;
            console.log(rowList);
        },

        saveGoods:function () {//保存商品

            this.goodsEntity.goods.category1Id = this.catSelect1;
            this.goodsEntity.goods.category2Id = this.catSelect2;
            this.goodsEntity.goods.category3Id = this.catSelect3;
            this.goodsEntity.goods.typeTemplateId=this.typeId,
            this.goodsEntity.goods.brandId=this.selBrand,
            this.goodsEntity.goods.isEnableSpec=this.isEnableSpec,

            this.goodsEntity.goodsDesc.itemImages=this.imgEntitys,
            this.goodsEntity.goodsDesc.specListSel=this.specListSel,
            this.goodsEntity.goodsDesc.introduction=UE.getEditor('editor').getContent()

            this.goodsEntity.itemList = this.rowList;

            //发送请求
            axios.post("/goods/add",this.goodsEntity).then(function (response) {
                    if (response.data.success){
                        console.log(response.data);
                        location.href="goods.html";
                    }else {
                        alert(response.data.message);
                    }
                }).catch(function (reason) {
            });

        }


    },

    /*watch可以监听所有的属性值的变化*/
    watch: {
        typeId:function (newValue,oldValue) {
            let _this = this;
            this.brandList = [];
            this.selBrand = -1;

            /*加载分类数据*/
            axios.post("/template/findOne?id="+newValue).then(function (response) {
                    console.log(response.data);
                    _this.brandList = JSON.parse(response.data.brandIds);
                    console.log(_this.brandList);
                }).catch(function (reason) {
                console.log(reason);
            });

            /*每切换一次就会清空一次规格*/
            _this.specList = [];

            /*加载规格数据*/
            axios.post("/template/findBySpecId?id="+newValue).then(function (response) {
                console.log(response.data);
                _this.specList = response.data;
            }).catch(function (reason) {
                console.log(reason.data);
            })
        }
    },

    created: function() {
        this.loadCateData(0);
    }
});
