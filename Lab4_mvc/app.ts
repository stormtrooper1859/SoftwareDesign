// import { Database } from 'sqlite3'

// // import { TodoListHandler } from '.'

// const db = new Database('./todo.db');

// db.serialize(() => {
//     // db.get("create table if not exists lists (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NAME TEXT NOT NULL)", (err) => {
//     //     if (err) {
//     //         console.log(err)
//     //     } else {
//     //         console.log('no error')
//     //     }
//     // });
//     // db.run(`create table if not exists items (
//     //         id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//     //         data TEXT NOT NULL,
//     //         list_key INTEGER NOT NULL,
//     //         marked boolean not null
//     //     )`, (err) => {
//     //     if (err) {
//     //         console.log(err)
//     //     } else {
//     //         console.log('no error')
//     //     }
//     // });
//     // db.get(`insert into lists (name) values ("kek4"), ("list4"); select last_insert_rowid();`, (err, res) => {
//     //     console.log(err);
//     //     console.log(res);
//     //     // if (err) {
//     //     //     console.log(err)
//     //     // } else {
//     //     //     console.log('no error')
//     //     // }
//     // });
//     db.run(`insert into lists (name) values ("kek4"), ("list4"); select last_insert_rowid();`, function(err){
//         if(err){
//             console.log(err)
//         }else{
//             console.log(this.lastID);
//         }
//     });
//     // db.get(`select last_insert_rowid();`, (err, res) => {
//     //     console.log(err);
//     //     console.log(res);
//     //     // if (err) {
//     //     //     console.log(err)
//     //     // } else {
//     //     //     console.log('no error')
//     //     // }
//     // });
//     // db.run(`insert into items (list_key, data, marked) values
//     //         (1, "item1", false),
//     //         (2, "item2", false),
//     //         (2, "item3", false),
//     //         (1, "item4", false);
//     //     `, (err) => {
//     //     if (err) {
//     //         console.log(err)
//     //     } else {
//     //         console.log('no error')
//     //     }
//     // });
//     // db.all(`update items set marked=true where id=3;`, (err, value) => {
//     //     console.log(value);
//     //     if (err) {
//     //         console.log(err)
//     //     }
//     // });
//     // db.all(`delete from items where id=2;`, (err, value) => {
//     //     console.log(value);
//     //     if (err) {
//     //         console.log(err)
//     //     }
//     // });
//     // db.all(`select * from items where list_key="2";`, (err, value) => {
//     //     console.log(value);
//     //     if (err) {
//     //         console.log(err)
//     //     }
//     // });

//     // db.run(`drop table items`, (err) => {
//     //     if (err) {
//     //         console.log(err)
//     //     } else {
//     //         console.log('no error')
//     //     }
//     // })
// })


import express from 'express'
import path from 'path'
import { TodoDAO } from './model/TODODao'
import bodyParser from 'body-parser'

import indexRoute from './controllers/index'
import listRoute from './controllers/list'

const app = express()

app.use(bodyParser.json());

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(express.static(path.join(__dirname, 'public')))

const list = new TodoDAO('./todo.db');

app.use('/', indexRoute(list))
app.use('/lists', listRoute(list))

console.log('started')

app.listen(3000)
