
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      {
        path: 'modules',
        component: () => import('pages/modules/Index.vue'),
        children: [
          {
            path: 'stock',
            component: () => import('pages/modules/stock/Index.vue'),
            children: [
              { path: 'categories', name: 'stock-categories', component: () => import('pages/modules/stock/Categories.vue') },
              { path: 'category/:id', name: 'stock-category', props: true, component: () => import('pages/modules/stock/Category.vue') },
              { path: 'items', name: 'stock-items', props: true, component: () => import('pages/modules/stock/Items.vue') }
            ]
          }
        ]
      }
    ]

  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
