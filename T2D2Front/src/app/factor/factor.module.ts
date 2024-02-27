import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FactorRoutingModule } from './factor-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FactorService } from './services/factor.service';
import { FactorListComponent } from './components/factor-list/factor-list.component';


@NgModule({
  declarations: [
    FactorListComponent
  ],
  imports: [
    CommonModule,
    FactorRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    FactorService
  ]
})
export class FactorModule { }