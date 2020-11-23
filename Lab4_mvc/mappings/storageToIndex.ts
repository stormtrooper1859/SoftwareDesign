import { TodoListHandler } from '../model'

interface IndexData {
    lists: {
        id: string,
        name: string,
    }[]
}

interface ListsData {
    name: string;
    items: string[]
}

export async function storageToIndex(store: TodoListHandler): Promise<IndexData> {
    const t = await store.getAllLists();
    store.getAllLists().then(console.log)
    const res =  {
        lists: t
    }

    console.log('storageToIndex', res)

    return res;
}

export async function storageToList(store: TodoListHandler, listId: string): Promise<ListsData> {
    const t = await store.getItemsOfList(listId);
    const t2 = await store.getListNameById(listId);
    const res =  {
        name: t2,
        items: t
    }

    console.log('storageToList', res)

    return res;
}
