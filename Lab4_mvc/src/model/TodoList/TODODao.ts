import { Database } from 'sqlite3'

import { dbRun, dbRunS, dbGet, dbAll } from '../../utils/DBUtils'
import { TodoListHandler, ListsRow, ItemsRow } from '../TODOList'


export class TodoDAO implements TodoListHandler {
    private db: Database;

    constructor(path: string) {
        this.db = new Database(path);
        this.dbInit();
    };

    private dbInit() {
        this.db.serialize(() => {
            this.db.run(`create table if not exists lists (
                    id integer primary key autoincrement not null,
                    name text not null
                )`, (err) => {
                if (err) {
                    console.error(err)
                }
            });
            this.db.run(`create table if not exists items (
                    id integer primary key autoincrement not null,
                    data text not null,
                    list_key integer not null,
                    marked boolean not null
                )`, (err) => {
                if (err) {
                    console.error(err)
                }
            });
        });
    }

    public async createList(name: string): Promise<string> {
        return await dbRunS(this.db, `insert into lists (name) values ("${name}");`);
    };

    public async removeList(id: string): Promise<void> {
        await dbRun(this.db, `delete from items where list_key="${id}";`);
        return await dbRun(this.db, `delete from lists where id="${id}";`);
    };

    public async addItemToList(listId: string, itemData: string): Promise<string> {
        return await dbRunS(this.db, `insert into items (list_key, data, marked) values ("${listId}", "${itemData}", false);`);
    };

    public async removeItemFromList(listId: string, itemId: string): Promise<void> {
        return await dbRun(this.db, `delete from items where id="${itemId}";`);
    };

    public async markItemIsDone(listId: string, itemId: string): Promise<void> {
        return await dbRun(this.db, `update items set marked=true where id="${itemId}";`);
    };

    public async markItemIsUndone(listId: string, itemId: string): Promise<void> {
        return await dbRun(this.db, `update items set marked=false where id="${itemId}";`);
    };

    public async getAllLists(): Promise<ListsRow[]> {
        return await dbAll(this.db, `select * from lists`);
    };

    public async getItemsOfList(listId: string): Promise<ItemsRow[]> {
        return await dbAll(this.db, `select * from items where list_key="${listId}"`);
    };

    public async getListNameById(listId: string): Promise<string> {
        const query: ListsRow = await dbGet(this.db, `select * from lists where id="${listId}"`);
        return query.name;
    };
}
