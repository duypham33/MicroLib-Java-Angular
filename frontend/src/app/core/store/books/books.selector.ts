import { createFeatureSelector, createSelector } from "@ngrx/store";
import { Book } from "../../models/book";
import { BooksState } from "./books.reducer";




export const selectBooksState = createFeatureSelector<BooksState>("books");


export const selectBooks = createSelector(selectBooksState, (state:BooksState) => state.bookList);

export const selectBookById = (id:string|null) => {
    return createSelector(selectBooks, (books:Book[]) => {
        return books.find(b => b.id === id);
    })
}


export const selectMyBooks = createSelector(selectBooksState, (state:BooksState) => state.myBooks);