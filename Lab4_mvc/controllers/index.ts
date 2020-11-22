import express from 'express'
import { ITODOList, TODOList } from '../model/TODOList'
import { storageToIndex } from '../mappings/storageToIndex'

const get_routes = (todo_list: TODOList) => {

    const router = express.Router();

    router.get('/', (req, res, next) => {
        res.render('index', storageToIndex(todo_list));
    })

    router.post('/add', (req, res, next) => {
        const listName = req.body.name;
        todo_list.createList(listName)

        res.send('OK')
    })

    router.delete('/remove', (req, res, next) => {
        const listName = req.body.name;
        todo_list.removeList(listName)

        res.send('OK')
    })

    // router.get('/remove', (req, res, next) => {
    //     const listName = req.params.id;
    //     // console.log('ID:', req.params.id);
    //     // console.log(req)
    //     // storage.todo_list.push("/add todo")
        
    //     // const data = storage.lists.find(e => e.name === listName)

    //     res.render('list', storageToList(todo_list, listName));
    // })

    return router;
}

export default get_routes;
