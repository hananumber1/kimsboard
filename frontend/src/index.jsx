import React from 'react';
import ReactDOM from 'react-dom';
import {hot} from 'react-hot-loader/root';
import Root from './components/Root';
//필요한 css도 불러오세요

const Hot = hot(Root);

ReactDOM.render(<Root />, document.querySelector('#root'));

