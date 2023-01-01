import { HttpClient, HttpHeaders } from "@angular/common/http";

const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem("token")}`
});



export const getReq = (http:HttpClient, path:string) => {
    return http.get<any>(`http://localhost:3000/api/${path}`, {
        headers: headers
    });
}


export const postReq = (http:HttpClient, path:string, payload:any) => {
    
    return http.post<any>(`http://localhost:3000/api/${path}`, payload, {
        headers: headers
    });
}


export const putReq = (http:HttpClient, path:string, payload:any) => {
    
    return http.put<any>(`http://localhost:3000/api/${path}`, payload, {
        headers: headers
    });
}


