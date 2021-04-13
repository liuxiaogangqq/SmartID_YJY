<template>
  <div style="height:100%;">
    <!---内容区域-->
    <div class="content helpContent">
      <mt-swipe :auto="0" :continuous="false">
        <mt-swipe-item>
          <div class="textTitle">一、访客预约</div>
          <div class="textContent">1、登录企业微信，在工作台中选择生活服务-停车管理，点击进入主页，点击停车场应用后，将认证企业微信用户ID，认证成功显示如下主页 若认证失败，请联系物业管理员（如图1）</div>
          <div class="textContent redWord suojin fontSize12" style="line-height:22px;">
            A.“访客预约”定义为“访客车辆预约”查看<br />
            B.“个人预约”定义为“员工临时预约”查看<br />
            C.主页中央显示所有未完成的预约及状态<br />
            D.“日历”按钮<img class="smallCalendar" src="../assets/image/lookBtn.png" alt="">定义为“查看其它日期剩余车位的跳转按钮”<br />
            E.“当前预约数量”定义为“当天预约及其它未完成的预约总数量”<br />
            F.“车位预约”按钮定义为“预约车位功能的跳转按钮”<br />
            G.“今日剩余车位”定义为“当天剩余的可预约车位数量”<br />
            H.“历史记录”定义为“其它日期已结束或已完成的预约记录”<br />
            I.“帮助”按钮<img class="smallHelp" src="../assets/image/questionCompany.png" alt="">定义为“帮助手册的跳转按钮”<br />
            J.“我的”按钮<img class="smallMine" src="../assets/image/userPhoto.png" alt="">定义为“查看我的信息窗口跳转按钮”<br />
          </div>
          <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk01.jpg" alt=""><p class="textImgTips">图1</p></div>
        </mt-swipe-item>
        <mt-swipe-item>
          <div class="textTitle">二、预约车位</div>
          <div class="textContent">1、右下角“今日剩余车位”显示当天剩余车位，点击“日历”按钮显示其它日期的剩余车位，若车位不为0，可点击车位预约按钮，填写访客姓名、单位、车牌号码和预约开始时间及结束时间等信息，点击提交完成预约。预约成功后，车辆请于预约开始时间前2小时至后2小时时间段内入场<span class="redWord">（例如：预约时间为3月18日7：00至3月18日19：00，即车辆可在3月18日5：00至9：00时间段内入场，超过9：00未入场即为逾期，预约失效）。预约时间内，车辆进出次数不限。（如图2）</span></div>
          <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk02.jpg" alt=""></div>
          <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk03.jpg" alt=""><p class="textImgTips">图2</p></div>
        </mt-swipe-item>
        <mt-swipe-item>
          <div class="textTitle">三、预约状态信息查看</div>
          <div class="textContent" style="margin-bottom:10px;">1、预约状态分别有：待入场、已取消、已入场、已出场、已逾期、已完成。</div>
          <div class="textContent suojin fontSize12">
            A. 待入场<br />预约成功，车辆未入场，预约时间未到。可点击详情取消该预约。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk04.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12">
            B. 已取消<br />进入待入场预约详情页，点击取消预约，确认后免费取消该预约。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk05.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12">
            C. 已入场<br />预约开始时间段内，车辆进场后，状态由未入场变更为已入场。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk06.jpg" alt=""></div>
          </div>
          <div class="textContent suojin fontSize12">
            D. 已出场<br />在预约时间段内，车辆出场，预约状态变更为已出场。
            <!-- <div class="textImgBox"><img class="textImg" style="width:50%;" src="../assets/image/helpFk07.png" alt=""></div> -->
            <div style="text-indent: 2em;">在预约时间段内，车辆出场后，可选择提前结束此预约。在已出场详情页内，点击提前结束，确认后结束此预约，自动扣除停车费用（扣除金额为入场时间至提前结束时间的停车费用），状态更改为已完成。</div>
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk07.jpg" alt=""></div>
            <!-- <div class="textImgBox"><img class="textImg" style="width:50%;" src="../assets/image/helpFk08.png" alt=""></div> -->
          </div>
          <div class="textContent suojin fontSize12">
            E. 已完成<br />预约时间结束时，如果车辆在场内，则自动延期两小时；<br />预约时间结束时，如果车辆已出场，则预约自动结束，费用自动扣除，释放车位。
            <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk08.jpg" alt=""></div>
            <!-- <div class="textImgBox"><img class="textImg" style="width:50%;" src="../assets/image/helpFk09.png" alt=""></div> -->
          </div>
          <div class="textContent suojin fontSize12" style="margin-bottom:50px;">
            F. 已逾期<br />预约开始时间半小时后，车辆未入场，此预约视为逾期失效。<span class="redWord">（例如：预约时间为3月18日7：00至3月18日19：00，即车辆可在3月18日5：00至9：00时间段内入场，超过9：00即为逾期，预约失效）。</span>

            <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk09.jpg" alt=""></div>
          </div>
        </mt-swipe-item>
        <mt-swipe-item>
          <div class="textTitle">四、预约历史记录查看</div>
          <div class="textImgBox"><img class="textImg" src="../assets/image/helpFk10.jpg" alt=""></div>
        </mt-swipe-item>
      </mt-swipe>
    </div>

  </div>
</template>
<script>
  export default {
    name: 'helpgs',
    data: function(){
      return {}
    },
    created(){
      if(this.$route.query.iswhere=="helpgs"){
        this.$route.meta.title='帮助'
        window.document.title = '帮助'
      }
    },
    methods: {}
  }
</script>
<style scoped></style>
