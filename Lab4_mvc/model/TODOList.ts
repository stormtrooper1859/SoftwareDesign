import { TodoListHandler } from '.'

interface TodoItem {
    text: string,
    marked: boolean
}

interface TodoList {
    name: string,
    listId: string,
    items: TodoItem[]
}

interface TodoListStorage {
    lists: Map<string, Set<string>>
}

// export class TodoListModel implements TodoListHandler {
//     public storage: TodoListStorage;

//     private mapInit() {
//         const set1 = new Set<string>();
//         set1.add('list1-p1')
//         set1.add('list1-p2')
//         set1.add('list1-p3')
//         this.storage.lists.set('list1', set1);

//         const set2 = new Set<string>();
//         set2.add('list1-p3')
//         set2.add('list1-p5')
//         set2.add('list1-p6')
//         this.storage.lists.set('list2', set2);
//     }

//     constructor() {
//         console.log('called')
//         this.storage = {
//             lists: new Map()
//         };
//         this.mapInit();
//     };

//     public createList(name: string): void {
//         this.storage.lists.set(name, new Set())
//     };

//     public removeList(id: string): void {
//         this.storage.lists.delete(name)
//     };

//     public addItemToList(listId: string, itemData: string): void {
//         this.storage.lists.get(listName).add(itemData);
//     };

//     public removeItemFromList(listId: string, itemId: string): void {
//         this.storage.lists.get(listName).delete(itemData);
//     };

//     public markItemIsDone(listId: string, itemId: string): void {
//         // this.storage.lists.
//         this.storage.lists.push({name: pname, items: []})
//     };

//     public markItemIsUndone(listId: string, itemId: string): void {
//         // this.storage.lists.
//         this.storage.lists.push({name: pname, items: []})
//     };
// }

