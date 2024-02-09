import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import { Observable, map, tap } from 'rxjs';
import { confirmEqualsValidator } from 'src/app/shared/validators/confirmEquals.validator';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';
import { Address } from '../../models/address.model';
import { Person } from '../../models/person.model';
import { AddressService } from '../../services/address.service';
import { PersonService } from '../../services/person.service';
import { ResidenceService } from '../../services/residence.service';

@Component({
  selector: 'app-person-profile',
  templateUrl: './person-profile.component.html',
  styleUrls: ['./person-profile.component.scss']
})
export class PersonProfileComponent {

  @Input()
  public currentPersonId!: number;
  @Input()
  public currentPerson!: Person;

  constructor(
    private formBuilder: FormBuilder,
    private addressService: AddressService,
    private personService: PersonService,
    private residenceService: ResidenceService
  ) { }

  public personResidenceEditForm!: FormGroup;
  public personEmailEditForm!: FormGroup;
  public personEditForm!: FormGroup;

  public personGenderCtrl!: FormControl;
  public personLastnameCtrl!: FormControl;
  public personFirstnameCtrl!: FormControl;
  public personBirthdateCtrl!: FormControl;
  public personPhoneCtrl!: FormControl;

  public personResidenceNumberCtrl!: FormControl;
  public personResidenceWayTypeCtrl!: FormControl;
  public personResidenceWayNameCtrl!: FormControl;
  public personResidencePostcodeCtrl!: FormControl;
  public personResidenceCityCtrl!: FormControl;
  public personResidenceCountryCtrl!: FormControl;

  public personEmailCtrl!: FormControl;
  public personConfirmEmailCtrl!: FormControl;
  public showPersonConfirmEmail$!: Observable<boolean>;
  public showPersonEmailError$!: Observable<boolean>;

  public person$!: Observable<Person>;
  public residences$!: Observable<Address[]>;

  public isLoading = false;
  public isEditing = false;
  private addressDirty = false;
  private personDirty = false;

  public wayTypes!: string[];
  public birthdateMaxDate = new Date();

  private ngOnInit() {
    this.wayTypes = this.residenceService.wayTypes;

    this.initObservables();
    this.initPersonResidenceEditFormControls();
    this.initPersonResidenceEditForm();
    this.initPersonProfileEditFormControls();
    this.initPersonProfileEditForm();
    this.initPersonEditFormObservables();
  }

  private initObservables() {
    this.residences$ = this.residenceService.residences$;
    this.residenceService.getResidencesByPersonId(this.currentPersonId);
  }

  private reloadObservables() {
    this.residenceService.getResidencesByPersonId(this.currentPersonId);
    this.personService.getPersonById(this.currentPersonId);
  }

  private initPersonResidenceEditFormControls() {
    this.personResidenceNumberCtrl = this.formBuilder.control('', [Validators.required]);
    this.personResidenceWayTypeCtrl = this.formBuilder.control('', [Validators.required]);
    this.personResidenceWayNameCtrl = this.formBuilder.control('', [Validators.required]);
    this.personResidencePostcodeCtrl = this.formBuilder.control('', [Validators.required]);
    this.personResidenceCityCtrl = this.formBuilder.control('', [Validators.required]);
    this.personResidenceCountryCtrl = this.formBuilder.control('', [Validators.required]);
  }

  private initPersonResidenceEditForm() {
    this.personResidenceEditForm = this.formBuilder.group({
      number: this.personResidenceNumberCtrl,
      wayType: this.personResidenceWayTypeCtrl,
      wayName: this.personResidenceWayNameCtrl,
      postcode: this.personResidencePostcodeCtrl,
      city: this.personResidenceCityCtrl,
      country: this.personResidenceCountryCtrl
    })
  }

  private initPersonProfileEditFormEmailControls() {
    this.personEmailCtrl = this.formBuilder.control('placeholder@valide.mail', [Validators.required, Validators.email, emailPatternValidator()]);
    this.personConfirmEmailCtrl = this.formBuilder.control('placeholder@valide.mail');
  }

  private initPersonProfileEditFormControls() {
    this.personGenderCtrl = this.formBuilder.control('', [Validators.required]);
    this.personLastnameCtrl = this.formBuilder.control('', [Validators.required]);
    this.personFirstnameCtrl = this.formBuilder.control('', [Validators.required]);
    this.personBirthdateCtrl = this.formBuilder.control('', [Validators.required]);
    this.personPhoneCtrl = this.formBuilder.control('', [Validators.required]);
    this.initPersonProfileEditFormEmailControls();
  }

