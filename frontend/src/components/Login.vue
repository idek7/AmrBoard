<template>
  <div>
    <h2 style="text-align:center;">Login</h2>
    <form @submit.prevent="onSubmit(id, password)">
      <div style="display:flex;margin-top:10px;">
        <span style="display:flex;width:100px;justify-content:flex-end;margin-right:20px">ID</span>
        <input type="text" v-model="id" placeholder="Enter your ID" required>
      </div>
      <div style="display:flex;margin-top:10px">
        <span style="display:flex;width:100px;justify-content:flex-end;margin-right:20px">PASSWORD</span>
        <input type="password" v-model="password" placeholder="Enter your password" required>
      </div>
      <div style="display:flex;margin-top:10px">
        <span style="display:flex;justify-content:flex-end;margin-right:20px;color:red">{{ msg }}</span>
      </div>
      <div style="margin-top:10px">
        <button-spinner
          :is-loading="isLoading"
          :disabled="isLoading"
          style="font-size:1.5rem;height:3rem;"
        >
          <span>Login</span>
        </button-spinner>
      </div>
    </form>
    <p><i></i></p>
  </div>
</template>

<script>
import ButtonSpinner from 'vue-button-spinner'

export default {
  data () {
    return {
      id: '',
      password: '',
      msg: '',
      isLoading: false
    }
  },
  components: {
    ButtonSpinner
  },
  methods: {
    onSubmit (id, password) {
      this.isLoading = true
      // LOGIN 액션 실행
      this.$store.dispatch('LOGIN', {id, password})
        .then((data) => {
          this.isLoading = false
          this.msg = this.$store.state.errorState
          this.$router.push('/')
        })
        .catch((err) => {
          this.isLoading = false
          console.log('bbb', err)
          this.msg = this.$store.state.errorState
        })
    }
  }
}
</script>

<style>
form {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>
