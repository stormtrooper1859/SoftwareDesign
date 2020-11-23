import { Database } from 'sqlite3';

export function dbRun(db: Database, query: string): Promise<void> {
    const promise = new Promise<void>((accept, reject) => {
        db.run(query, function(err){
            if (err) {
                reject(err);
            } else {
                accept();
            }
        });
    });
    return promise;
}

export function dbRunS(db: Database, query: string): Promise<string> {
    const promise = new Promise<string>((accept, reject) => {
        db.run(query, function(err){
            if (err) {
                reject(err);
            } else {
                accept('' + this.lastID);
            }
        });
    });
    return promise;
}

export function dbGet<T>(db: Database, query: string): Promise<T> {
    const promise = new Promise<T>((accept, reject) => {
        db.get(query, (err, row) => {
            if (err) {
                reject(err);
            }
            accept(row);
        });
    });
    return promise;
}

export function dbAll<T>(db: Database, query: string): Promise<T[]> {
    const promise = new Promise<T[]>((accept, reject) => {
        db.all(query, (err, row) => {
            if (err) {
                reject(err);
            }
            accept(row);
        });
    });
    return promise;
}
