import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FactorListComponent } from './components/factor-list/factor-list.component';

const routes: Routes = [
  { path: '', component: FactorListComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class FactorRoutingModule { }