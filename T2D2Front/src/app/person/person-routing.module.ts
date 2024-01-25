import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewPersonComponent } from './components/new-person/new-person.component';

const routes: Routes = [
  { path: '', component: NewPersonComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class PersonRoutingModule { }
