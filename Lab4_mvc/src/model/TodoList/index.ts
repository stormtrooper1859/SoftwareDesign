export interface ListsRow {
    id: string;
    name: string;
}

export interface ItemsRow {
    id: string;
    data: string;
    list_key: string;
    marked: string;
}

export interface TodoListHandler {
    createList : (name: string) => Promise<string>;
    removeList : (id: string) => Promise<void>;
    addItemToList : (listId: string, itemData: string) => Promise<string>;
    removeItemFromList : (listId: string, itemId: string) => Promise<void>;
    markItemIsDone : (listId: string, itemId: string) => Promise<void>;
    markItemIsUndone : (listId: string, itemId: string) => Promise<void>;
    getAllLists : () => Promise<ListsRow[]>;
    getItemsOfList : (listId: string) => Promise<ItemsRow[]>;
    getListNameById : (listId: string) => Promise<string>;
}
