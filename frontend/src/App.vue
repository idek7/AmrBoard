<template>
  <div id="app">
    <div id="header">
      <main-header/>
    </div>
    <!--img src="./assets/logo.png"-->
    <router-view/>
  </div>
</template>

<script>
import Header from './shared-components/Header.vue'

export default {
  name: 'App',
  components: {
    'main-header': Header
  }
}
</script>
