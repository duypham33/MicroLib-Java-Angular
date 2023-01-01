import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { switchMap } from 'rxjs';
import { Book } from 'src/app/core/models/book';
import { BooksService } from 'src/app/core/services/books.service';
import { updateBookAPI } from 'src/app/core/store/books/books.actions';
import { selectBookById } from 'src/app/core/store/books/books.selector';
import { reset } from 'src/app/core/store/loading/loading.actions';
import { loadingStatus } from 'src/app/core/store/loading/loading.selector';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})


export class UpdateComponent implements OnInit {
  bookForm:Book = {id: "", name: "", author: "", active: true};

  constructor(private store:Store, 
    private route:ActivatedRoute, 
    private router:Router,
    private booksService:BooksService) {}

  ngOnInit():void{
    let data$ = this.route.paramMap.pipe(switchMap(param => {
      let id = param.get('id');
      //return this.store.pipe(select(selectBookById(id)));
      return this.booksService.getDetail(id);
    }));
    
    data$.subscribe(bookDetail => {
      if(bookDetail)
        this.bookForm = {...bookDetail};
      else
        this.router.navigate(['/']);
    });


    let loadingStatus$ = this.store.pipe(select(loadingStatus));
    loadingStatus$.subscribe(status => {
      if(status === "success")
        this.router.navigate(['/']);
      
      this.store.dispatch(reset());
    });
  }

  update():void{
    this.store.dispatch(updateBookAPI({payload: {...this.bookForm}}));
  }
}
