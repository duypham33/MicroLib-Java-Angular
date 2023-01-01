import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { catchError, map, mergeMap, of } from "rxjs";
import { UserService } from "../../services/user.service";
import { getResult, startLoading } from "../loading/loading.actions";
import { checkToken, loginUser, setUser } from "./user.actions";




@Injectable()
export class UserEffects {

    constructor(private action$:Actions, 
        private store:Store,
        private userService:UserService,
        private router:Router){}
    
    loginUser$ = createEffect(() => 
        this.action$.pipe(
            ofType(loginUser), 
            mergeMap(payload => {
                this.store.dispatch(startLoading());  //Loading

                return this.userService.login(payload).pipe(
                    
                    map(user => {
                        this.store.dispatch(getResult({
                            status: "success",
                            message: `Login successfully!`
                        }));

                        localStorage.setItem("token", user.token);
                        
                        return setUser({payload: user});
                    }),

                    catchError(err => of(getResult({
                        status: "error",
                        message: err.message
                    })))
                )
            })
        )
    )


    validateToken$ = createEffect(() => 
        this.action$.pipe(
            ofType(checkToken), 
            mergeMap(payload => {
                this.store.dispatch(startLoading());  //Loading

                return this.userService.validateToken().pipe(
                    
                    map(user => {
                        this.store.dispatch(getResult({
                            status: "success",
                            message: `Token is valid!`
                        }));

                        return setUser({payload: user});
                    }),

                    catchError(err => {
                        this.router.navigate(["/login"]);
                        
                        return of(getResult({
                            status: "error",
                            message: err.message
                        }));
                    })
                )
            })
        )
    )
}