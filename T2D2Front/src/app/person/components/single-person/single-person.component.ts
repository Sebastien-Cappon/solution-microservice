import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, map, switchMap, tap } from 'rxjs';
import { Person } from 'src/app/core/models/person.model';
import { Address } from 'src/app/core/models/address.model';
import { ResidenceService } from 'src/app/shared/services/residence.service';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';
import { confirmEqualsValidator } from 'src/app/shared/validators/confirmEquals.validator';
import { AddressService } from 'src/app/person/services/address.service';
import { PersonService } from 'src/app/person/services/person.service';

@Component({
  selector: 'app-single-person',
  templateUrl: './single-person.component.html',
  styleUrls: ['./single-person.component.scss']
})
export class SinglePersonComponent {

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private addressService: AddressService,
    private personService: PersonService,
    private residenceService: ResidenceService
  ) { }

    public personAddressEditForm!: FormGroup;
    public personEmailEditForm!: FormGroup;
    public personEditForm!: FormGroup;

    public personGenderCtrl!: FormControl;
    public personLastnameCtrl!: FormControl;
    public personFirstnameCtrl!: FormControl;
    public personBirthdateCtrl!: FormControl;
    public personPhoneCtrl!: FormControl;

    public personAddressNumberCtrl!: FormControl;
    public personAddressWayTypeCtrl!: FormControl;
    public personAddressWayNameCtrl!: FormControl;
    public personAddressPostcodeCtrl!: FormControl;
    public personAddressCityCtrl!: FormControl;
    public personAddressCountryCtrl!: FormControl;

    public personEmailCtrl!: FormControl;
    public personConfirmEmailCtrl!: FormControl;
    public showPersonConfirmEmail$!: Observable<boolean>;
    public showPersonEmailError$!: Observable<boolean>;

    public person$!: Observable<Person>;
    public personAddress$!: Observable<Address[]>;

    public isLoading = false;
    public isEditing = false;
    private addressDirty = false;
    private personDirty = false;

    public wayTypes!: string[];
    private personEmail!: string;

  private ngOnInit() {
    this.wayTypes = this.residenceService.wayTypes;
    this.personEmail = String(this.activatedRoute.snapshot.paramMap.get('personEmail'));
    
    this.initObservables();
    this.initPersonAddressEditFormControls();
    this.initPersonAddressEditForm();
    this.initPersonProfileEditFormControls();
    this.initPersonProfileEditForm();
    this.initPersonEditFormObservables();
  }

  private initObservables() {
    this.person$ = this.activatedRoute.params.pipe(
      switchMap(params => this.personService.getPersonById(+params['personId']))
    );
    this.personAddress$ = this.activatedRoute.params.pipe(
      switchMap(params => this.residenceService.getResidencesByPersonId(+params['personId']))
    )
  }

  private initPersonAddressEditFormControls() {
    this.personAddressNumberCtrl = this.formBuilder.control('', [Validators.required]);
    this.personAddressWayTypeCtrl = this.formBuilder.control('', [Validators.required]);
    this.personAddressWayNameCtrl = this.formBuilder.control('', [Validators.required]);
    this.personAddressPostcodeCtrl = this.formBuilder.control('', [Validators.required]);
    this.personAddressCityCtrl = this.formBuilder.control('', [Validators.required]);
    this.personAddressCountryCtrl = this.formBuilder.control('', [Validators.required]);
  }

  private initPersonAddressEditForm() {
    this.personAddressEditForm = this.formBuilder.group({
      number: this.personAddressNumberCtrl,
      wayType: this.personAddressWayTypeCtrl,
      wayName: this.personAddressWayNameCtrl,
      postcode: this.personAddressPostcodeCtrl,
      city: this.personAddressCityCtrl,
      country: this.personAddressCountryCtrl
    })
  }

  private initPersonProfileEditFormEmailControls() {
    this.personEmailCtrl = this.formBuilder.control(this.personEmail, [Validators.required, Validators.email, emailPatternValidator()]);
    this.personConfirmEmailCtrl = this.formBuilder.control(this.personEmail);
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
    if(ctrl.hasError('required')) {
      return 'Please confirm previous input.';
    } else if (ctrl.hasError('email') || ctrl.hasError('emailPatternValidator')) {
      return 'Valid e-mail address required.';
    } else {
      return 'An error has occured.';
    }
  }

  public onConfirmPersonEdition(addressId: number, personId: number) {
    this.isLoading = true;
    
    if(this.personAddressNumberCtrl.dirty
      || this.personAddressWayTypeCtrl.dirty
      || this.personAddressWayNameCtrl.dirty
      || this.personAddressPostcodeCtrl.dirty
      || this.personAddressCityCtrl.dirty
      || this.personAddressCountryCtrl.dirty) {
      this.addressDirty = true;
    }
    
    if(this.personGenderCtrl.dirty
      || this.personLastnameCtrl.dirty
      || this.personFirstnameCtrl.dirty
      || this.personBirthdateCtrl.dirty
      || this.personEmailCtrl.dirty
      || this.personPhoneCtrl.dirty) {
      this.personDirty = true;
    }

    if(this.addressDirty && !this.personDirty) {
      this.addressService.updateAddressById(addressId, this.personAddressEditForm.value).pipe(
        tap(addressUpdated => {
          this.isLoading = false;
          if(addressUpdated) {
            this.isEditing = false;
            this.addressDirty = false;
            this.ngOnInit();
          }
        })
      ).subscribe();
    } else if(!this.addressDirty && this.personDirty) {
      this.personService.updatePersonById(personId, this.personEditForm.value).pipe(
        tap(personUpdated => {
          this.isLoading = false;
          if(personUpdated) {
            this.isEditing = false;
            this.personDirty = false;
            this.ngOnInit();
          }
        })
      ).subscribe();
    } else if(this.addressDirty && this.personDirty) {
      this.addressService.updateAddressById(addressId, this.personAddressEditForm.value).pipe(
        tap(addressUpdated => {
          if(addressUpdated) {
            this.addressDirty = false;
            this.personService.updatePersonById(personId, this.personEditForm.value).pipe(
              tap(personUpdated => {
                this.isLoading = false;
                if(personUpdated) {
                  this.isEditing = false;
                  this.personDirty = false;
                  this.ngOnInit();
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

  public onBack() {
    this.router.navigateByUrl('/patients');
  }
}