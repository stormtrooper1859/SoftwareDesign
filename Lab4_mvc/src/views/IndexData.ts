import { Models } from '../model'

interface IndexPageData {
    lists: {
        id: string,
        name: string,
    }[]
}

export async function getIndexData({ todoList }: Models): Promise<IndexPageData> {
    const lists = await todoList.getAllLists();
    const res =  {
        lists
    }

    // console.log('storageToIndex', res)

    return res;
}
