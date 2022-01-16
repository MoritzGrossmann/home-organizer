<template>
  <router-view />
</template>

<script>
import { api } from './boot/axios'
import { onMounted } from 'vue'

export default {
  name: 'App',
  setup () {
    function loadInit () {
      api.get('/dummy/hello').then(r => {
        // alert(r.data)
      })
    }

    onMounted(() => {
      loadInit()
    })

    return {
      loadInit
    }
  }
}
</script>
