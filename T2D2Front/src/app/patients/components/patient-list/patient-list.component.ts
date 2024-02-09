import { Component } from '@angular/core';
import { PatientService } from '../../services/patient.service';
import { Observable, combineLatest, map, startWith, tap } from 'rxjs';
import { Person } from '../../../person/models/person.model';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';
import { MatDialog } from '@angular/material/dialog';
import { MatAutocomplete } from '@angular/material/autocomplete';
import { NewPersonComponent } from 'src/app/person/components/new-person/new-person.component';
import { NewNoteComponent } from 'src/app/note/components/new-note/new-note.component';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent {

  constructor(
    private patientService: PatientService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog
  ) { }

  private currentPractitionerId = Number(sessionStorage.getItem('currentPractitionerId'));
  public patients$!: Observable<Person[]>;
  public notPatients$!: Observable<Person[]>;

  public patientListForm!: FormGroup;
  public newPatientEmailCtrl!: FormControl;

  public isLoading = false;
  public newPatientEmail!: string;

  private ngOnInit(): void {
    this.initObservables();
    this.initNewPatientFormControls();
    this.initNewPatientForm();
    this.initNewPatientFormObservables();
  }

  private initNewPatientForm() {
    this.patientListForm = this.formBuilder.group({
      practionnerId: this.currentPractitionerId,
      newPatientEmail: this.newPatientEmailCtrl
    });
  }

  private initNewPatientFormControls() {
    this.newPatientEmailCtrl = this.formBuilder.control('', [Validators.required, Validators.email, emailPatternValidator()]);
  }

  private initNewPatientFormObservables() {
    const searchNotPatient$ = this.newPatientEmailCtrl.valueChanges.pipe(
      startWith(this.newPatientEmailCtrl.value),
      map(value => value.toLowerCase())
    );

    this.notPatients$ = combineLatest([
      searchNotPatient$,
      this.patientService.notPatients$
    ]).pipe(
      map(([searchNotPatient, notPatients]) => notPatients.filter(notPatient => notPatient.email
        .toLowerCase()
        .includes(searchNotPatient)))
    );
  }

  private initObservables() {
    this.patients$ = this.patientService.patients$;
    this.patientService.getPatientsByPractitionerId(this.currentPractitionerId);

    this.notPatients$ = this.patientService.notPatients$;
    this.patientService.getNotPatientsByPractitionerId(this.currentPractitionerId);
  }

  public getNewPatientEmailControlErrorText(ctrl: AbstractControl): string {
    if (ctrl.hasError('required')) {
      return 'The future patient email address is required.';
    } else if (ctrl.hasError('email') || ctrl.hasError('emailPatternValidator')) {
      return 'A valid email address is mandatory.';
    } else {
      return 'An error has occured.';
    }
  }

  public openNewNoteDialog(personId: number) {
    const newNoteDialog = this.dialog.open(NewNoteComponent, {
      width: '800px',
      maxWidth: 'calc(100vw - 32px)',
      maxHeight: 'calc(100vh - 32px)',
      data: {
        personId: personId
      }
    })
  }

  public openNewPersonDialog(auto: MatAutocomplete) {
    const newPersonDialog = this.dialog.open(NewPersonComponent, {
      width: '800px',
      maxWidth: 'calc(100vw - 32px)',
      maxHeight: 'calc(100vh - 32px)',
      data: {
        currentPractitionerId: this.currentPractitionerId,
        newPatientEmail: this.patientListForm.value.newPatientEmail
      }
    });

    auto.options.forEach((option) => {
      option.deselect();
    });

    newPersonDialog.afterClosed().subscribe(newPatient => {
      if (newPatient) {
        this.patientService.addPatient(newPatient).pipe(
          tap(linked => {
            if (linked) {
              this.patientListForm.reset();
            }
          })
        ).subscribe(() => {
          this.initObservables();
        });
      }
    })
  }

  public onDeletePatient(patientId: number) {
    this.isLoading = true;
    this.patientService.deletePatient(this.currentPractitionerId, patientId).subscribe(() => {
      this.isLoading = false;
      this.patientListForm.reset();

      // WORKAROUND : AUTOSUGGESTION LIST MUST CONTAIN PERSONS WHO HAVE BEEN >>CREATED<< (NOT UPDATED)
      // WHEN THEY'RE ADDED, WITHOUT REFRESH THE ALL PAGE.
      this.ngOnInit();
    });
  }
}