import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { PersonService } from './services/person.service';
import { ResidenceService } from './services/residence.service';
import { SharedModule } from '../shared/shared.module';
import { NewPersonComponent } from './components/new-person/new-person.component';
import { PersonRoutingModule } from './person-routing.module';
import { AddressService } from './services/address.service';
import { PersonProfileComponent } from './components/person-profile/person-profile.component';
import { SinglePersonComponent } from './components/single-person/single-person.component';
import { NoteModule } from '../note/note.module';

@NgModule({
  declarations: [
    NewPersonComponent,
    PersonProfileComponent,
    SinglePersonComponent
  ],
  imports: [
    CommonModule,
    PersonRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    NoteModule
  ],
  providers: [
    PersonService,
    AddressService,
    ResidenceService
  ]
})
export class PersonModule { }