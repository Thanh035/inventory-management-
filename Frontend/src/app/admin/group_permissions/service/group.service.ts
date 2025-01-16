import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GroupDTO } from '../group_permissions.model';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/groups');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }
  query(): Observable<HttpResponse<GroupDTO[]>> {
    return this.http.get<GroupDTO[]>(this.resourceUrl, { observe: 'response' });
  }
}
