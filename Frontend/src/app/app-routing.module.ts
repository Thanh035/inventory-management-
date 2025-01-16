import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserRouteAccessService } from './core/auth/user-route-access.service';


@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          // data: {
          //   Groups: [Group.ADMIN,Group.MANAGER,Group.STAFF],
          // },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          path: '',
          redirectTo: '/admin',
          pathMatch: 'full'
        },
        // ...errorRoute
      ]
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
