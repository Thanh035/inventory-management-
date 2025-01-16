import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { NavbarOfAdminComponent } from './layouts/admin/navbar/navbar.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HeaderOfAdminComponent } from './layouts/admin/header/header.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { httpInterceptorProviders } from './core/interceptor';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './core/config/font-awesome-icons';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule(
  {
  declarations: [
    HeaderOfAdminComponent,
    NavbarOfAdminComponent,
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'app', separator: '-', caseSensitive: true }),
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent],
  }
)
export class AppModule {
  constructor(iconLibrary:FaIconLibrary) {
    iconLibrary.addIcons(...fontAwesomeIcons);
  }

}
