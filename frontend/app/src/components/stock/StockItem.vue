
<template>
  <q-item>
    <q-item-section>
      <q-item-label>{{ value.name }}</q-item-label>
      <q-item-label v-if="showCategory" caption>
        {{ value.category.name }}
      </q-item-label>
    </q-item-section>
    <q-item-section side>
      <q-badge v-if="value.status === 1" color="warning">Opened</q-badge>
      <q-badge v-if="value.status === 2" color="negative">Almost Empty</q-badge>
    </q-item-section>
    <q-item-section side>
      <q-item-label>{{ value.count }}</q-item-label>
    </q-item-section>
    <q-item-section side>
      <q-btn size="sm" color="secondary" icon="arrow_drop_down_circle" :loading="decreasing" @click="decrease"></q-btn>
    </q-item-section>
  </q-item>
</template>

<script>
import { ref } from 'vue'
import { api } from 'src/boot/axios'
import { useQuasar } from 'quasar'
const CHANGED = 'changed'

export default {
  name: 'StockItem',
  props: {
    value: {
      type: Object,
      required: true
    },
    showCategory: {
      type: Boolean,
      default: false
    }
  },
  emits: [
    CHANGED
  ],
  setup (props, { emit }) {
    const $q = useQuasar()

    const decreasing = ref(false)
    async function decrease () {
      decreasing.value = true
      try {
        const response = await api.patch(`stock/item/${props.value.id}/decrease`)
        emit(CHANGED, response.data)
      } catch (e) {
        if (e.response.status === 400) {
          $q.notify({
            type: 'negative',
            message: `Cannot decrease amount of ${props.value.name}`,
            icon: 'error'
          })
        }
      } finally {
        decreasing.value = false
      }
    }

    return {
      decreasing,
      decrease
    }
  }
}
</script>

<style>

</style>
