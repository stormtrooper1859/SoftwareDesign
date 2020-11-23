import express from 'express'
import { TodoListHandler } from '../model'
import { storageToIndex } from '../mappings/storageToIndex'

const get_routes = (todo_list: TodoListHandler) => {

    const router = express.Router();

    router.get('/', async (req, res, next) => {    
        res.render('index', await storageToIndex(todo_list));
    })

    router.post('/add', async (req, res, next) => {
        try {
            const listName = req.body.name;
            await todo_list.createList(listName)
    
            res.send('OK')
        } catch(e) {
            console.error(e)
        }
    })

    router.delete('/remove', async (req, res, next) => {
        
        try {
            const listName = req.body.name;
            await todo_list.removeList(listName)
    
            res.send('OK')
        } catch(e) {
            console.error(e)
        }
        // const listName = req.body.name;
        // todo_list.removeList(listName)

        // res.send('OK')
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
