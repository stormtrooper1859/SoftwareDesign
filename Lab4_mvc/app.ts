import express from 'express'
import path from 'path'
import bodyParser from 'body-parser'

import { TodoDAO } from './src/model/TodoList/TODODao'
import { Models } from './src/model'
import { getIndexRoutes } from './src/controllers/indexPage'
import { getListRoutes } from './src/controllers/listPage'

const app = express()

app.use(bodyParser.json());

app.set('views', path.join(__dirname, './page/templates'));
app.set('view engine', 'hbs');

app.use(express.static(path.join(__dirname, '/page/resources')))

const list: Models = {
    todoList: new TodoDAO('./todo.db')
}

app.use('/', getIndexRoutes(list))
app.use('/lists', getListRoutes(list))

console.log('started')

app.listen(3000)
