<template>
  <header class="header">
    <span>AmrNotice</span>
    <span v-if="isAuthenticated">Hi~, {{ getUserName }}.</span>
    <div>
      <button class="btn btn-default" type="button" v-if="isAuthenticated" @click.prevent="onClickLogout">Logout</button>
    </div>
  </header>
</template>

<script>

export default {
  name: 'main-header',
  computed: {
    isAuthenticated () {
      let isAuthenticated = this.$store.getters.isAuthenticated
      if (!isAuthenticated) {
        isAuthenticated = sessionStorage.getItem('accessToken') || false
      }
      return isAuthenticated
    },
    getUserName () {
      return sessionStorage.getItem('userName')
    }
  },
  methods: {
    onClickLogout () {
      this.$store.dispatch('LOGOUT').then(() => window.location.replace('/'))
    }
  }
}
</script>

<style>
.header {
  display: flex;
  padding: 10px 20px;
  justify-content: space-between;
  align-items: center;
  border-bottom: 0.5px solid gray;
}
</style>
