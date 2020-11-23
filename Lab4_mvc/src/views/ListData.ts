import { Models } from '../model'

interface ListPageData {
    name: string;
    items: {
        id: string;
        data: string;
        marked: string;
    }[]
}

export async function getListData({ todoList }: Models, listId: string): Promise<ListPageData> {
    const items = await todoList.getItemsOfList(listId);
    const listTitle = await todoList.getListNameById(listId);
    const clearedItems = items.map(({ list_key, ...rest }) => ({ ...rest }))
    const res = {
        name: listTitle,
        items: clearedItems
    }

    // console.log('storageToList', res)

    return res;
}
