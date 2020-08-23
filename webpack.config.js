const path = require('path');
const { VueLoaderPlugin } = require('vue-loader')
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: {
    main: './frontend/src/main.js'
  },
  output: {
    filename: '[name].js',
    path: path.resolve('./frontend/dist'),
  },
  devServer: {
    proxy: {
        '/api': {
            "target": 'http://localhost:8080',
            "pathRewrite": {'^/api' : ''},
            "changeOrigin": true,
            "secure": false
        }
    },
     historyApiFallback: true,
    
  },
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
    }
  },  
  module: {
    rules: [
    {
      test: /\.s[ac]ss$/i,
      use: [
        // Creates `style` nodes from JS strings
        'style-loader',
        // Translates CSS into CommonJS
        'css-loader',
        // Compiles Sass to CSS
        'sass-loader',
      ],
    },{
      test: /\.vue$/,
      use: 'vue-loader'
  },],
},
plugins: [
  
  new VueLoaderPlugin(),
  new HtmlWebpackPlugin({
    template: './frontend/src/index.html',
  }),
]
}