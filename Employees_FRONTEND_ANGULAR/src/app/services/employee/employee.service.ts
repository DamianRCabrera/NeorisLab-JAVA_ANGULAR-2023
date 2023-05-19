import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8080/empleado';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private _http: HttpClient) {

  }

  addEmployee(data: any) : Observable<any>{
    return this._http.post(BASE_URL, data);
  }

  updateEmployee(id: number, data: any) : Observable<any>{
    return this._http.put(`${BASE_URL}/${id}`, data);
  }

  getEmployees() : Observable<any>{
    return this._http.get(BASE_URL);
  }

  deleteEmployee(id: number) : Observable<any>{
    return this._http.delete(`${BASE_URL}/${id}`);
  }
}
