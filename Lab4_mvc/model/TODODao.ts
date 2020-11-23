import { Database } from 'sqlite3'

import { TodoListHandler, ListsRow } from '.'

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


function dbRun(db: Database, query: string): Promise<void> {
    const promise = new Promise<void>((accept, reject) => {
        db.run(query, function(err){
            if (err) {
                reject();
                // console.log(err)
            } else {
                accept();
                // console.log('no error')
            }
        });
    });
    return promise;
}

function dbRunS(db: Database, query: string): Promise<string> {
    const promise = new Promise<string>((accept, reject) => {
        db.run(query, function(err){
            if (err) {
                reject();
                // console.log(err)
            } else {
                accept('' + this.lastID);
                // console.log('no error')
            }
        });
    });
    return promise;
}

function dbGet<T>(db: Database, query: string): Promise<T> {
    const promise = new Promise<T>((accept, reject) => {
        db.get(query, (err, row) => {
            if (err) {
                reject();
            }
            accept(row);
        });
    });
    return promise;
}

function dbAll<T>(db: Database, query: string): Promise<T[]> {
    const promise = new Promise<T[]>((accept, reject) => {
        db.all(query, (err, row) => {
            if (err) {
                reject();
            }
            accept(row);
        });
    });
    return promise;
}

interface ItemsRow {
    id: string;
    data: string;
    list_key: string;
    marked: string;
}

export class TodoDAO implements TodoListHandler {
    private db: Database;

    constructor(path: string) {
        this.db = new Database('./todo.db');
        this.dbInit();
    };

    private dbInit() {
        this.db.serialize(() => {
            this.db.run(`create table if not exists lists (
                    id integer primary key autoincrement not null,
                    name text not null
                )`, (err) => {
                if (err) {
                    console.log(err)
                }
            });
            this.db.run(`create table if not exists items (
                    id integer primary key autoincrement not null,
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

    public async createList(name: string): Promise<string> {
        const query: string = await dbRunS(this.db, `insert into lists (name) values ("${name}");`);
        // console.log(query)
        return query;
    };

    public async removeList(id: string): Promise<void> {
        return await dbRun(this.db, `delete from lists where id="${id}";`);
    };

    public async addItemToList(listId: string, itemData: string): Promise<string> {
        const str = `insert into items (list_key, data, marked) values ("${listId}", "${itemData}", false);`
        console.log(str)
        const query: string = await dbRunS(this.db, str);
        // console.log(query)
        return query;
    };

    public async removeItemFromList(listId: string, itemId: string): Promise<void> {
        return await dbRun(this.db, `delete from lists where id="${itemId}";`);
    };

    public async markItemIsDone(listId: string, itemId: string): Promise<void> {
        return await dbRun(this.db, `update items set marked=true where id="${itemId}";`);
    };

    public async markItemIsUndone(listId: string, itemId: string): Promise<void> {
        return await dbRun(this.db, `update items set marked=false where id="${itemId}";`);
    };

    public async getAllLists(): Promise<ListsRow[]> {
        const query: ListsRow[] = await dbAll(this.db, `select * from lists`);
        console.log(query)
        // return query.(e => e.name);
        return query;
    };

    public async getItemsOfList(listId: string): Promise<string[]> {
        const query: ItemsRow[] = await dbAll(this.db, `select * from items where list_key="${listId}"`);
        console.log(query)
        return query.map(e => e.data);
    };

    public async getListNameById(listId: string): Promise<string> {
        const query: ListsRow = await dbGet(this.db, `select * from lists where id="${listId}"`);
        console.log(query)
        // return query.map(e => e.name);
        return query.name;
    };
}
