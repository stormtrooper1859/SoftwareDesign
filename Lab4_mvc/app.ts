import express from 'express'
import path from 'path'

import indexRoute from './controllers/index'

const app = express()

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(express.static(path.join(__dirname, 'public')))

app.use('/', indexRoute)

console.log('started')

app.listen(3000)

