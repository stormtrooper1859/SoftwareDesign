import express from 'express'
import path from 'path'
import { TODOList } from './model/TODOList'
import bodyParser from 'body-parser'

import indexRoute from './controllers/index'
import listRoute from './controllers/list'

const app = express()

app.use(bodyParser.json());

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(express.static(path.join(__dirname, 'public')))

const list = new TODOList();

app.use('/', indexRoute(list))
app.use('/lists', listRoute(list))

console.log('started')

app.listen(3000)
