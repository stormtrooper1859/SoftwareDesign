import { TODOList } from '../model/TODOList'

export const storageToIndex = (store: TODOList) => {
    const res =  {
        lists: Array.from(store.storage.lists.keys())
    }

    console.log('storageToIndex', res)

    return res;
}

export const storageToList = (store: TODOList, listName: string) => {
    const res =  {
        name: listName,
        items: Array.from(store.storage.lists.get(listName))
    }

    console.log('storageToList', res)

    return res;
}
