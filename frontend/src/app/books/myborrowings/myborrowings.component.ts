import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { getMyBooksAPI, returnBookAPI } from 'src/app/core/store/books/books.actions';
import { selectMyBooks } from 'src/app/core/store/books/books.selector';

@Component({
  selector: 'app-myborrowings',
  templateUrl: './myborrowings.component.html',
  styleUrls: ['./myborrowings.component.css']
})


export class MyborrowingsComponent implements OnInit {
  mybooks$ = this.store.pipe(select(selectMyBooks));

  constructor(private store:Store){}

  ngOnInit(): void {
    this.store.dispatch(getMyBooksAPI());
  }


  return(bookId:string){
    this.store.dispatch(returnBookAPI({bookId: bookId}));
  }
}
