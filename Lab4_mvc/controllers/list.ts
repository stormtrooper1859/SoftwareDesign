import express from 'express'
import { TodoListHandler } from '../model'
import { storageToList } from '../mappings/storageToIndex'

const get_routes = (todo_list: TodoListHandler) => {
    const router = express.Router();

    router.get('/:id', async (req, res, next) => {
        const listName = req.params.id;

        res.render('list', await storageToList(todo_list, listName));
    })

    router.post('/:id/add', async (req, res, next) => {
        const listName = req.params.id;
        const text = req.body.text;

        console.log('wtg')
        console.log(listName, text)

        try {
            await todo_list.addItemToList(listName, text);
    
            res.send('OK')
        } catch(e) {
            console.error(e)
        }

        // res.render('index', storageToList(todo_list, listName));
    })

    router.delete('/:id/remove', async (req, res, next) => {
        const listName = req.params.id;

        try {
            const text = req.body.text;
            await todo_list.removeItemFromList(listName, text);
    
            res.send('OK')
        } catch(e) {
            console.error(e)
        }

        // res.render('list', storageToList(todo_list, listName));
    })

    router.put('/:id/mark', async (req, res, next) => {
        const listName = req.params.id;

        try {
            const text = req.body.text;
            await todo_list.markItemIsDone(listName, text);
    
            res.send('OK')
        } catch(e) {
            console.error(e)
        }

        // res.render('list', storageToList(todo_list, listName));
    })

    router.put('/:id/unmark', async (req, res, next) => {
        const listName = req.params.id;

        try {
            const text = req.body.text;
            await todo_list.markItemIsUndone(listName, text);
    
            res.send('OK')
        } catch(e) {
            console.error(e)
        }

        // res.render('list', storageToList(todo_list, listName));
    })

    return router;
}

export default get_routes;
