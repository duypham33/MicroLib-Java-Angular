import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { UserService } from '../core/services/user.service';
import { reset } from '../core/store/loading/loading.actions';
import { loadingStatus } from '../core/store/loading/loading.selector';
import { loginUser } from '../core/store/user/user.actions';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})


export class AuthComponent implements OnInit {
  loginForm = {username: '', password: ''};

  constructor(private store:Store, private router:Router){}

  ngOnInit(): void {
    let loadingStatus$ = this.store.pipe(select(loadingStatus));
    loadingStatus$.subscribe(status => {
      if(status === "success")
        this.router.navigate(['/']);
      
      this.store.dispatch(reset());
    })
  }

  login():void {
    //this.userService.login({...this.loginForm});
    this.store.dispatch(loginUser({...this.loginForm}));
  }
}
