import { Database } from 'sqlite3'

import { TodoListHandler } from '.'

// const db = new Database('./todo.db');

// db.serialize(() => {
//     db.run("create table if not exists lists (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NAME TEXT NOT NULL)", (err) => {
//         if (err) {
//             console.log(err)
//         } else {
//             console.log('no error')
//         }
//     });
//     db.run(`create table if not exists items (
//             id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//             data TEXT NOT NULL,
//             list_key INTEGER NOT NULL,
//             marked boolean not null
//         )`, (err) => {
//         if (err) {
//             console.log(err)
//         } else {
//             console.log('no error')
//         }
//     });
//     // db.run(`insert into lists (name) values ("kek"), ("list2")`, (err) => {
//     //     if (err) {
//     //         console.log(err)
//     //     } else {
//     //         console.log('no error')
//     //     }
//     // });
//     // db.run(`insert into items (list_key, data, marked) values
//     //         (1, "item1", false),
//     //         (2, "item2", false),
//     //         (2, "item3", false),
//     //         (1, "item4", false);
//     //     `, (err) => {
//     //     if (err) {
//     //         console.log(err)
//     //     } else {
//     //         console.log('no error')
//     //     }
//     // });
//     // db.all(`update items set marked=true where id=3;`, (err, value) => {
//     //     console.log(value);
//     //     if (err) {
//     //         console.log(err)
//     //     }
//     // });
//     db.all(`delete from items where id=2;`, (err, value) => {
//         console.log(value);
//         if (err) {
//             console.log(err)
//         }
//     });
//     db.all(`select * from items where list_key="2";`, (err, value) => {
//         console.log(value);
//         if (err) {
//             console.log(err)
//         }
//     });

//     // db.run(`drop table items`, (err) => {
//     //     if (err) {
//     //         console.log(err)
//     //     } else {
//     //         console.log('no error')
//     //     }
//     // })
// })

// db.serialize(() => {
//     db.run(`create table if not exists lists (
//             id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//             NAME TEXT NOT NULL
//         )`, (err) => {
//         if (err) {
//             console.log(err)
//         }
//     });
//     db.run(`create table if not exists items (
//             id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
//             data text not null,
//             list_key integer not null,
//             marked boolean not null
//         )`, (err) => {
//         if (err) {
//             console.log(err)
//         }
//     });
// })




export class TodoDAO implements TodoListHandler {
    private db: Database;

    constructor(path: string) {
        this.db = new Database('./todo.db');
        this.dbInit();
    };

    private dbInit() {
        this.db.serialize(() => {
            this.db.run(`create table if not exists lists (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    NAME TEXT NOT NULL
                )`, (err) => {
                if (err) {
                    console.log(err)
                }
            });
            this.db.run(`create table if not exists items (
                    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
                    data text not null,
                    list_key integer not null,
                    marked boolean not null
                )`, (err) => {
                if (err) {
                    console.log(err)
                }
            });
        });
    }

    public async createList(name: string): string {
        this.db.run(`insert into lists (name) values ("${name}")`, (err) => {
            if (err) {
                console.log(err)
            } else {
                console.log('no error')
            }
        });
    };

    // public removeList(id: string): void {
    //     this.storage.lists.delete(name)
    // };

    // public addItemToList(listId: string, itemData: string): void {
    //     this.storage.lists.get(listName).add(itemData);
    // };

    // public removeItemFromList(listId: string, itemId: string): void {
    //     this.storage.lists.get(listName).delete(itemData);
    // };

    // public markItemIsDone(listId: string, itemId: string): void {
    //     // this.storage.lists.
    //     this.storage.lists.push({name: pname, items: []})
    // };

    // public markItemIsUndone(listId: string, itemId: string): void {
    //     // this.storage.lists.
    //     this.storage.lists.push({name: pname, items: []})
    // };
}
