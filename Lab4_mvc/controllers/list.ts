import express from 'express'
import { ITODOList, TODOList } from '../model/TODOList'
import { storageToList } from '../mappings/storageToIndex'

const get_routes = (todo_list: TODOList) => {
    const router = express.Router();

    router.get('/:id', (req, res, next) => {
        const listName = req.params.id;

        res.render('list', storageToList(todo_list, listName));
    })

    router.post('/:id/add', (req, res, next) => {
        const listName = req.params.id;

        todo_list.addItemToList(listName, 'item' + Date.now());

        res.send('OK')
        // res.render('index', storageToList(todo_list, listName));
    })

    router.delete('/:id/remove', (req, res, next) => {
        const listName = req.params.id;

        res.send('OK')
        // res.render('list', storageToList(todo_list, listName));
    })

    router.put('/:id/mark', (req, res, next) => {
        const listName = req.params.id;

        console.log('unrealized method mark')

        res.send('OK')
        // res.render('list', storageToList(todo_list, listName));
    })

    router.put('/:id/unmark', (req, res, next) => {
        const listName = req.params.id;

        console.log('unrealized method unmark')

        res.send('OK')
        // res.render('list', storageToList(todo_list, listName));
    })

    return router;
}

export default get_routes;
