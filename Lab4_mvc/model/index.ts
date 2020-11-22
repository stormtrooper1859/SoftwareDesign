export interface TodoListHandler {
    createList : (name: string) => string;
    removeList : (id: string) => void;
    addItemToList : (listId: string, itemData: string) => string;
    removeItemFromList : (listId: string, itemId: string) => void;
    markItemIsDone : (listId: string, itemId: string) => void;
    markItemIsUndone : (listId: string, itemId: string) => void;
    getAllLists : () => string[];
    getItemsOfList : (listId: string) => string[];
}
