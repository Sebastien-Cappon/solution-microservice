import { Component } from '@angular/core';
import { PatientsService } from '../../services/patients.service';
import { Observable } from 'rxjs';
import { Patient } from '../../../core/models/person.model';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent {

  constructor(
    private patientsService: PatientsService,
  ) { }

  private currentPractitionerId = Number(sessionStorage.getItem('currentPractitionerId'));
  patients$!: Observable<Patient[]>;

  ngOnInit() : void {
    this.initObservables();
  }

  private initObservables() {
    this.patientsService.getPatientsByPractitioner(this.currentPractitionerId);
    this.patients$ = this.patientsService.patients$;
  }
}
