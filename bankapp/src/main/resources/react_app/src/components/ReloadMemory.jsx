// reload or get data on first render/refresh
export function loadProp(data, itemName, defaultState) {
    const item = JSON.parse(localStorage.getItem(itemName)); // persisted data for refreshing
    if(item !== null) {
        return item;
    } else if (data !== null && data.length !== 0) {    // existing data on first load (can be empty array on reload)
        return data;
    }
    return defaultState;    // in case of data loss
}
