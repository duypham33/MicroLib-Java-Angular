import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { addBookAPI } from 'src/app/core/store/books/books.actions';
import { reset } from 'src/app/core/store/loading/loading.actions';
import { loadingStatus } from 'src/app/core/store/loading/loading.selector';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})


export class AddComponent implements OnInit {
  bookForm = {name: "", author: "", active: true};

  constructor(private store:Store, private router:Router) {}

  ngOnInit(): void {
    let loadingStatus$ = this.store.pipe(select(loadingStatus));
    loadingStatus$.subscribe(status => {
      if(status === "success")
        this.router.navigate(['/']);
      
      this.store.dispatch(reset());
    })
  }

  add():void{
    this.store.dispatch(addBookAPI({payload: {...this.bookForm}}));
    // this.bookForm.name = "";
    // this.bookForm.author = "";
  }
}
