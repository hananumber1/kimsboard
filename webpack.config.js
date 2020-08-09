const path = require('path');
const { VueLoaderPlugin } = require('vue-loader')

module.exports = {
  mode: 'development',
  entry: {
    main: './frontend/src/main.js'
  },
  output: {
    filename: '[name].js',
    path: path.resolve('./frontend/dist'),
  },
  module: {
    rules: [{
        test: /\.vue$/,
        use: 'vue-loader'
    }]
},
plugins: [
  
  new VueLoaderPlugin()
]
}