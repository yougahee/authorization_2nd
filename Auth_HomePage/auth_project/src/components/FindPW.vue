<template>
  <div class="FindPW">
    <h1>{{ msg }}</h1>
    <h4 class="join_title">
      <label for="email">아이디(e-mail)</label>
    </h4>
    <span class="box int_email">
      <input type="text" id="email" class="int" maxlength="100">
    </span>
    <span class="error_next_box"></span>
    <button id="Login" @click="submit">이메일 발송</button>
    <p id="result" style="display:none">Hello</p>
    <ul>
      <li>
        <router-link to='/'>
          돌아가기
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: '비밀번호를 찾고 싶습니까?!',
      status: 0,
      success: false,
      message: '',
      token: '',
      refreshToken: ''
    }
  },
  methods: {
    submit: function () {
      var id = document.getElementById('inputId').value
      var pw = document.getElementById('inputPW').value
      
      if (id === null || id === undefined || id === '') {
        alert('Enter the id!!!!!!!!!')
      }
      else if (pw === null || pw === undefined || pw === '') {
        alert('Enter the pw!!!!!!!!!')
      } else {
        axios.post('http://localhost:8001/login', {
          email: id,
          password: pw
        }).then((res) => {
          console.log(res.headers.status)
          this.status = res.data.status
          this.success = res.data.success
          this.message = res.data.message
          this.token = res.data.token
          this.refreshToken = res.data.refreshToken
          if (res.status === 200) {
            if (res.data.success) {
              alert(this.message)
              location.href = "http://localhost:8080/main";
            } else {
              alert(this.message)
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
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
h4 {
  margin-top: 100px;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
div {
  margin: 0 15% 0 15%;
  display: block;
  align-items: center;
  justify-content: center;
}
input {
  display: block;
  text-align: center;
  margin:auto;
  padding: 10px 15px;
  font-size: 14px;
  line-height: 1.5;
  border: 1px solid #cccccc;
  border-radius: 5px;
}
button {
  background-color: #e08128;
  width: 100px;
  height: 35px;
  border: none;
  border-radius: 3px;
  display: block;
  align-content: center;
  text-align: center;
  margin:auto;
  margin-top: 100px;
}
p{
  text-align: center;
  display: block;
  margin-top: 20px;
  color: red;
}
</style>
