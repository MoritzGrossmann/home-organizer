<template>
  <q-dialog v-model="dialog" position="top">
    <q-card style="width: 700px; max-width: 90vw;">
        <q-form @submit.prevent.stop="onOk">
          <q-card-section class="bg-primary text-white">
            <div class="text-h6">Add Stock Item</div>
          </q-card-section>
          <q-card-section>
            <q-input dense v-model="name" :rules="[val => !!val || 'Name is required']" label="Name">
            </q-input>

            <q-select
              v-model="selectedCategory"
              label="Category"
              dense
              emit-value
              map-options
              :options="categories"
              :rules="[val => !!val || 'Category is required']"
            >
            </q-select>
            <q-input dense v-model.number="count" label="Bestand">
            </q-input>
              <q-btn-toggle spread class="q-mt-md"
                v-model="status"
                :toggle-color="status === 0 ? 'primary' : (status === 1 ? 'warning' : 'negative')"
                                :options="[
                  {label: 'Full', value: 0},
                  {label: 'Opened', value: 1},
                  {label: 'Almost Empty', value: 2}
                ]"
              />
          </q-card-section>
          <q-card-actions align="right" class="bg-white text-primary">
            <q-btn color="primary" outline label="Cancel" @click="onCancel"/>
            <q-btn type="submit" color="primary" label="OK" :loading="addingItem"/>
          </q-card-actions>
        </q-form>
      </q-card>
  </q-dialog>
</template>

<script>

import { ref, defineExpose, watch, onMounted } from 'vue'
import { api } from 'src/boot/axios'

export default {
  props: {
    category: {
      type: String,
      default: null
    }
  },
  emits: [
    'item-created'
  ],
  setup (props, { emit }) {
    const name = ref(null)
    const count = ref(0)
    const status = ref(0)
    const addingItem = ref(false)
    const dialog = ref(false)
    const selectedCategory = ref(null)
    const categories = ref([])
    const categoriesLoading = ref(false)

    function show () {
      dialog.value = true
    }

    defineExpose({
      show
    })

    async function onOk () {
      addingItem.value = true
      try {
        const response = await api.post('stock/item', {
          name: name.value,
          count: count.value,
          status: status.value,
          category_id: props.category || selectedCategory.value
        })

        emit('item-created', response.data)
        dialog.value = false
        clearForm()
      } finally {
        addingItem.value = false
      }
    }

    async function loadCategories () {
      categoriesLoading.value = true
      try {
        const response = await api.get('stock/category')
        categories.value = response.data.map(c => {
          return {
            value: c.id,
            label: c.name
          }
        })
      } finally {
        categoriesLoading.value = false
      }
    }

    function clearForm () {
      name.value = null
      count.value = 0
      status.value = 0
    }

    function onCancel () {
      clearForm()
      dialog.value = false
    }

    watch(dialog, (newValue) => {
      if (!newValue) clearForm()
    })

    onMounted(async () => {
      await loadCategories()
    })

    return {
      name,
      count,
      status,
      dialog,
      onCancel,
      onOk,
      show,
      selectedCategory,
      categories,
      addingItem
    }
  }
}
</script>
