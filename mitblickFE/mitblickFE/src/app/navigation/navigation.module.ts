import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavbarComponent} from './navbar/navbar.component';
import {MatBadgeModule, MatButtonModule, MatIconModule, MatMenuModule, MatToolbarModule} from "@angular/material";
import {UserModule} from "../user/user.module";
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "../user/login/login.component";
import {ProfileComponent} from "../user/profile/profile.component";
import {UserManagementModule} from "../user-management/user-management.module";
import {ManageAllUsersComponent} from "../user-management/manage-all-users/manage-all-users.component";
import {SkillManagementViewComponent} from "../skill-management/skill-management-view/skill-management-view.component";
import {SkillManagementModule} from "../skill-management/skill-management.module";
import {NotificationsComponent} from "../user/notifications/notifications.component";
import {ProfileManagementComponent} from "../profile-management/profile-management/profile-management.component";
import {ProfileManagementModule} from "../profile-management/profile-management.module";
import {TranslationModule} from "../translation/translation.module";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";
import {HttpClient} from "@angular/common/http";
import {SupervisorViewComponent} from "../supervisor-management/supervisor-view/supervisor-view.component";
import {SupervisorManagementModule} from "../supervisor-management/supervisor-management.module";

const loginRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'manage-all-users', component: ManageAllUsersComponent},
  {path: 'manage-skills', component: SkillManagementViewComponent},
  {path: 'notifications', component: NotificationsComponent},
  {path: 'superior-view', component: ProfileManagementComponent},
  {path: 'supervisor-view', component: SupervisorViewComponent}

];


@NgModule({
  declarations: [NavbarComponent],
  exports: [
    NavbarComponent,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
    UserModule,
    UserManagementModule
  ],
  imports: [
    TranslateModule.forChild({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }

    }),
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
    MatBadgeModule,
    MatIconModule,
    RouterModule.forChild(loginRoutes),
    UserModule,
    UserManagementModule,
    SkillManagementModule,
    ProfileManagementModule,
    SupervisorManagementModule,
    TranslationModule,
  ]
})
export class NavigationModule {
}
