<template>
  <div>
    <!--头部-->
    <!-- <mt-header fixed title="首页"></mt-header> -->
    <!---内容区域-->
    <div class="content">
            <!-- <div class="main-body" ref="wrapper" :style="{ width: '100%',height: (wrapperHeight) + 'px' }"> -->
            <div class="main-body" ref="wrapper">
                <mt-loadmore :top-method="loadTop" :bottom-method="loadBottom" @bottom-status-change="handleBottomChange" :bottom-all-loaded="allLoaded" ref="loadmore1" :autoFill="isAutoFill">
                  <div class="rongUl">
                    <p v-if="len2" style="text-align:center;margin-top:50px;">暂无数据</p>
                    <ul class="mui-table-view mui-grid-view plateList">
                      <li v-for="(item,index) in datas" :key="index" class="mui-table-view-cell mui-media mui-col-xs-6 plateLi flexBetween" @click="toDetails(item.recordInnerId,datas[index])">
                          <!-- <img class="mui-media-object" v-lazy="item.img"> -->
                          <div>
                            <div class="listDiv">
                              <span class="plateNumber" v-text="item.visitorCarCode"></span>
                              <span v-if="item.visitorState==1" class="plateTips">待入场</span>
                              <span v-else-if="item.visitorState==3" class="plateTips plateTipsOverHui">已取消</span>
                              <span v-else-if="item.visitorState==4" class="plateTips plateTipsOver">已入场</span>
                              <span v-else-if="item.visitorState==5" class="plateTips plateTipsOver">已支付</span>
                              <span v-else-if="item.visitorState==6" class="plateTips plateTipsOverHui2">已完成</span>
                              <span v-else-if="item.visitorState==7" class="plateTips plateTipsOverHui">已出场</span>
                              <span v-else class="plateTips plateTipsOverRed">已逾期</span>
                            </div>
                            <div class="plateTime">
                                <span class="plateStartTime" v-text="cutTime(item.visitorBeginTime)"></span> —
                                <span class="plateEndTime" v-text="cutTime(item.visitorEndTime)"></span>
                            </div>
                          </div>
                          <div>
                            <div class="money">￥<span v-if="item.money==null" v-text="0"></span><span v-else v-text="item.money/100"></span></div>
                            <div class="jian"></div>
                          </div>
                      </li>
                    </ul>
                    <div class="noMoreData" v-if="more">没有更多数据了...</div>
                  </div>
                </mt-loadmore>
            </div>
    </div>

  </div>
</template>
<script>
  import { Toast } from 'mint-ui'
  export default {
    name: 'staffRecord',
    data: function(){
      return {
        len2: false,
        more: false,
        visitorCarCode: '',
        datas: [],
        //可以进行上拉
        allLoaded: false,
        //是否自动触发上拉函数
        isAutoFill: false,
        wrapperHeight: 0,
        courrentPage: 0,
        page: 1,
      }
    },
    mounted() {
      // 父控件要加上高度，否则会出现上拉不动的情况
      this.wrapperHeight =
        document.documentElement.clientHeight -
        this.$refs.wrapper.getBoundingClientRect().top;
    },
    created(){
      if(this.$route.query.iswhere=="staffRecord"){
        this.$route.meta.title='历史记录'
        window.document.title = '历史记录'
      }
      this.getRecord()
    },
    methods: {
      cutTime(time){
        if (time !== '' && time !== null) {
            time = time.substring(0,16);
            return time;
          }
      },
      getRecord(){
        var that = this
        this.$axios.post("getCompanyVistor",{
          page: that.page,
          size: 10,
          OrderType: 2,
          VisitorStateHis: '1,2,3,4,5,6,7,8',
          requestType: 1
        })
        .then(response => {
          if( response.data.status == 200 ){
              that.datas = response.data.result;
              var length2 = that.datas.length
              // alert(length2)
              if( length2 < 1){
                that.len2 = true
              }else{
                that.len2 = false
              }
          }else{
            let instance = Toast({
              message: response.data.message,
              position: 'bottom',
              duration: 5000
            });
            setTimeout(() => {
              instance.close();
            }, 2000);
          }
        })
        .catch(error => {
          console.log(error);
        });
      },
      // 进入个人预约详情
      toDetails(id,list){
        localStorage.setItem('dataVistorInfo1',JSON.stringify(list))
        this.$router.push({
          path: '/details',
          query: {
            visType: 2,
            VisRecordInnerId: id
          }
        })
      },
      // 下拉刷新
      loadTop() {
        this.loadFrist();
      },
      // 上拉加载
      loadBottom() {
        this.loadMore();
      },
      // 下拉刷新
      loadFrist() {
        var that = this
        this.$axios.post("getCompanyVistor",{
          page: 1,
          size: 10,
          OrderType: 2,
          VisitorStateHis: '1,2,3,4,5,6,7,8',
          requestType: 1
        })
        .then(response => {
            if( response.data.status == 200 ){
              that.courrentPage = 0;
              that.allLoaded = false; // 可以进行上拉
              that.datas = response.data.result;
              that.$refs.loadmore1.onTopLoaded();
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
        })
        .catch(error => {
          console.log(error);
        });
      },
      // 上拉加载
      loadMore() {
        var that = this
        that.page++
        this.$axios.post("getCompanyVistor",{
          page: that.page,
          size: 10,
          OrderType: 2,
          VisitorStateHis: '1,2,3,4,5,6,7,8',
          requestType: 1
        })
        .then(response => {
            if( response.data.status == 200 ){
              // concat数组的追加
              that.datas = that.datas.concat(response.data.result);
              if (that.courrentPage > 2) {
                that.allLoaded = true; // 若数据已全部获取完毕
              }
              that.courrentPage++;
              that.$refs.loadmore1.onBottomLoaded();
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
        })
        .catch(error => {
          console.log(error);
        });
      },
      handleBottomChange(){
        var that = this
        if( that.allLoaded == true ){
          that.more = true
          setTimeout(function(){
            that.more = false
          },2000)
        }
      }
    }
  }
</script>
<style scoped>
  .content {
    overflow: hidden;
  }
  .tabBorder{
    height: 50px;
    line-height: 50px;
    margin: 0 auto;
    font-size: 16px;
  }
  .money{
    display: inline-block;
    color: #D71313;
    margin-right: 8px;
    vertical-align: top;
    padding-top: 12px;
  }
</style>
