const request = (url, method, data) => fetch(url, {
  method, // *GET, POST, PUT, DELETE, etc.
  mode: 'cors', // no-cors, *cors, same-origin
  cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
  credentials: 'same-origin', // include, *same-origin, omit
  headers: {
    'Content-Type': 'application/json'
    // 'Content-Type': 'application/x-www-form-urlencoded',
  },
  redirect: 'follow', // manual, *follow, error
  referrerPolicy: 'no-referrer', // no-referrer, *client
  body: JSON.stringify(data) // body data type must match "Content-Type" header
});

const textField = document.getElementById('text_add');
const button = document.getElementById('button_add');


const fn = () => {
    const data = {
        text: textField.value
    }

    const url = document.location.href + '/add';

    request(url, 'POST', data).then(response => {
        location.reload();
    })
}

button.addEventListener('click', fn)





const removeAction = (id) => () => {
    const data = {
        text: id
    }

    const url = document.location.href + '/remove';

    request(url, 'DELETE', data).then(response => {
        location.reload();
    })
}



const removeButtons = document.querySelectorAll('.list-item__remove')

removeButtons.forEach((b) => {
  b.addEventListener('click', removeAction(b.dataset.id))
})





//--------
const markAction = (id, state) => () => {
    const data = {
        text: id
    }

    let pref = "mark"
    if (state !== "0") {
        pref = "unmark"
    }

    const url = document.location.href + '/' + pref;

    request(url, 'PUT', data).then(response => {
        location.reload();
    })
}


const markButtons = document.querySelectorAll('.list-item__link')

markButtons.forEach((b) => {
    b.addEventListener('click', markAction(b.dataset.id, b.dataset.marked))
})

