const path = require('path'); //node에서 경로를 쉽게 조작하도록 설정
const HtmlWebpackPlugin = require('html-webpack-plugin'); // oupt을 prject

module.exports = {
    name: 'webpackConfiguration', //웹팩 설정 이름
    mode: 'development', // 실제 서비스를 배포하려면 'production' 으로 변경
    //context: path.resolve(__dirname, 'frontend'), // 앞서 생성한 모듈 파일의 디렉토리를 의미
    devtool: 'eval', // 디버깅 프로세스를 향상시키기 위한 옵션 (빌드 및 재구성 속도) 참조 : https://webpack.js.org/configuration/devtool/#root
    resolve: { //모듈의 해결 방법을 구성
        extensions: ['.js', '.jsx'] //모듈중 loader가 파일을 불러올 때 확장자를 나열된 확장자 옵션으로 가져온다.
    },
    module: { //모듈들을 처리하는 방법을 결정
        rules: [{  // .js나  .jsx라는 파일을 babel-loader를 가지고 options을 적용 한다라는 규칙.
            test: /\.jsx?/, //.js나 .jsx라는 파일에 적용
            loader: 'babel-loader', // babel을 webpack에서 사용할 수 있게
            options: {
                presets: ['@babel/preset-env', '@babel/preset-react'],  //preset-env 최신문법(es6) -> 옛날 문법(es5 이하)으로 변환, preset-react jsx를 js로 바꿔주는 용도 
                plugins: ['@babel/plugin-proposal-class-properties', 'react-hot-loader/babel']
            }
        }]
    },
    entry: { //app.js
        app: path.join(__dirname, 'src/index')
    },
    output: { //path라는 경로에  filename으로 build 파일을 출력
        path: path.join(__dirname, '..', 'src/main/resources/static/built'),
        filename: 'bundle.js'
    },
    plugins:[new htmlWebpackPlugin()]
};