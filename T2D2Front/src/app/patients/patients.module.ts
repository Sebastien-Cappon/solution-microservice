import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PatientsRoutingModule } from './patients-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { PatientListComponent } from './components/patient-list/patient-list.component';
import { PatientService } from './services/patient.service';
import { SinglePersonComponent } from '../person/components/single-person/single-person.component';


@NgModule({
  declarations: [
    PatientListComponent,
    SinglePersonComponent
  ],
  imports: [
    CommonModule,
    PatientsRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    PatientService
  ]
})
export class PatientsModule { }