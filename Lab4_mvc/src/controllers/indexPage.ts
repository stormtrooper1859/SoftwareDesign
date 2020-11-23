import express from 'express'

import { Models } from '../model'
import { trySafety } from '../utils/ResponseUtils'
import { getIndexData } from '../views'

export const getIndexRoutes = (model: Models) => {
    
    const router = express.Router();

    const { todoList } = model;

    router.get('/', async (req, res, next) => {    
        res.render('index', await getIndexData(model));
    })

    router.post('/add', async (req, res, next) => {
        const listName = req.body.name;

        await trySafety(res, async () => {
            await todoList.createList(listName)
        })
    })

    router.delete('/remove', async (req, res, next) => {
        const listName = req.body.name;

        await trySafety(res, async () => {
            await todoList.removeList(listName)
        })
    })

    return router;
}
