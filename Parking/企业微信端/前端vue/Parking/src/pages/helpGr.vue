<template>
  <div style="height:100%;">
    <!---内容区域-->
    <div class="content helpContent">
      <mt-swipe :auto="0" :continuous="false">
        <mt-swipe-item>
          <div class="textTitle">一、个人预约</div>
          <div class="textContent">1、登录企业微信，在工作台中选择生活服务-停车管理，点击进入主页,点击停车场应用后，将认证企业微信用户ID，认证成功显示如下主页，<span class="redWord">认证失败，请联系物业管理员</span><br />（如图1）</div>
          <div class="textContent redWord suojin fontSize12" style="line-height:22px;">
            A.主页中央显示所有未完成的预约及状态<br />
            B.“日历”按钮<img class="smallCalendar" src="../assets/image/lookBtn.png" alt="">定义为“查看其它日期剩余车位的跳转按钮”<br />
            C.“当前预约数量”定义为“当天预约及其它未完成的预约总数量”<br />
            D.“车位预约”按钮定义为“预约车位功能的跳转按钮”<br />
            E.“今日剩余车位”定义为“当天剩余的可预约车位数量”<br />
            F.“历史记录”定义为“其它日期已结束或已完成的预约记录”<br />
            G.“帮助”按钮<img class="smallHelp" src="../assets/image/questionCompany.png" alt="">定义为“帮助手册的跳转按钮”<br />
            H.“我的”按钮<img class="smallMine" src="../assets/image/userPhoto.png" alt="">定义为“查看我的信息窗口跳转按钮”<br />
          </div>
          <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr01.jpg" alt=""><p class="textImgTips">图1</p></div>
        </mt-swipe-item>
        <mt-swipe-item>
          <div class="textTitle">二、预约车位</div>
          <div class="textContent">1、界面右下角“今日剩余车位”显示剩余车位，点击“日历”按钮查看其它日期剩余车位，若车位不为0，可点击车位预约按钮，输入车牌号码，来访时间等信息，点击提交完成预约。预约成功后，请于预约开始时间前2小时到后半小时内入场。<span class="redWord">（例如：预约时间为3月18日7：00至3月18日19：00，即车辆可在3月18日5：00至7：30时间段内入场，超过7：30未入场即为逾期，预约失效，需交付半小时停车费用）。此预约单次有效，出场后结束。</span>（如图2）</div>
          <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr02.jpg" alt=""><p class="textImgTips">图2</p></div>
        </mt-swipe-item>
        <mt-swipe-item>
          <div class="textTitle">三、预约状态信息查看</div>
          <div class="textContent" style="margin-bottom:10px;">1、预约状态分别有：待入场、已取消、已入场、已支付、已出场、已逾期。</div>
          <div class="textContent suojin fontSize12">
            A. 待入场<br />预约成功，车辆未入场，预约时间未到。可点击详情取消该预约。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr03.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12">
            B. 已取消<br />进入待入场预约详情页，点击取消预约，确认后免费取消该预约。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr04.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12">
            C. 已入场<br />预约开始时间段内，车辆进场后，状态由未入场变更为已入场。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr05.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12">
            D. 已支付<br />在出场前，提前支付费用可以过车自动抬杆出场，以免出现堵车情况。打开已入场详情页，点击去支付，确认金额后点击确认，调用微信支付接口支付费用。支付完成后，请于15分钟内出场，超过15分钟未出场会二次收取停车费用。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr06.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12">
            E. 已完成<br />车辆出场，费用已结清，预约结束，释放车位。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr07.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12" style="margin-bottom:50px;">
            F. 已逾期<br />预约开始时间半小时后，车辆未入场，此预约视为逾期失效。<span class="redWord">（例如：预约时间为3月18日7：00至3月18日19：00，即车辆可在3月18日5：00至9：00时间段内入场，超过9：00即为逾期，预约失效）。</span>在逾期详情页中，点击去支付，交纳半小时停车费用，不交纳停车费用将不能再使用预约功能。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr08.jpg" alt=""></div>
          </div>
        </mt-swipe-item>
        <mt-swipe-item>
          <div class="textTitle">四、预约历史记录查看</div>
          <div class="textContent" style="text-indent: 2em;">可在历史记录中查看预约扣费详情等信息</div>
          <div class="textImgBox"><img class="textImg" src="../assets/image/helpGr09.jpg" alt=""></div>
        </mt-swipe-item>
      </mt-swipe>
    </div>

  </div>
</template>
<script>
  export default {
    name: 'helpgr',
    data: function(){
      return {}
    },
    created(){
      if(this.$route.query.iswhere=="helpgr"){
        this.$route.meta.title='帮助'
        window.document.title = '帮助'
      }
    },
    methods: {}
  }
</script>
<style scoped></style>
