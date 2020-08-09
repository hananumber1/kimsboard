const path = require('path');

module.exports = {
  mode: 'development',
  entry: {
    main: './frontend/src/main.js'
  },
  output: {
    filename: '[name].js',
    path: path.resolve('./frontend/dist'),
  },
}