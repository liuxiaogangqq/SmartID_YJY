<template>
  <div id="app">
    <div class="content-div">
      <router-view v-if="isRouterAlive"></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'app',
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  }
}
</script>
<style>
  *{
    padding: 0;
    margin: 0;
  }
  html,body{
    width: 100%;
    height: 100%;
    background-color: #FFF;
  }
  ul{
    margin-block-start: 0;
    margin-block-end: 0;
    list-style: none;
    padding: 0;
  }
  p{
    margin-block-start: 0;
    margin-block-end: 0;
  }
  button{
    border: none;
  }
  input[type=text]:focus{
    outline: none;
  }
  /* 设置滚动条的样式 */
  ::-webkit-scrollbar {
      width: 0px;
  }
  /* 滚动槽 */
  ::-webkit-scrollbar-track {
      border-radius: 4px;
  }
  /* 滚动条滑块 */
  ::-webkit-scrollbar-thumb {
      border-radius: 4px;
      background-color: rgb(138, 138, 138);
  }
  #app{
    height: 100vh;
    background-color: #f5f5f8;
    overflow: auto;
  }
  .content-div{
    height: inherit;
  }


  .mint-header{
    background-color: #fff;
    box-shadow: 0 1px 2px rgba(0,0,0,0.1);
  }
  .mint-header-title{
    font-size: 17px;
    color: #6B6B6B;
  }
  .mint-header-button.is-right .headerRight{
    font-size: 14px;
    color: #33CC99;
    text-decoration: none;
  }
  .topBack{
    width: 8px;
  }
  .ellipsis{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .flexBetween{
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    align-items: center;
  }
  .mint-field-core{
    color: #333;
  }
  .appointment .mint-cell-wrapper{
    padding: 0;
  }
  .btnGroup{
    position: fixed;
    bottom: 0;
    left: 0;
    z-index: 10;
    width: 100%;
    height: 64px;
    line-height: 64px;
  }
  .loginBtn,.cancelBtn{
    display: inline-block;
    flex: 1;
    height: inherit;
    text-align: center;
    padding: 0;
    margin: 0;
    color: #264887;
    font-size: 22px;
    background-color: #fff;
    border: 1px solid #264887;
    border-radius: 0;
    box-shadow: none;
    box-sizing: border-box;
  }
  .loginBtn{
    color: #fff;
    background-color: #264887;
  }

  .appointBtnGroup{
    width: 100%;
    height: 44px;
    line-height: 44px;
    margin-top: 50px;
  }
  .appointSubmitBtn,.appointCancelBtn{
    font-size: 18px;
    border-radius: 8px;
  }
  .appointCancelBtn{
    margin-left: 20px;
  }

  /* 预约详情 */
  .appointmentDetails .mint-cell{
    min-height: 36px;
  }
  .appointmentDetails .mint-cell-text{
    color: #888;
  }
  .cancelAppointBtn{
    border-left: none;
    border-right: none;
  }
  .detailStatus{
    text-align: center;
    padding: 30px 0 0;
    margin-bottom: 30px;
  }
  .statusLine{
    position: relative;
    top: -3px;
    left: 0;
    width: 100%;
    height: 3px;
    background: url(./assets/image/statusLine.png) 0 0 no-repeat;
    background-size: 100% 3px;
  }
  .statusTips{
    color: #545252;
    font-size: 14px;
    margin-bottom: 30px;
  }
  .statusText{
    color: #BCBCBC;
    font-size: 24px;
    margin-bottom: 30px;
  }
  .statusText1{
    color: #FA9819;
  }
  .statusText2{
    color: #E31C1C;
  }
  .statusText3{
    color: #1A1C1D;
  }
  .carPlace{
    color: #424242;
  }
  .carPrice{
    color: #CC1313;
    margin-top: 5px;
  }
  /* 预约信息 */
  .carNumber .mintui-field-error:before,.visitType .mintui-field-error:before{
    content: '';
  }
  .visitType .mint-field-core{
    background: url('assets/image/selectIcon.png') 99% center no-repeat;
    background-size: 15px 8px;
  }
  .date .mint-field-core{
    background: url('assets/image/dateIcon.png') 99% center no-repeat;
    background-size: 22px 22px;
  }
  .date .mintui-field-error:before{
    content: '';
  }

  /* 弹窗样式 */
  .popupAppointVisible{
    width: 80%;
    padding: 35px 35px 20px;
    border-radius: 10px;
    box-sizing: border-box;
    position: absolute;
  }
  .popupAppointVisible .popupContent{
    text-align: center;
  }
  .popupIcon{
    position: absolute;
    top: -92px;
    left: 50%;
    margin-left: -85.75px;
  }
  .popupIcon img{
    display: block;
    width: 171.5px;
    height: 163.75px;
    margin: 0 auto;
  }
  .popupTxt{
    color: #424242;
    text-align: center;
    margin-top: 50px;
  }
  .stationBtn{
    color:#931d36;;
    font-size: 18px;
    margin-top: 15px;
    background: transparent;
    border: none;
    box-shadow: none;
  }
  /* 我的信息弹窗 */
  .mine{
    width: 60%;
    height: 100%;
    background: #fff;
    color: #333333;
    font-size: 14px;
    padding: 0;
    border-radius: 0;
    z-index: 2000;
  }
  .mineInfo{
    padding: 20px;
  }
  .photoDiv{
    width: 100%;
    text-align: center;
    margin-bottom: 80px;
  }
  .photo{
    width: 80px;
    height: 80px;
    margin: 36px auto 10px;
    overflow: hidden;
    border-radius: 50%;
    background: #e5e5e5;
  }
  .photo img{
    width: 100%;
  }
  .photoName{
    font-size: 16px;
  }
  .infoDiv{
    padding: 20px 0px;
  }
  .infoDiv li{
    color: #0F9FF2;
    height: 50px;
    /* list-style-type: circle; */
  }
  .infoDiv li div{
    color: #333;
    font-size: 14px;
  }
  .infoDiv li{display: flex; flex-direction: row; align-items: center; justify-content: center;}
  .infoDiv li>*{justify-content: center; align-items: center;}
  .infoDiv li .icon{width: 24px; height: 14px;}
  .infoDiv li .desc{font-weight: bold;}
  .infoDiv li .icon img{width: 80%;}
  .infoDiv li .flex-box{ flex:1; margin-left:10px;}
  .unbindBtn{
    width: 100%;
    height: 55px;
    line-height: 55px;
    text-align: center;
    color: #fff;
    font-size: 16px;
    background: #E31212;
    position: absolute;
    left: 0;
    bottom: 63px;
  }
  .fixedNav{
    position: fixed;
    top: 20px;
    left: 20px;
    right: 20px;
    z-index: 1000000000;
  }
  .fixedNav.mint-navbar{background-color: transparent; border: 2px solid #931d36; border-radius: 8px;}
  .fixedNav.mint-navbar .tabBorder{width: 100%; color: #931d36; }
  .mint-navbar .mint-tab-item.is-selected .tabBorder{border-bottom: none; color:#fff; background-color: #931d36;} 
  .marTopNav{
    margin-top: 74px;
  }

  /* 帮助 */
  .helpContent{
    width: 100%;
    height: 100%;
  }
  .mint-swipe{
    width: 100%;
    height: inherit;
  }
  .helpContent .mint-swipe-items-wrap > div{
    height: auto;
  }
  .mint-swipe-item{
    padding: 0 20px;
    box-sizing: border-box;
  }
  .helpContent .mint-swipe-items-wrap{
    height: auto;
    min-height: 100%;
    overflow: unset;
    overflow-y: scroll;
    background: #F6F6F6;
  }
  .helpContent .mint-swipe-indicators{
    position: fixed;
    bottom: 10px;
  }
  .textTitle{
    color: #121111;
    font-size: 22px;
    margin: 20px 0 10px;
  }
  .textContent{
    color: #646060;
    font-size: 14px;
  }
  img.smallCalendar{
    width: 25px;
    margin: 0 8px;
    vertical-align: middle;
  }
  img.smallHelp{
    width: 10px;
    margin: 0 8px;
    vertical-align: middle;
  }
  img.smallMine{
    width: 17px;
    margin: 0 8px;
    vertical-align: middle;
  }
  .fontSize12{
    font-size: 12px;
  }
  .suojin{
    padding: 0 20px;
  }
  .redWord{
    color: #F51010;
  }
  .textImgBox{
    text-align: center;
    margin: 20px auto;
  }
  .textImg{
    width: 80%;
  }
  .textImg3{
    width: 33%;
    height: 450px;
  }
  .textImgTips{
    font-size: 14px;
    text-align: center;
  }

  .displayFlexStart{
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-start;
  }
  .displayFlexColumn{
    display: flex;
    flex-flow: column nowrap;
    justify-content: space-around;
  }
  .textImg3Tips{
    font-size: 12px;
    margin-left: 20px;
  }
  .mint-swipe-indicator{
    width: 5px;
    height: 5px;
    background:#931d36;;
    border: 1px solid#931d36;;
    opacity: 1;
  }
  .mint-swipe-indicator.is-active{
    background: #fff;
  }
  




  /* 预约列表 */
  .mint-navbar .mint-tab-item{
    height: 50px;
    line-height: 50px;
    padding: 0;
  }
  .mint-navbar .mint-tab-item.is-selected {
    border-bottom: none;
    color: #931d36;
    margin-bottom: 0;
    box-sizing: border-box;
  }
  .flexBetween{
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    align-items: center;
  }
  .main-body { overflow: scroll; }
  .rongUl{ padding: 0px 15px; }
  .plateList{ padding: 10px 0px 20px;min-height: 300px; }
  .plateLi{
    width: 100%;
    height: 62.5px;
    padding: 7.5px 18px;
    margin-top: 10px;
    background:rgba(255,255,255,1);
    box-shadow:0px 0px 10px 0px rgba(0,2,11,0.2);
    border-radius: 10px;
    box-sizing: border-box;
  }
  .listDiv{
    vertical-align: middle;
  }
  .plateVisName{
    display: inline-block;
    width: 80px;
    color: #18191A;
    font-size: 18px;
    font-weight: 400;
    vertical-align: middle;
  }
  .plateNumber{
    display: inline-block;
    width: 100px;
    color: #18191A;
    font-size: 18px;
    font-weight: 400;
    vertical-align: middle;
  }
  .plateTips{
    font-size: 10px;
    font-weight: 400;
    padding: 2px 8px;
    vertical-align: middle;
    border-radius: 4px;
    background: rgba(244,145,88,.23);
    color: #C77247;
  }
  .plateTipsOver{
    background: rgba(65,154,235,.14);
    color: #2C73B3;
  }
  .plateTipsOverRed{
    color: #fff;
    background: #E31C1C;
  }
  .plateTipsOverHui{
    background-color: rgba(177,177,177,.14);
    color: #999999;
  }
  .plateTipsOverHui2{
    background-color: rgba(177,177,177,.14);
    color: #999999;
  }
  .plateTime{
    color: #606060;
    font-size: 12px;
    font-weight: 400;
    margin-top: 8px;
  }
  .jian{
    display: inline-block;
    width: 7px;
    height: 44.5px;
    color: #931d36;;
    background: url("assets/image/jian.png") center center no-repeat;
    background-size: 7px 13px;
  }
  .orderInfo{
    position: fixed;
    left: 0;
    bottom: 0;
    z-index: 2;
    width: 100%;
    height: 146px;
    box-sizing: border-box;
  }
  .xingz{
    position: absolute;
    top: 10px;
    z-index: 3;
    left: 20px;
    right: 20px;
    height: 70px;
    border-radius: 4px;
    box-shadow: 0 0 30px rgba(0,2,11,0.2);
    background-color: #fff;
    text-align: center;
  }
  .xingz button{
    position: relative;
    top: -15px;
    z-index: 4;
    color: #931d36;
    font-size: 14px;
    font-weight: 500;
    font-family: "SourceHanSansCN-Medium";
    width: 75px;
    min-height: 75px;
    background: #fff;
    border: none;
    border-radius: 50% 50% 0 0;
  }
  .infoContent{
    height: inherit;
  }
  .infoCount{
    position: relative;
  }
  .lookCount{
    width: 52px;
    height: 52px;
    overflow: hidden;
    position: fixed;
    bottom: 150px;
    right: 15px;
    z-index: 1000000;
    background: none;
    outline: none;
  }
  .lookCount img{
    width: 100%;
    height: 100%;
  }
  .todayCount,.onlyCount{
    width: 30%;
    text-align: center;
    padding: 20px 15px 0;
    color: #333333;
    font-size: 22px;
    font-weight: 700;
    z-index: 4;
  }
  .todayCount span,.onlyCount span{
    margin-top: 6px;
    font-size: 12px;
    font-weight: 400;
    color: #808080;
  }
  .infoHelp{
    margin-top: 24px;
    width: 100%;
    text-align: center;
    padding: 10px 15px;
    box-sizing: border-box;
    color: #fff;
    font-size: 12px;
    font-weight: 400;
    vertical-align: top;
    background-color: #931d36;
    line-height: 2;
  }
  .question{
    width: 16.5px;
    height: 16.5px;
    background: url("assets/image/questionSelf.png") center 0 no-repeat;
    background-size: 100% 100%;
  }
  .historyText{
    position: relative;
    left: 9px;
    text-decoration: underline;
  }
  .userInfo{
    position: relative;
    width: 32px;
    height: 31.5px;
    background: url("assets/image/userPhoto.png") center 0 no-repeat;
    background-size: 100% 100%;
  }
  .xingz button{
    color: #931d36;
    font-size: 12px;
    background: #fff;
  }
  .xingz button img{
    display: block;
    margin: 2px auto 0;
    width:60px;
  }
  .noMoreData{
    width: 100%;
    text-align: center;
  }
  .parkLotPopup.mint-popup{
    width: 86%;
    height: 470px;
    overflow: hidden;
    border-radius: 10px;
    padding: 0px 0px 20px;
    box-sizing: border-box;
  }
  .parkLotPopupTips{
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    color: #fff;
    font-size: 14px;
    background-color: #931d36;
  }
  .parkLotPopupTips span{
    display: inline-block;
    width: 20px;
    height: 20px;
    line-height: 20px;
    text-align: center;
    border: 1px solid #fff;
    border-radius: 50%;
    color: #fff;
    margin: 0 5px;
    vertical-align: middle;
  }
  .van-calendar{
    width: 94%;
    margin: 0 auto;
  }
  .van-calendar__popup.van-popup--bottom, .van-calendar__popup.van-popup--top {
    height: 92%!important;
  }
  .van-calendar__month:last-child{
    padding-bottom: 0px!important;
  }
  .parkLotPopup .van-calendar{
    border-radius: 10px;
    overflow-y: auto;
  }
  .van-calendar__header{
    box-shadow: unset!important;
  }
  .van-calendar__body{
    overflow: unset!important;
  }
  .van-calendar__weekdays{
    position: fixed;
    top: 50px;
    left: 2%;
    width: 96%;
    height: 50px;
    background: #fff;
    z-index: 1000;
  }
  .van-calendar__weekday{
    line-height: 50px!important;
  }
  .van-calendar__body{
    margin-top: 70px;
  }
  .parkLotPopup .van-calendar__header-title, .van-calendar__header-subtitle, .van-calendar__month-title{
    display: none;
  }
  .van-calendar__day, .van-calendar__selected-day{
    align-items: flex-start!important;
  }
  .van-calendar__selected-day {
    font-weight: 600;
    color: #931d36!important;
    background-color: transparent!important;
    outline:none!important;
  }
  .van-calendar__bottom-info {
    bottom: 2px!important;
    width: 20px;
    height: 20px;
    line-height: 20px!important;
    border-radius: 50%;
    margin: 0 auto;
    border: 1px solid #931d36;
    background: #fff;
    color: #931d36;
  }
  .van-calendar__selected-day .van-calendar__bottom-info {
    background: #931d36;
    color: #fff;
  }
  .van-calendar__day{
    height: 50px!important;
    margin-bottom: 10px;
  }
  .van-calendar__day:focus{
    background-color: unset;
    border: 0;
    outline: 0;
    outline: none;
  }
  
  /* 查看近期停车位的表格样式 */
  /* .parkLotTitle{
    padding: 0px 30px;
  }
  .parkLotTitle li{
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    color: #6C6C6C;
    line-height: 40px;
    border-top: 1px solid #f0f0f0;
  }
  .parkLotTitle li span:first-child{
    flex: 2;
    text-align: center;
  }
  .parkLotTitle li span:last-child{
    flex: 1;
    text-align: center;
  }
  .parkLotUl{
    max-height: 500px;
    overflow-y: scroll;
    padding: 0px 30px;
  }
  .parkLotLi{
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    color: #AAA;
    line-height: 40px;
    border-top: 1px solid #f0f0f0;
  }
  .parkLotLi span:first-child{
    flex: 2;
    text-align: center;
  }
  .parkLotLi span:last-child{
    flex: 1;
    text-align: center;
  }
  .parkLotLi:nth-child(1){
    color: #FA9819;
  }
  .parkLotLi:nth-child(1) span:first-child{
    background: url("../assets/image/carToday.png") 5px center no-repeat;
    background-size: 16px 10px;
  }
  .parkLotLi:nth-child(2){
    color: #15E7A6;
  }
  .parkLotLi:nth-child(2) span:first-child{
    background: url("../assets/image/carTorrow.png") 5px center no-repeat;
    background-size: 16px 10px;
  }
  .parkLotLi:nth-child(3){
    color:#931d36;;
  }
  .parkLotLi:nth-child(3) span:first-child{
    background: url("../assets/image/carThird.png") 5px center no-repeat;
    background-size: 16px 10px;
  } */





  /* 添加历史车牌号码 */
  .carNumber .mint-field-core{
    flex: unset;
    width: 0;
  }
  .carNumber .mint-field-other{
    width: 100%;
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
  }
  input.choiceCarNumber{
    display: inline-block;
    width: 85%;
    height: 66px;
    line-height: 66px;
    border: none;
    outline: none;
    font-size: inherit;
  }
  .historyCar{
    display: inline-block;
  }
  .historyCar img{
    width: 27px;
    height: 27px;
    padding-top: 17px;
  }
  .historyCarVisible{
    width: 50%;
    border-radius: 10px;
    padding: 15px 20px;
  }
  .historyCarList li{
    font-size: 16px;
    text-align: center;
    height: 49px;
    line-height: 50px;
    border-top: 1px solid #eee;
  }
  .historyCarList li:first-child{
    border-top: none;
  }
</style>
