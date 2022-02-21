import { CustomHttpResponse } from './../model/custom-http-response';
import { TodoItem } from './../model/todoItem';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class TodoItemService {
  private host = environment.apiUrl;

  constructor(private http: HttpClient) {}

  public findById(idTodoItem: string): Observable<TodoItem> {
    return this.http.get<TodoItem>(
      `${this.host}/todoItem/findById/${idTodoItem}`
    );
  }

  public listAll(): Observable<TodoItem[]> {
    return this.http.get<TodoItem[]>(`${this.host}/todoItem/listAll`);
  }

  public listAllByItemGroup(idItemGroup: string): Observable<TodoItem[]> {
    return this.http.get<TodoItem[]>(
      `${this.host}/todoItem/listAllByItemGroup/${idItemGroup}`
    );
  }

  public save(formData: FormData): Observable<TodoItem> {
    return this.http.post<TodoItem>(
      `${this.host}/todoItem/saveByForm`,
      formData
    );
  }

  public delete(idTodoItem: bigint): Observable<CustomHttpResponse> {
    return this.http.delete<CustomHttpResponse>(
      `${this.host}/todoItem/deleteById/${idTodoItem}`
    );
  }

  public createTodoItemSaveFormData(
    idItemGroup: string,
    idTodoItem: string,
    todoItem: TodoItem
  ): FormData {
    const formData = new FormData();
    if (idTodoItem != null) {
      formData.append('idTodoItem', idTodoItem);
      formData.append('isDone', todoItem.isDone.toString());
    }
    formData.append('idItemGroup', idItemGroup);
    formData.append('descTodoItem', todoItem.descTodoItem);
    return formData;
  }
}
