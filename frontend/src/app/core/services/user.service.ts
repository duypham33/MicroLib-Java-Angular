import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { getReq, postReq } from "../utils/callAPI.utils";



@Injectable({
    providedIn: 'root'
  })
  
  
  export class UserService {
    
    constructor(private http:HttpClient) { }
  
    login(user:any){
      
        return postReq(this.http, `users/login`, user);
      
    }

    validateToken(){
      return getReq(this.http, `users/validateToken`);
    }
  }
  