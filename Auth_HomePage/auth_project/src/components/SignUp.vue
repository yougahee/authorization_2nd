<template>
  <div id="wrapper">
    <h1>{{ title }}</h1>
    <div id="content">
      <!-- ID 음,, 이메일 중복확인이나 이런 것도 해아할 듯? -->
      <div>
        <h3 class="join_title">
          <label for="email">아이디(e-mail)</label>
        </h3>
        <span class="box int_id">
          <input type="text" id="id" class="int" maxlength="20">
        </span>
        <span class="error_next_box"></span>
      </div>
      <!-- PW1 -->
      <div>
        <h3 class="join_title"><label for="pswd1">비밀번호</label></h3>
        <span class="box int_pass">
          <input type="text" id="pswd1" class="int" maxlength="20">
          <span id="alertTxt">사용불가</span>
          <img src="m_icon_pass.png" id="pswd1_img1" class="pswdImg">
        </span>
        <span class="error_next_box"></span>
      </div>
      <!-- PW2 -->
      <div>
        <h3 class="join_title"><label for="pswd2">비밀번호 재확인</label></h3>
          <span class="box int_pass_check">
            <input type="text" id="pswd2" class="int" maxlength="20">
              <img src="m_icon_check_disable.png" id="pswd2_img1" class="pswdImg">
          </span>
          <span class="error_next_box"></span>
      </div>
      <!-- NAME -->
      <div>
        <h3 class="join_title"><label for="name">이름</label></h3>
        <span class="box int_name">
          <input type="text" id="name" class="int" maxlength="20">
          </span>
          <span class="error_next_box"></span>
          </div>
      <!-- BIRTH -->
      <div>
        <h3 class="join_title"><label for="yy">생년월일</label></h3>
        <div id="bir_wrap">
          <!-- BIRTH_YY -->
          <div id="bir_yy">
            <span class="box">
              <input type="text" id="yy" class="int" maxlength="4" placeholder="년(4자)">
              </span>
              </div>
              <!-- BIRTH_MM -->
              <div id="bir_mm">
                <span class="box">
                  <select id="mm" class="sel">
                    <option>월</option>
                    <option value="01">1</option>
                    <option value="02">2</option>
                    <option value="03">3</option>
                    <option value="04">4</option>
                    <option value="05">5</option>
                    <option value="06">6</option>
                    <option value="07">7</option>
                    <option value="08">8</option>
                    <option value="09">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    </select>
                    </span>
                    </div>
                    <!-- BIRTH_DD -->
                    <div id="bir_dd">
                      <span class="box">
                        <input type="text" id="dd" class="int" maxlength="2" placeholder="일">
                        </span>
                        </div>
                    </div>
                    <span class="error_next_box"></span>    
                </div>
                <!-- GENDER -->
                <div>
                    <h3 class="join_title"><label for="gender">성별</label></h3>
                    <span class="box gender_code">
                        <select id="gender" class="sel">
                            <option>성별</option>
                            <option value="M">남자</option>
                            <option value="F">여자</option>
                        </select>                            
                    </span>
                    <span class="error_next_box">필수 정보입니다.</span>
                </div>
                <!-- EMAIL 나중에 지우기 -> 어짜피 id가 이메일이니까. -->
                <div>
                    <h3 class="join_title"><label for="email">본인확인 이메일<span class="optional">(선택)</span></label></h3>
                    <span class="box int_email">
                        <input type="text" id="email" class="int" maxlength="100" placeholder="선택입력">
                    </span>
                    <span class="error_next_box">이메일 주소를 다시 확인해주세요.</span>    
                </div>
                <!-- MOBILE -->
                <div>
                    <h3 class="join_title"><label for="phoneNo">휴대전화</label></h3>
                    <span class="box int_mobile">
                        <input type="tel" id="mobile" class="int" maxlength="16" placeholder="전화번호 입력">
                    </span>
                    <span class="error_next_box"></span>    
                </div>
                <!-- JOIN BTN-->
                <div class="btn_area">
                    <button type="button" id="btnJoin">
                        <span>가입하기</span>
                    </button>
                </div>
                <div> 
                  <ul>
                    <li>
                      <router-link to='/'>
                        로그인창으로 돌아가기
                      </router-link>
                    </li>
                  </ul>
                </div>
            </div> 
        </div> 
</template>

