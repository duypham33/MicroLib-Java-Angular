import { createAction, props } from "@ngrx/store";
import { Book } from "../../models/book";


export const getBooksAPI = createAction(
    "[Books API] fetch books API", 
);

export const bookListAPISuccess = createAction(
    "[Books API] fetch books api success",
    props<{bookList:Book[]}>()
);


export const addBookAPI = createAction(
    "[Books API] add book API",
    props<{payload:any}>()
);


export const addBookAPISuccess = createAction(
    "[Books API] add book api success",
    props<{book:Book}>()
);

export const updateBookAPI = createAction(
    "[Books API] update book API",
    props<{payload:Book}>()
);

export const updateBookAPISuccess = createAction(
    "[Books API] update book api success",
    props<{book:Book}>()
);



export const getMyBooksAPI = createAction(
    "[Books API] fetch my books API", 
);

export const getMyBooksAPISuccess = createAction(
    "[Books API] fetch my books api success",
    props<{myBooks:Book[]}>()
);


export const borrowBookAPI = createAction(
    "[Books API] borrow book api",
    props<{bookId:string, employeeId:string}>()
);

export const borrowBookAPISuccess = createAction(
    "[Books API] borrow book api success",
    props<{book:Book}>()
);


export const returnBookAPI = createAction(
    "[Books API] return book api",
    props<{bookId:string}>()
);

export const returnBookAPISuccess = createAction(
    "[Books API] return book api success",
    props<{book:Book}>()
);