<template>
  <div class="userList">
    <h1>{{ msg }}</h1>
    <ul id = "userlist">
      <li> 아이디(이메일)
      </li>
      <li> 회원 이름
      </li>
    </ul>
    <ul id = "example-1">
      <div class = "">
        <div v-for="item in items" >
          <div class="grid">
            <li>
              {{item.email}}
            </li>
            <li id = "user-name">
              {{item.name}}
            </li>
          </div>
        </div>
      </div>
    </ul>
    <button id="Login" @click="getAllUsers">회원정보가져오기</button>
    <p id="result" style="display:none">Hello</p>
    <ul>
      <li>
        <router-link to='/login'>
          로그아웃
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: '',
  data () {
    return {
      msg: 'User List',
      status: 0,
      success: false,
      message: '',
      token: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcyIsImdlbmRlciI6IuyXrCIsImV4cCI6MTYwODQ4OTA2NSwiaWF0IjoxNjA4NDg1NDY1fQ._Z8ShXZ6OTplBS-n-pxz-8EaLOMj-JgzHD8X8yu6i6w',
      refreshToken: '',
      items: []
    }
  },
  methods: {
    getAllUsers: function () {
      let config = {
        headers: {
          'token' : this.token
        }
      }
      axios.get('http://localhost:8001/users', config)
      .then((res) => {
          console.log(res.data)
          console.log(res.data.data)
          if (res.data.success) {
            this.items = res.data.data
            console.log("item " + this.items)
            alert(res.data.message)
          } else {
            alert(res.data.message)
          }
        }, function () {
          alert('400 Error : Bad Request')
        })
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
  width: 200px;
  height: 35px;
  border: none;
  border-radius: 3px;
  display: block;
  align-content: center;
  text-align: center;
  color:#FFFFFF;
  margin:auto;
  margin-top: 20px;
}
p{
  text-align: center;
  display: block;
  margin-top: 20px;
  color: red;
}
.list-row-item{
  display: grid;
  grid-template-columns: 150px 1fr;
  padding: 4px;
  transition: all 0.5s;
}
.user-name{
  display: block;
}
</style>
