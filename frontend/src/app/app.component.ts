import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { bookListAPISuccess } from './core/store/books/books.actions';
import { loadingStatus } from './core/store/loading/loading.selector';
import { checkToken, resetUser } from './core/store/user/user.actions';
import { userSelector } from './core/store/user/user.selector';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'MicroLib';
  loadingStatus$ = this.store.pipe(select(loadingStatus));
  isLoading: boolean = false;
  user$ = this.store.pipe(select(userSelector));
  isLogining = false;

  constructor(private store:Store, private router:Router){}

  ngOnInit(): void {
    
    this.loadingStatus$.subscribe(status => {this.isLoading = status !== null && status === 'loading'})
    this.user$.subscribe(u => {

      if(u === null){
        if(localStorage.getItem("token") !== null)
          this.store.dispatch(checkToken());
        else
          this.router.navigate(['/login']);
      }

      else
        this.isLogining = true;
        
    });
  }


  logout(){
    localStorage.removeItem("token");
    this.store.dispatch(resetUser());
    this.isLogining = false;
    this.store.dispatch(bookListAPISuccess({bookList: []}));  //Reset
    //this.router.navigate(['/login']);
  }
}
