import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { GroupPermissionRoute } from './group_permissions.route';
import { GroupPermissionsComponent } from './list/group_permissions.component';

@NgModule({
  imports: [SharedModule,RouterModule.forChild(GroupPermissionRoute)],
  declarations: [
    GroupPermissionsComponent,
  ]
})
export class GroupPermissionModule {}
