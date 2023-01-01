import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { select, Store } from "@ngrx/store";
import { catchError, delay, EMPTY, map, mergeMap, of, switchMap, tap, withLatestFrom } from "rxjs";
import { BooksService } from "../../services/books.service";
import { getResult, startLoading, reset } from "../loading/loading.actions";
import { userSelector } from "../user/user.selector";
import { addBookAPI, addBookAPISuccess, bookListAPISuccess, 
    getBooksAPI, getMyBooksAPI, updateBookAPI, updateBookAPISuccess, getMyBooksAPISuccess, borrowBookAPI, borrowBookAPISuccess, returnBookAPI, returnBookAPISuccess } from "./books.actions";
import {selectBooks, selectMyBooks} from "./books.selector";




@Injectable()
export class BooksEffects {

    constructor(private action$:Actions, 
        private booksService:BooksService,
        private store:Store){}

    loadAllBooks$ = createEffect(() => 
        this.action$.pipe(
            ofType(getBooksAPI),
            withLatestFrom(this.store.pipe(select(selectBooks))),
            switchMap(([action, bookList]) => {
                if(bookList.length > 0)
                    return EMPTY;  //Empty Observable
                
                this.store.dispatch(startLoading());  //Loading

                return this.booksService.get().pipe(
                    delay(1500),
                    map(books => {
                        this.store.dispatch(getResult({
                            status: "success",
                            message: "The books is loaded successfully!"
                        }));
                        return bookListAPISuccess({bookList: books});
                    }),
                    
                    catchError(err => of(getResult({
                        status: "error",
                        message: err.message
                    }))),

                    tap(() => this.store.dispatch(reset()))
                )
            })
        )
    );

    addNewBook$ = createEffect(() => 
        this.action$.pipe(
            ofType(addBookAPI),
            mergeMap(action => {

                this.store.dispatch(startLoading());  //Loading

                return this.booksService.add(action.payload).pipe(
                    
                    map(book => {
                        this.store.dispatch(getResult({
                            status: "success",
                            message: `The book ${book.name} is added successfully!`
                        }));

                        return addBookAPISuccess({book: book});
                    }),

                    catchError(err => of(getResult({
                        status: "error",
                        message: err.message
                    })))
                );
            })
        )
    );


    updateBook$ = createEffect(() => 
        this.action$.pipe(
            ofType(updateBookAPI),
            mergeMap(action => {
                this.store.dispatch(startLoading());  //Loading

                return this.booksService.update(action.payload).pipe(
                
                    map(book => {
                        
                        this.store.dispatch(getResult({
                            status: "success",
                            message: `The book is updated successfully!`
                        }));

                        return updateBookAPISuccess({book: book});
                    }),

                    catchError(err => of(getResult({
                        status: "error",
                        message: err.message
                    })))
                );
            })
        )
    );



    loadMyBooks$ = createEffect(() => 
        this.action$.pipe(
            ofType(getMyBooksAPI),
            withLatestFrom(this.store.pipe(select(selectMyBooks))),
            withLatestFrom(this.store.pipe(select(userSelector))),
            switchMap(([[action, myBooks], user]) => {
                if(myBooks.length > 0)
                    return EMPTY;  //Empty Observable
                
                this.store.dispatch(startLoading());  //Loading

                return this.booksService.getMyBooks(user?.employeeId).pipe(
                    delay(1500),
                    map(books => {
                        this.store.dispatch(getResult({
                            status: "success",
                            message: "The borrowings are loaded successfully!"
                        }));
                        return getMyBooksAPISuccess({myBooks: books});
                    }),
                    
                    catchError(err => of(getResult({
                        status: "error",
                        message: err.message
                    }))),

                    tap(() => this.store.dispatch(reset()))
                )
            })
        )
    );



    borrowBook$ = createEffect(() => 
        this.action$.pipe(
            ofType(borrowBookAPI),
            mergeMap(payload => {

                this.store.dispatch(startLoading());  //Loading

                return this.booksService.borrowBook(payload).pipe(
                    
                    map(book => {
                        this.store.dispatch(getResult({
                            status: "success",
                            message: `Borrow the book ${book.name} successfully!`
                        }));

                        return borrowBookAPISuccess({book: book});
                    }),

                    catchError(err => of(getResult({
                        status: "error",
                        message: err.message
                    }))),

                    tap(() => this.store.dispatch(reset()))
                );
            })
        )
    );


    returnBook$ = createEffect(() => 
        this.action$.pipe(
            ofType(returnBookAPI),
            withLatestFrom(this.store.pipe(select(userSelector))),
            mergeMap(([payload, user]) => {

                this.store.dispatch(startLoading());  //Loading

                return this.booksService.returnBook({...payload, employeeId: user?.employeeId}).pipe(
                    
                    map(book => {
                        this.store.dispatch(getResult({
                            status: "success",
                            message: `Return the book ${book.name} successfully!`
                        }));

                        return returnBookAPISuccess({book: book});
                    }),

                    catchError(err => of(getResult({
                        status: "error",
                        message: err.message
                    }))),

                    tap(() => this.store.dispatch(reset()))
                );
            })
        )
    );

}