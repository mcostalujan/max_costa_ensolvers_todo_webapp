import { ItemGroup } from '../model/itemGroup';
import { CustomHttpResponse } from '../model/custom-http-response';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ItemGroupService {
  private host = environment.apiUrl;

  constructor(private http: HttpClient) {}

  public findById(idItemGroup: string): Observable<ItemGroup> {
    return this.http.get<ItemGroup>(
      `${this.host}/itemGroup/findById/${idItemGroup}`
    );
  }

  public listAll(): Observable<ItemGroup[]> {
    return this.http.get<ItemGroup[]>(`${this.host}/itemGroup/listAll`);
  }

  public save(formData: FormData): Observable<ItemGroup> {
    return this.http.post<ItemGroup>(
      `${this.host}/itemGroup/saveByForm`,
      formData
    );
  }

  public delete(
    idItemGroup: bigint
  ): Observable<CustomHttpResponse> {
    return this.http.delete<CustomHttpResponse>(
      `${this.host}/itemGroup/deleteById/${idItemGroup}`
    );
  }

  public createItemGroupSaveFormData(
    idItemGroup: string,
    itemGroup: ItemGroup
  ): FormData {
    const formData = new FormData();
    if (idItemGroup != null) {
      formData.append('idItemGroup', idItemGroup);
    }
    formData.append('descItemGroup', itemGroup.descItemGroup);
    return formData;
  }
}
