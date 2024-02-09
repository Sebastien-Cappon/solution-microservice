import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientListComponent } from './components/patient-list/patient-list.component';
import { SinglePersonComponent } from '../person/components/single-person/single-person.component';

const routes: Routes = [
  { path: '', component: PatientListComponent },
  { path: ':personId', component: SinglePersonComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class PatientsRoutingModule { }