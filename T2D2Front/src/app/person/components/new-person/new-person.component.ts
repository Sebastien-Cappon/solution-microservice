import { Component, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Observable, map, tap } from 'rxjs';
import { Address } from 'src/app/person/models/address.model';
import { Person } from 'src/app/shared/models/person.model';
import { PersonService } from 'src/app/person/services/person.service';
import { ResidenceService } from 'src/app/person/services/residence.service';
import { confirmEqualsValidator } from 'src/app/shared/validators/confirmEquals.validator';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';
import { AddressService } from '../../services/address.service';
import { ResidenceValue } from 'src/app/person/models/residence.model';
import { PatientValue } from 'src/app/patients/models/patient.model';

@Component({
  selector: 'app-new-person',
  templateUrl: './new-person.component.html',
  styleUrls: ['./new-person.component.scss']
})
export class NewPersonComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: {
      currentPractitionerId: any,
      newPatientEmail: string
    },

    public personDialog: MatDialogRef<NewPersonComponent>,
    private formBuilder: FormBuilder,
    private addressService: AddressService,
    private personService: PersonService,
    private residenceService: ResidenceService
  ) { }

  public newAddressForm!: FormGroup;
  public newPersonEmailForm!: FormGroup;
  public newPersonForm!: FormGroup;

  public newAddressNumberCtrl!: FormControl;
  public newAddressWayTypeCtrl!: FormControl;
  public newAddressWayNameCtrl!: FormControl;
  public newAddressPostcodeCtrl!: FormControl;
  public newAddressCityCtrl!: FormControl;
  public newAddressCountryCtrl!: FormControl;

  public newPersonEmailCtrl!: FormControl;
  public newPersonConfirmEmailCtrl!: FormControl;
  public showNewPersonConfirmEmail$!: Observable<boolean>;
  public showNewPersonEmailError$!: Observable<boolean>;

  public newPersonGenderCtrl!: FormControl;
  public newPersonLastnameCtrl!: FormControl;
  public newPersonFirstnameCtrl!: FormControl;
  public newPersonBirthdateCtrl!: FormControl;
  public newPersonPhoneCtrl!: FormControl;

  public isLoading = false;
  public isAlreadyPatient = false;

  public existingAddress$!: Observable<Address[]>;
  public existingPerson$!: Observable<Person>;

  public newPersonId!: number;
  public newAddressId!: number;
  public wayTypes!: string[];
  public birthdateMaxDate = new Date();

  private ngOnInit(): void {
    this.wayTypes = this.residenceService.wayTypes;

    this.initObservables();
    this.initNewAddressFormControls();
    this.initNewAddressForm();
    this.initNewPersonFormControls();
    this.initNewPersonForm();
    this.initNewPersonFormObservables();
  }

  private initObservables() {
    this.existingPerson$ = this.personService.getPersonByEmail(this.data.newPatientEmail);
    this.existingAddress$ = this.residenceService.getResidencesByPersonEmail(this.data.newPatientEmail);
  }

  private initNewAddressFormControls() {
    this.newAddressNumberCtrl = this.formBuilder.control('', [Validators.required]);
    this.newAddressWayTypeCtrl = this.formBuilder.control('', [Validators.required]);
    this.newAddressWayNameCtrl = this.formBuilder.control('', [Validators.required]);
    this.newAddressPostcodeCtrl = this.formBuilder.control('', [Validators.required]);
    this.newAddressCityCtrl = this.formBuilder.control('', [Validators.required]);
    this.newAddressCountryCtrl = this.formBuilder.control('', [Validators.required]);
  }

  private initNewPersonEmailFormControls() {
    this.newPersonEmailCtrl = this.formBuilder.control(this.data.newPatientEmail, [Validators.required, Validators.email, emailPatternValidator()]);
    this.newPersonConfirmEmailCtrl = this.formBuilder.control(this.data.newPatientEmail);
  }

  private initNewPersonFormControls() {
    this.newPersonGenderCtrl = this.formBuilder.control('', [Validators.required]);
    this.newPersonLastnameCtrl = this.formBuilder.control('', [Validators.required]);
    this.newPersonFirstnameCtrl = this.formBuilder.control('', [Validators.required]);
    this.newPersonBirthdateCtrl = this.formBuilder.control('', [Validators.required]);
    this.newPersonPhoneCtrl = this.formBuilder.control('', [Validators.required]);
    this.initNewPersonEmailFormControls();
  }

  private initNewAddressForm() {
    this.newAddressForm = this.formBuilder.group({
      number: this.newAddressNumberCtrl,
      wayType: this.newAddressWayTypeCtrl,
      wayName: this.newAddressWayNameCtrl,
      postcode: this.newAddressPostcodeCtrl,
      city: this.newAddressCityCtrl,
      country: this.newAddressCountryCtrl
    });
  }

  private initNewPersonEmailForm() {
    this.newPersonEmailForm = this.formBuilder.group({
      email: this.newPersonEmailCtrl,
      confirmEmail: this.newPersonConfirmEmailCtrl
    }, {
      validators: [confirmEqualsValidator('email', 'confirmEmail')]
    });
  }

  private initNewPersonForm() {
    this.initNewPersonEmailForm();
    this.newPersonForm = this.formBuilder.group({
      gender: this.newPersonGenderCtrl,
      lastname: this.newPersonLastnameCtrl,
      firstname: this.newPersonFirstnameCtrl,
      birthdate: this.newPersonBirthdateCtrl,
      phone: this.newPersonPhoneCtrl,
      email: this.newPersonEmailForm.get('email'),
    })
  }

  private initNewPersonFormObservables() {
    this.showNewPersonConfirmEmail$ = this.newPersonEmailCtrl.valueChanges.pipe(
      map(() => this.newPersonEmailCtrl.value),
      tap(showNewPersonConfirmEmail => this.setNewPersonEmailValidators(showNewPersonConfirmEmail))
    );
    this.showNewPersonEmailError$ = this.newPersonEmailForm.statusChanges.pipe(
      map(status => status === ('INVALID')
        && this.newPersonEmailCtrl.value
        && this.newPersonEmailCtrl.valid
        && this.newPersonConfirmEmailCtrl.value
        && this.newPersonConfirmEmailCtrl.valid
      )
    );
  }

  public setNewPersonEmailValidators(showNewPersonConfirmEmail: boolean) {
    if (showNewPersonConfirmEmail) {
      this.newPersonConfirmEmailCtrl.addValidators([
        Validators.required,
        Validators.email,
        emailPatternValidator()
      ]);
      this.newPersonConfirmEmailCtrl.markAsTouched();
    } else {
      this.newPersonConfirmEmailCtrl.reset('');
      this.newPersonConfirmEmailCtrl.clearValidators();
    }
    this.newPersonConfirmEmailCtrl.updateValueAndValidity();
  }

  public getNewPersonFormControlErrorText(ctrl: AbstractControl) {
    if (ctrl.hasError('required')) {
      return 'Please confirm previous input.';
    } else if (ctrl.hasError('email') || ctrl.hasError('emailPatternValidator')) {
      return 'Valid email address required.'
    } else {
      return 'An error has occured.';
    }
  }

  public onAddPatient(addressId: number, personId: number) {
    if (this.newAddressNumberCtrl.dirty
      || this.newAddressWayTypeCtrl.dirty
      || this.newAddressWayNameCtrl.dirty
      || this.newAddressPostcodeCtrl.dirty
      || this.newAddressCityCtrl.dirty
      || this.newAddressCountryCtrl.dirty) {
      this.addressService.updateAddressById(addressId, this.newAddressForm.value).subscribe();
    }

    if (this.newPersonGenderCtrl.dirty
      || this.newPersonLastnameCtrl.dirty
      || this.newPersonFirstnameCtrl.dirty
      || this.newPersonBirthdateCtrl.dirty
      || this.newPersonEmailCtrl.dirty
      || this.newPersonPhoneCtrl.dirty) {
      this.personService.updatePersonById(personId, this.newPersonForm.value).subscribe();
    }

    const newPatient: PatientValue = {
      practitionerId: this.data.currentPractitionerId,
      personEmail: this.newPersonEmailCtrl.value
    }
    this.personDialog.close(newPatient);
  }

  public onCreatePatient() {
    this.isLoading = true;
    this.addressService.createNewAddress(this.newAddressForm.value).pipe(
      tap(newAddressId => {
        if (newAddressId != 0) {
          this.newAddressId = newAddressId;
          this.personService.createNewPerson(this.newPersonForm.value).pipe(
            tap(newPersonId => {
              if (newPersonId != 0) {
                this.newPersonId = newPersonId;
              }
            })
          ).subscribe(() => {
            const newResidence: ResidenceValue = {
              personId: this.newPersonId,
              addressId: this.newAddressId
            }
            this.residenceService.addResidence(newResidence).pipe(
              tap(linked => {
                if (linked) {
                  this.isLoading = false;
                  this.newAddressForm.reset();
                  this.newPersonForm.reset();
                }
              })
            ).subscribe();
          });
        }
      })
    ).subscribe(() => {
      const newPatient: PatientValue = {
        practitionerId: this.data.currentPractitionerId,
        personEmail: this.newPersonEmailCtrl.value
      }
      this.personDialog.close(newPatient);
    });
  }
}