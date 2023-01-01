import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { EMPTY } from "rxjs";
import { Book } from "../models/book";
import { getReq, postReq, putReq } from "../utils/callAPI.utils";



@Injectable({
    providedIn: 'root'
  })
  
  
  export class BooksService {
    
    constructor(private http:HttpClient) { }
  
    get(){
      //return this.http.get<Book[]>('http://localhost:3000/books');
      return getReq(this.http, "books");
    }

    getDetail(id:string|null){
      //return this.http.get<Book[]>('http://localhost:3000/books');
      if(id !== null)
        return getReq(this.http, `books/${id}`);
      return EMPTY;
    }
  
    add(payload:any){
      //return this.http.post<Book>('http://localhost:3000/books', payload);
      return postReq(this.http, "books", payload);
    }
  
    update(payload:Book){
      //return this.http.put<Book>(`http://localhost:3000/books/${payload.id}`, payload);
      return putReq(this.http, `books/${payload.id}`, payload);
    }

    getMyBooks(employeeId:string|undefined){
      if(employeeId)
        return getReq(this.http, `employees/${employeeId}/books`);

      return EMPTY;
    }

    borrowBook(payload:any){
      //return postReq(this.http, `borrowing/${payload.bookId}`, payload);
      return postReq(this.http, `borrowing`, payload);
    }

    returnBook(payload:any){
      //return putReq(this.http, `mybooks/${payload.bookId}`, payload);
      return putReq(this.http, `borrowing`, payload);
    }
  }
  