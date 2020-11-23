export async function trySafety<T>(res: any, func: () => Promise<T>) {
    try {
        await func();

        res.send('OK')
    } catch(e) {
        console.error(e)
        res.status(500).send('Internal server error');
    }
}
