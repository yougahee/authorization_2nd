<template>
  <div class="login">
    <img src="../assets/logo.png">
    <h1>{{ msg }}</h1>
    <ul>
      <li>
        <input type="text" id="inputId" placeholder="Enter the Email id" />
      </li>
    </ul>
    <ul>
      <li>
        <input type="text" id="inputPW" placeholder="Enter the password" />
      </li>
    </ul>
    <button id="Login" @click="submit">Login</button>
    <p id="result" style="display:none">Hello</p>
    <ul>
      <li>
        <router-link to='/SignUp'>
          회원가입
        </router-link>
      </li>
    </ul>
    <ul>
      <li>
        <router-link to='/FindPW'>
          비밀번호 찾기
        </router-link>
      </li>
    </ul>
    <ul>
      <li>
        <router-link to='/user'>
          회원관리
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
      msg: 'Login Page',
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
              this.$router.push('/user')
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
  margin-top: 20px;
}
p{
  text-align: center;
  display: block;
  margin-top: 20px;
  color: red;
}
</style>
