import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { PersonService } from './services/person.service';
import { ResidenceService } from '../shared/services/residence.service';
import { SharedModule } from '../shared/shared.module';
import { NewPersonComponent } from './components/new-person/new-person.component';
import { PersonRoutingModule } from './person-routing.module';
import { AddressService } from './services/address.service';

@NgModule({
  declarations: [
    NewPersonComponent
  ],
  imports: [
    CommonModule,
    PersonRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    PersonService,
    AddressService,
    ResidenceService    
  ]
})
export class PersonModule { }
