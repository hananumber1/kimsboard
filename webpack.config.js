const path = require('path');
const { VueLoaderPlugin } = require('vue-loader')
const HtmlWebpackPlugin = require('html-webpack-plugin');
// const autoprefixer = require('autoprefixer');
// const tailwindcss = require('tailwindcss');

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
      test: /\.(sass|css|scss)$/,
        use: [
          'style-loader',
          'css-loader',
          {
            loader: "postcss-loader",
            options: {
              plugins: () => [
                require("autoprefixer")()
              ],
            },
          },
          'sass-loader',
        ]
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
  // tailwindcss,
  //   autoprefixer,
  
]
}