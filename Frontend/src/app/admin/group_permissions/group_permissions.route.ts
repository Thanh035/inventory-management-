import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { GroupService } from './service/group.service';
import { GroupPermissionsComponent } from './list/group_permissions.component';

@Injectable({ providedIn: 'root' })
export class GroupPermissionResolve  {
  constructor(private service: GroupService) {}
}

export const GroupPermissionRoute: Routes = [
  {
    path: '',
    component: GroupPermissionsComponent
  }
];
