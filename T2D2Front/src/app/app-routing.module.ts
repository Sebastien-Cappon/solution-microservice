import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: 'login', loadChildren: () => import('./auth/auth-routing.module').then(m => m.AuthRoutingModule) },
  { path: 'home', loadChildren: () => import('./home/home-routing.module').then(m => m.HomeRoutingModule), canActivate: [AuthGuard] },
  { path: 'patients', loadChildren: () => import('./patients/patients-routing.module').then(m => m.PatientsRoutingModule), canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