  private initPersonEmailEditForm() {
    this.personEmailEditForm = this.formBuilder.group({
      email: this.personEmailCtrl,
      confirmEmail: this.personConfirmEmailCtrl
    }, {
      validators: [confirmEqualsValidator('email', 'confirmEmail')]
    });
  }

  private initPersonProfileEditForm() {
    this.initPersonEmailEditForm();
    this.personEditForm = this.formBuilder.group({
      gender: this.personGenderCtrl,
      lastname: this.personLastnameCtrl,
      firstname: this.personFirstnameCtrl,
      birthdate: this.personBirthdateCtrl,
      phone: this.personPhoneCtrl,
      email: this.personEmailEditForm.get('email')
    });
  }

  private initPersonEditFormObservables() {
    this.showPersonConfirmEmail$ = this.personEmailCtrl.valueChanges.pipe(
      map(() => this.personEmailCtrl.value),
      tap(showPersonConfirmEmail => this.setNewPersonEmailValidators(showPersonConfirmEmail))
    );
    this.showPersonEmailError$ = this.personEmailEditForm.statusChanges.pipe(
      map(status => status === ('INVALID')
        && this.personEmailCtrl.value
        && this.personEmailCtrl.valid
        && this.personConfirmEmailCtrl.value
        && this.personConfirmEmailCtrl.valid
      )
    );
  }

  private setNewPersonEmailValidators(showPersonConfirmEmail: boolean) {
    if (showPersonConfirmEmail) {
      this.personConfirmEmailCtrl.addValidators([
        Validators.required,
        Validators.email,
        emailPatternValidator()
      ]);
      this.personConfirmEmailCtrl.markAsTouched();
    } else {
      this.personConfirmEmailCtrl.reset('');
      this.personConfirmEmailCtrl.clearValidators();
    }
    this.personConfirmEmailCtrl.updateValueAndValidity();
  }

  public getPersonEditFormControlErrorText(ctrl: AbstractControl): string {
    if (ctrl.hasError('required')) {
      return 'Please confirm previous input.';
    } else if (ctrl.hasError('email') || ctrl.hasError('emailPatternValidator')) {
      return 'Valid e-mail address required.';
    } else {
      return 'An error has occured.';
    }
  }

  public onConfirmPersonEdition(addressId: number, personId: number) {
    this.isLoading = true;

    if (this.personResidenceNumberCtrl.dirty
      || this.personResidenceWayTypeCtrl.dirty
      || this.personResidenceWayNameCtrl.dirty
      || this.personResidencePostcodeCtrl.dirty
      || this.personResidenceCityCtrl.dirty
      || this.personResidenceCountryCtrl.dirty) {
      this.addressDirty = true;
    }

    if (this.personGenderCtrl.dirty
      || this.personLastnameCtrl.dirty
      || this.personFirstnameCtrl.dirty
      || this.personBirthdateCtrl.dirty
      || this.personEmailCtrl.dirty
      || this.personPhoneCtrl.dirty) {
      this.personDirty = true;
    }

    if (this.addressDirty && !this.personDirty) {
      this.addressService.updateAddressById(addressId, this.personResidenceEditForm.value).pipe(
        tap(addressUpdated => {
          this.isLoading = false;
          if (addressUpdated) {
            this.isEditing = false;
            this.addressDirty = false;
            this.reloadObservables();
          }
        })
      ).subscribe();
    } else if (!this.addressDirty && this.personDirty) {
      this.personService.updatePersonById(personId, this.personEditForm.value).pipe(
        tap(personUpdated => {
          this.isLoading = false;
          if (personUpdated) {
            this.isEditing = false;
            this.personDirty = false;
            this.reloadObservables();
          }
        })
      ).subscribe();
    } else if (this.addressDirty && this.personDirty) {
      this.addressService.updateAddressById(addressId, this.personResidenceEditForm.value).pipe(
        tap(addressUpdated => {
          if (addressUpdated) {
            this.addressDirty = false;
            this.personService.updatePersonById(personId, this.personEditForm.value).pipe(
              tap(personUpdated => {
                this.isLoading = false;
                if (personUpdated) {
                  this.isEditing = false;
                  this.personDirty = false;
                  this.reloadObservables();
                }
              })
            ).subscribe();
          }
        })
      ).subscribe();
    } else {
      this.isEditing = false;
      this.isLoading = false;
    }
  }

  public onEditPerson() {
    this.isEditing = true;
  }
}