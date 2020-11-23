import express from 'express'

import { Models } from '../model'
import { trySafety } from '../utils/ResponseUtils'
import { getListData } from '../views'

export const getListRoutes = (model: Models) => {
    const router = express.Router();
    const { todoList } = model;

    router.get('/:id', async (req, res, next) => {
        const listName = req.params.id;

        res.render('list', await getListData(model, listName));
    })

    router.post('/:id/add', async (req, res, next) => {
        const listName = req.params.id;
        const text = req.body.text;

        await trySafety(res, async () => {
            await todoList.addItemToList(listName, text);
        })
    })

    router.delete('/:id/remove', async (req, res, next) => {
        const listName = req.params.id;
        const text = req.body.text;

        await trySafety(res, async () => {
            await todoList.removeItemFromList(listName, text);
        })
    })

    router.put('/:id/mark', async (req, res, next) => {
        const listName = req.params.id;
        const text = req.body.text;

        await trySafety(res, async () => {
            await todoList.markItemIsDone(listName, text);
        });
    })

    router.put('/:id/unmark', async (req, res, next) => {
        const listName = req.params.id;
        const text = req.body.text;

        await trySafety(res, async () => {
            await todoList.markItemIsUndone(listName, text);
        });
    })

    return router;
}