<script>
/*
id.addEventListener("change", checkId);
pw1.addEventListener("change", checkPw);
pw2.addEventListener("change", comparePw);
userName.addEventListener("change", checkName);
yy.addEventListener("change", isBirthCompleted);
mm.addEventListener("change", isBirthCompleted);
dd.addEventListener("change", isBirthCompleted);
gender.addEventListener("change", function() {
    if(gender.value === "성별") {
        error[5].style.display = "block";
    } else {
        error[5].style.display = "none";
    }
})
email.addEventListener("change", isEmailCorrect);
mobile.addEventListener("change", checkPhoneNum);

var idPattern = /[a-zA-Z0-9_-]{5,20}/;
var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
var namePattern = /[a-zA-Z가-힣]/;
var yearPattern = /[0-9]{4}/;
var datePattern = /\d{1,2}/;
var emailPattern = /[a-z0-9]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/;
var isPhoneNum = /([01]{2,})([01679]{1,})([0-9]{3,4})([0-9]{4})/;

function checkPw() {
    var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
    if(pw1.value === "") {
        error[1].innerHTML = "필수 정보입니다.";
        pwMsg.style.display = "block";
        pwMsgArea.style.paddingRight = "40px";
        pwImg1.src = "m_icon_pass.png";
        error[1].style.display = "block";
    } else if(!pwPattern.test(pw1.value)) {
        error[1].innerHTML = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
        pwMsg.innerHTML = "사용불가";
        pwMsgArea.style.paddingRight = "93px";
        error[1].style.display = "block";
        pwMsg.style.color = "red";
        pwMsg.style.display = "block";
        pwImg1.src = "m_icon_not_use.png";
    } else {
        error[1].style.display = "none";
        pwMsg.innerHTML = "안전";
        pwMsgArea.style.paddingRight = "93px";
        pwMsg.style.color = "#03c75a";
        pwMsg.style.display = "block";
        pwImg1.src = "m_icon_safe.png";
    }
}
*/
import axios from 'axios'
export default {
  data () {
    return {
      status: 0,
      success: false,
      message: '',
      shortestUrl: '',
      title: '회원가입'
    }
  },
  methods: {
    submit: function () {
      var input = document.getElementById('inputUrl').value
      var result = document.getElementById('result')
      
      var id = document.querySelector('#id')
      var pw1 = document.querySelector('#pswd1')
      var pwMsg = document.querySelector('#alertTxt')
      var pwImg1 = document.querySelector('#pswd1_img1')
      var pw2 = document.querySelector('#pswd2')
      var pwImg2 = document.querySelector('#pswd2_img1')
      var pwMsgArea = document.querySelector('.int_pass')
      var userName = document.querySelector('#name') 
      var yy = document.querySelector('#yy')
      var mm = document.querySelector('#mm')
      var dd = document.querySelector('#dd')
      var gender = document.querySelector('#gender')
      var email = document.querySelector('#email')
      var mobile = document.querySelector('#mobile')
      var error = document.querySelectorAll('.error_next_box')
      
      if (input === null || input === undefined || input === '') {
        alert('Enter the URL!!!!!!!!!')
        result.innerHTML = ''
      } else {
        axios.post('http://localhost:8001/url', {
          url: input
        }).then((res) => {
          console.log(res.headers.status)
          this.status = res.data.status
          this.success = res.data.success
          this.message = res.data.message
          this.shortestUrl = res.data.resultUrl
          if (res.status === 200) {
            if (res.data.success) {
              result.innerHTML = this.shortestUrl
              result.style.display = 'block'
            } else {
              result.innerHTML = 'Invaild URL'
              result.style.display = 'block'
            }
          }
          console.log(res.data.status)
          console.log(res.data.success)
          console.log(res.data.message)
          console.log(res.data.resultUrl)
        }, function () {
          alert('400 Error : Bad Request')
          result.innerHTML = ''
        })
      }
      document.getElementById('inputUrl').value = ''
    }
  }
}
</script>

<style scoped>
/* 레이아웃 틀 */
#header {
    padding-top: 62px;
    padding-bottom: 20px;
    text-align: center;
}
#wrapper {
    position: relative;
    height: 100%;
}
#content {
    position: absolute;
    left: 50%;
    transform: translate(-50%);
    width: 460px;
}

ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin-bottom: 91px;
}
/* 입력폼 */
h3 {
    margin: 19px 0 8px;
    font-size: 14px;
    font-weight: 700;
}
.box {
    display: block;
    width: 100%;
    height: 51px;
    border: solid 1px #dadada;
    padding: 10px 14px 10px 14px;
    box-sizing: border-box;
    background: #fff;
    position: relative;
}
.int {
    display: block;
    position: relative;
    width: 100%;
    height: 29px;
    border: none;
    background: #fff;
    font-size: 15px;
}
input {
    font-family: Dotum,'돋움',Helvetica,sans-serif;    
}
.box.int_id {
    padding-right: 110px;
}
.box.int_pass {
    padding-right: 40px;
}
.box.int_pass_check {
    padding-right: 40px;
}
.step_url {
    /*@naver.com*/
    position: absolute;
    top: 16px;
    right: 13px;
    font-size: 15px;
    color: #8e8e8e;
}
.pswdImg {
    width: 18px;
    height: 20px;
    display: inline-block;
    position: absolute;
    top: 50%;
    right: 16px;
    margin-top: -10px;
    cursor: pointer;
}
#bir_wrap {
    display: table;
    width: 100%;
}
#bir_yy {
    display: table-cell;
    width: 147px;
}
#bir_mm {
    display: table-cell;
    width: 147px;
    vertical-align: middle;
}
#bir_dd {
    display: table-cell;
    width: 147px;
}
#bir_mm, #bir_dd {
    padding-left: 10px;
}
select {
    width: 100%;
    height: 29px;
    font-size: 15px;
    background: #fff url(https://static.nid.naver.com/images/join/pc/sel_arr_2x.gif) 100% 50% no-repeat;
    background-size: 20px 8px;
    -webkit-appearance: none;
    display: inline-block;
    text-align: start;
    border: none;
    cursor: default;
    font-family: Dotum,'돋움',Helvetica,sans-serif;
}

/* 에러메세지 */
.error_next_box {
    margin-top: 9px;
    font-size: 12px;
    color: red;    
    display: none;
}
#alertTxt {
    position: absolute;
    top: 19px;
    right: 38px;
    font-size: 12px;
    color: red;
    display: none;
}
/* 버튼 */
.btn_area {
    margin: 30px 0;
}
#btnJoin {
    width: 100%;
    height: 50px;
    background-color: #e08128;
    border: none;
    border-radius: 7px;
    align-content: center;
    text-align: center;
    margin:auto;
    font-size: 20px;
    font-weight: 400;
    font-family: Dotum,'돋움',Helvetica,sans-serif;
    color: #fff;
}
</style>
