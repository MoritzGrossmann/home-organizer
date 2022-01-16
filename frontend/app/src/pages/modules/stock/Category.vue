<script>
import _ from 'lodash'
import { ref, onMounted, watch } from 'vue'
import { api } from 'src/boot/axios'
import { useQuasar } from 'quasar'
import { useStore } from 'vuex'
import AddStockItemDialog from 'src/components/stock/AddStockItemDialog.vue'
import StockItem from 'src/components/stock/StockItem.vue'

export default {
  components: { AddStockItemDialog, StockItem },
  name: 'StockCategory',
  props: {
    id: {
      type: String,
      required: true
    }
  },
  setup (props) {
    const $q = useQuasar()
    const $store = useStore()
    const search = ref(null)
    const items = ref([])
    const category = ref(null)
    const addItemDialog = ref()

    async function loadCategory () {
      $q.loading.show()
      try {
        const response = await api.get('stock/category/' + props.id)
        category.value = response.data
        items.value = response.data.items
        $store.commit('toolbar/setTitle', category.value.name)
      } finally {
        $q.loading.hide()
      }
    }

    async function loadItems () {
      $q.loading.show()
      try {
        const response = await api.get(`stock/category/${props.id}/items`, { params: { search: search.value } })
        items.value = response.data
      } finally {
        $q.loading.hide()
      }
    }

    function showAddItem () {
      addItemDialog.value.show()
    }

    function onItemCreated (item) {
      items.value.push(item)
    }

    function onItemChanged (item) {
      const index = _.findIndex(items.value, i => i.id === item.id)
      items.value.splice(index, 1, item)
    }

    onMounted(async () => {
      await loadCategory()
    })

    watch(search, async () => {
      await loadItems()
    })

    return {
      loadCategory,
      showAddItem,
      addItemDialog,
      onItemCreated,
      onItemChanged,
      items,
      category,
      search
    }
  }
}
</script>

<template>
  <q-page padding>
    <div v-if="!category">
      <div class="row q-mb-lg">
        <div class="col-grow">
          <q-skeleton height="40px"/>
        </div>
        <div class="col-atuo">
          <q-skeleton type="circle" class="q-ml-md" height="40px"/>
        </div>
      </div>
      <q-space/>

      <q-skeleton class="q-mb-md" type="rect" v-for="i in 5" :key="i"/>
    </div>

    <div v-else>
      <div class="row">
        <div class="col-grow">
          <q-input dense v-model="search" debounce="500" label="Search">
            <template v-slot:prepend>
              <q-icon name="search" />
            </template>
          </q-input>
        </div>
        <div class="col-atuo">
          <q-btn round color="primary" class="q-ml-md" icon="add" @click="showAddItem"/>
        </div>
      </div>

      <q-space/>

      <q-list separator padding>
        <q-item>

            <q-item-section>
              <q-item-label>Item</q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-item-label>Status</q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-item-label>Count</q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-item-label>Decrease</q-item-label>
            </q-item-section>
        </q-item>
        <stock-item v-for="i in items" :value="i" :key="i.id" @changed="onItemChanged"></stock-item>
      </q-list>
      <add-stock-item-dialog v-if="category" ref="addItemDialog" :category="id" @item-created="onItemCreated"/>
    </div>
  </q-page>
</template>
