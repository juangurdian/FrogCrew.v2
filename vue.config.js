const { defineConfig } = require('@vue/cli-service')
const { default: DefineOptions } = require('unplugin-vue-define-options/webpack')

module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    plugins: [
      DefineOptions()
    ]
  }
})
