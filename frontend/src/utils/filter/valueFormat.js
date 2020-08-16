//filter/valueFormat.js
//값이 null, undefined, '', 일 경우 false 값으로 return 

function valueFormat(value) {
    if(value === null || value ===undefined || value==='')
    return false
}

export default valueFormat