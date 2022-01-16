<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="toggleLeftDrawer"
        />

        <q-toolbar-title>
          {{ title }}
        </q-toolbar-title>

        <div>Quasar v{{ $q.version }}</div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
    >
      <q-list>
        <q-item-label
          header
        >
          Essential Links
        </q-item-label>

        <EssentialLink
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import EssentialLink from 'components/EssentialLink.vue'

import { useStore } from 'vuex'

const linksList = [
  {
    title: 'Stock Categories',
    caption: 'Stock Management',
    icon: 'inventory',
    to: 'stock-categories'
  },
  {
    title: 'Stock Items',
    caption: 'Stock Management',
    icon: 'list',
    to: 'stock-items'
  }
]

import { defineComponent, ref, computed } from 'vue'

export default defineComponent({
  name: 'MainLayout',

  components: {
    EssentialLink
  },

  setup () {
    const $store = useStore()

    const title = computed({
      get: () => $store.state.toolbar.title,
      set: val => {
        $store.commit('toolbar/setTitle', val)
      }
    })

    const leftDrawerOpen = ref(false)

    return {
      essentialLinks: linksList,
      title,
      leftDrawerOpen,
      toggleLeftDrawer () {
        leftDrawerOpen.value = !leftDrawerOpen.value
      }
    }
  }
})
</script>
