import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { borrowBookAPI, getBooksAPI } from 'src/app/core/store/books/books.actions';
import { selectBooks } from 'src/app/core/store/books/books.selector';
import { userSelector } from 'src/app/core/store/user/user.selector';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {
  books$ = this.store.pipe(select(selectBooks));
  user$ = this.store.pipe(select(userSelector));
  isLogining:boolean = false;
  employeeId:string = "";

  constructor(private store:Store, private router:Router){
    // console.log("Fetch!");
    // this.store.dispatch(invokeBooksAPI());
  }

  ngOnInit(): void {
    //console.log("Fetch!");
    this.store.dispatch(getBooksAPI());

    this.user$.subscribe(user => {
      if(user === null){
        this.isLogining = false;
        this.employeeId = "";
      }
      else{
        this.isLogining = true;
        this.employeeId = user.employeeId;
      }
        
    });

    if(!this.isLogining)
      this.router.navigate(["/login"]);
  }


  borrow(bookId:string){
    this.store.dispatch(borrowBookAPI({bookId: bookId, employeeId: this.employeeId}));
  }
}
