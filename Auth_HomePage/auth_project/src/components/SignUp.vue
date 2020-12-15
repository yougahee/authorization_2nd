<template>
  <div id="wrapper">
    <h1>{{ title }}</h1>
    <div id="content">
      <!-- ID 음,, 이메일 중복확인이나 이런 것도 해아할 듯? -->
      <div>
        <h3 class="join_title">
          <label for="email">아이디(e-mail)</label>
        </h3>
        <span class="box int_email">
          <input type="text" id="email" class="int" maxlength="100">
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
        <h3 class="join_title">
          <label for="name">이름</label>
        </h3>
        <span class="box int_name">
          <input type="text" id="name" class="int" maxlength="20">
        </span>
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
      <!-- JOIN BTN-->
      <div class="btn_area">
        <button type="button" id="btnJoin" @click="btnJoin">
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
      title: '회원가입'
    }
  },
  methods: {
    btnJoin: function () {
      var id = document.getElementById('email').value
      var pw1 = document.getElementById('pswd1').value
      var userName = document.getElementById('name').value 
      var gender = document.getElementById('gender').value

      console.log(id)
      console.log(pw1)
      console.log(userName)
      console.log(gender)

      var pwMsg = document.querySelector('#alertTxt')
      var pwImg1 = document.querySelector('#pswd1_img1')
      var pw2 = document.querySelector('#pswd2')
      var pwImg2 = document.querySelector('#pswd2_img1')
      var pwMsgArea = document.querySelector('.int_pass')
      var error = document.querySelectorAll('.error_next_box')

      if (id === null || id === undefined || id === '') {
        alert('Enter the id(email)!!!!!!!!!')
      }
      else if (pw1 === null || pw1 === undefined || pw1 === '') {
        alert('Enter the password!!!!!!!!!')
      }
      else if (userName === null || userName === undefined || userName === '') {
        alert('Enter the userName!!!!!!!!!')
      }
      else {
        axios.post('http://localhost:8001/signup', {
          email: id,
          password: pw1,
          name: userName,
          gender: gender
        }).then((res) => {
          console.log(res.headers.status)
          console.log(res.data)
          this.status = res.data.status
          this.success = res.data.success
          this.message = res.data.message
          this.shortestUrl = res.data.resultUrl
          if (res.status === 200) {
            if (res.data.success) {
              alert(this.message)
              location.href = "http://localhost:8080/";
            } else {
              alert('회원가입 실패!')
            }
          }
          console.log(res.data.status)
          console.log(res.data.success)
          console.log(res.data.message)
          console.log(res.data.resultUrl)
        }, function () {
          alert('400 Error : Bad Request')
        })
      }
    },
    submit: function () {
      var input = document.getElementById('inputUrl').value
      var result = document.getElementById('result')
      
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
