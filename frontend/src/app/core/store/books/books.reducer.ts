import { createReducer, on } from "@ngrx/store";
import { Book } from "../../models/book";
import {bookListAPISuccess, addBookAPISuccess, updateBookAPISuccess, getMyBooksAPISuccess, borrowBookAPISuccess, returnBookAPISuccess} from "./books.actions";

export interface BooksState{
    bookList: Book[],
    myBooks: Book[]
}

const initialState : BooksState = {bookList: [], myBooks: []};

export const booksReducer = createReducer(
    initialState,
    on(bookListAPISuccess, (state, {bookList}) => {
        return {...state, bookList};
    }),
    on(addBookAPISuccess, (state, {book}) => {
        return {...state, bookList: [book, ...state.bookList]};
    }),
    on(updateBookAPISuccess, (state, {book}) => {
        return {...state, bookList: state.bookList.map(b => b.id !== book.id ? b : book)};
    }),
    on(getMyBooksAPISuccess, (state, {myBooks}) => {
        return {...state, myBooks};
    }),
    on(borrowBookAPISuccess, (state, {book}) => {
        return {
            bookList: state.bookList.filter(b => b.id !== book.id), 
            myBooks: [book, ...state.myBooks]
        }
    }),
    on(returnBookAPISuccess, (state, {book}) => {
        return {
            bookList: [book, ...state.bookList],
            myBooks: state.myBooks.filter(b => b.id !== book.id)
        }
    })
);