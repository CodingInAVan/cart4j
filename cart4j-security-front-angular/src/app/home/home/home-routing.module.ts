import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home.component';
import {AuthGuard} from '../../auth/guard/auth.guard';
import {ResourcesComponent} from "../../resources/resources/resources.component";

const homeRoute: Routes = [{
  path: '',
  component: HomeComponent,
  canActivate: [AuthGuard],
  children: [
    {
      path: 'resources',
      component: ResourcesComponent,
      canActivate: [AuthGuard]
    }
  ]
}
];

@NgModule({
  imports: [RouterModule.forRoot(homeRoute)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
