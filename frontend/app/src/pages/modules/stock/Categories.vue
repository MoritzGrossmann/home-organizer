<script>
import { ref, onMounted, watch } from 'vue'
import { api } from 'src/boot/axios'
import { useQuasar } from 'quasar'

export default {
  name: 'StockCategories',
  setup () {
    const $q = useQuasar()

    const addCategoryDialog = ref(false)
    const addCategoryName = ref(null)
    const addCategoryDescription = ref(null)
    const addingCategory = ref(false)
    const search = ref(null)
    const categories = ref([])

    async function loadCategories () {
      $q.loading.show()
      try {
        const response = await api.get('stock/category')
        categories.value = response.data
      } finally {
        $q.loading.hide()
      }
    }

    async function onCreateNewCategory () {
      addingCategory.value = true
      try {
        const response = await api.post('stock/category', {
          name: addCategoryName.value,
          description: addCategoryDescription.value
        })

        categories.value.push(response.data)
        addCategoryDialog.value = false
      } finally {
        addingCategory.value = false
      }
    }

    function clearAddCategoryDialog () {
      addCategoryName.value = null
      addCategoryDescription.value = null
    }

    onMounted(async () => {
      await loadCategories()
    })

    watch(addCategoryDialog, (newValue) => {
      if (!newValue) clearAddCategoryDialog()
    })

    return {
      loadCategories,
      onCreateNewCategory,
      clearAddCategoryDialog,
      addCategoryDialog,
      addCategoryName,
      addCategoryDescription,
      addingCategory,
      categories,
      search
    }
  }
}
</script>

<template>
  <q-page padding>
    <div class="row">
      <div class="col-grow">
      <q-input dense v-model="search" label="Search">
        <template v-slot:prepend>
          <q-icon name="search" />
        </template>
      </q-input>
      </div>
      <div class="col-atuo">
        <q-btn round color="primary" class="q-ml-md" icon="add" @click="addCategoryDialog = true"/>
      </div>
    </div>

    <q-space/>

    <q-list separator padding>
      <q-item
        v-for="c in categories" :key="c.id"
        clickable
        :to="{ name: 'stock-category', params: { id: c.id }}"
      >
        <q-item-section
          avatar
        >
          <q-icon v-if="c.icon" :name="c.icon" />
        </q-item-section>

        <q-item-section>
          <q-item-label>{{ c.name }}</q-item-label>
          <q-item-label caption>
            {{ c.description }}
          </q-item-label>
        </q-item-section>
      </q-item>
      </q-list>

    <q-dialog v-model="addCategoryDialog" position="top">
      <q-card style="width: 700px; max-width: 90vw;">
        <q-form @submit.prevent.stop="onCreateNewCategory">
          <q-card-section class="bg-primary text-white">
            <div class="text-h6">Add Stock Category</div>
          </q-card-section>
          <q-card-section>
            <q-input dense v-model="addCategoryName" :rules="[val => !!val || 'Name is required']" label="Name">
            </q-input>
            <q-input dense v-model="addCategoryDescription" label="Description (optional)" autogrow>
            </q-input>
          </q-card-section>
          <q-card-actions align="right" class="bg-white text-primary">
            <q-btn color="primary" outline label="Cancel" @click="addCategoryDialog = false"/>
            <q-btn type="submit" color="primary" label="OK" :loading="addingCategory"/>
          </q-card-actions>
        </q-form>
      </q-card>
    </q-dialog>
  </q-page>
</template>
