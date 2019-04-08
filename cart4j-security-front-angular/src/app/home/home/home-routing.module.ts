import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home.component';
import {AuthGuard} from '../../auth/guard/auth.guard';
import {LeftMenuModule} from '../left-menu/left-menu.module';
import {ResourceComponent} from '../resource/resource.component';
import {ClientComponent} from '../client/client.component';
import {ClientAddComponent} from '../client/client-add/client-add.component';
import {ClientListComponent} from '../client/client-list/client-list.component';

const homeRoute: Routes = [{
  path: '',
  component: HomeComponent,
  canActivate: [AuthGuard],
  children: [
    {
      path: 'resource',
      component: ResourceComponent,
      canActivate: [AuthGuard]
    },
    {
      path: 'client',
      component: ClientComponent,
      canActivate: [AuthGuard],
      children: [
        {
          path: '',
          component: ClientListComponent,
          canActivate: [AuthGuard]
        },
        {
          path: 'add',
          component: ClientAddComponent,
          canActivate: [AuthGuard]
        }
      ]
    }
  ]
}
];

@NgModule({
  imports: [RouterModule.forRoot(homeRoute)],
  exports: [RouterModule, LeftMenuModule]
})
export class HomeRoutingModule { }
