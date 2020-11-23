const textField = document.getElementById('text2');
const button = document.getElementById('btn2');


const fn = () => {
    const data = {
        text: textField.value
    }

    // console.log(data)

    // const url2 = url + '?name=' + data.name

    // let xhr = new XMLHttpRequest();

    // xhr.open('POST', "./add");

    // xhr.send([JSON.stringify(data)]);

    // xhr.onload = function() {
    //     console.log('smth')
    // };

    const url = document.location.href;

    fetch(url + "/add", {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
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
        // body: JSON.stringify(data)
    })
    .then((response) => {
        location.reload();
        // return response.json();
    })
}
  // .then((data) => {
  //   console.log(data);
  // });

button.addEventListener('click', fn)

