import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Observable, map, tap } from 'rxjs';
import { confirmEqualsValidator } from 'src/app/shared/validators/confirmEquals.validator';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';
import { passwordPatternValidator } from 'src/app/shared/validators/passwordPattern.validator';
import { NewAccountService } from '../../services/new-account.service';

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.component.html',
  styleUrls: ['./new-account.component.scss']
})
export class NewAccountComponent {

  constructor(
    private newAccountDialog: MatDialogRef<NewAccountComponent>,
    private formBuilder: FormBuilder,
    private newAccountService: NewAccountService
  ) { }

  public newAccountForm!: FormGroup;
  public newAccountFirstnameCtrl!: FormControl;
  public newAccountLastnameCtrl!: FormControl;

  public newAccountEmailForm!: FormGroup;
  public newAccountEmailCtrl!: FormControl;
  public newAccountConfirmEmailCtrl!: FormControl;
  public showNewAccountConfirmEmail$!: Observable<boolean>;
  public showNewAccountEmailError$!: Observable<boolean>;

  public newAccountPasswordForm!: FormGroup;
  public newAccountPasswordCtrl!: FormControl;
  public newAccountConfirmPasswordCtrl!: FormControl;
  public showNewAccountConfirmPassword$!: Observable<boolean>;
  public showNewAccountPasswordError$!: Observable<boolean>;

  public isLoading = false;
  public isAlreadyCreated = false;

  private ngOnInit() {
    this.initNewAccountFormControls();
    this.initNewAccountForm();
    this.initNewAccountFormObservables();
  }

  private initNewAccountFormControls() {
    this.newAccountFirstnameCtrl = this.formBuilder.control('', [Validators.required]);
    this.newAccountLastnameCtrl = this.formBuilder.control('', [Validators.required]);
    this.initNewAccountEmailFormControl();
    this.initNewAccountPasswordFormControl();
  }

  private initNewAccountEmailFormControl() {
    this.newAccountEmailCtrl = this.formBuilder.control('', [Validators.required, Validators.email, emailPatternValidator()]);
    this.newAccountConfirmEmailCtrl = this.formBuilder.control('');
    this.newAccountEmailForm = this.formBuilder.group({
      email: this.newAccountEmailCtrl,
      confirmEmail: this.newAccountConfirmEmailCtrl
    }, {
      validators: [confirmEqualsValidator('email', 'confirmEmail')]
    });
  }

  private initNewAccountPasswordFormControl() {
    this.newAccountPasswordCtrl = this.formBuilder.control('', [Validators.required, passwordPatternValidator()]);
    this.newAccountConfirmPasswordCtrl = this.formBuilder.control('');
    this.newAccountPasswordForm = this.formBuilder.group({
      password: this.newAccountPasswordCtrl,
      confirmPassword: this.newAccountConfirmPasswordCtrl
    }, {
      validators: [confirmEqualsValidator('password', 'confirmPassword')]
    });
  }

  private initNewAccountForm() {
    this.newAccountForm = this.formBuilder.group({
      firstname: this.newAccountFirstnameCtrl,
      lastname: this.newAccountLastnameCtrl,
      email: this.newAccountEmailForm.get('email'),
      social: false,
      password: this.newAccountPasswordForm.get('password'),
      amount: 0,
      active: true
    })
  }

  private initNewAccountFormObservables() {
    this.initNewAccountEmailFormObservables();
    this.initNewAccountPasswordFormObservables();
  }

  private initNewAccountEmailFormObservables() {
    this.showNewAccountConfirmEmail$ = this.newAccountEmailCtrl.valueChanges.pipe(
      map(() => this.newAccountEmailCtrl.value),
      tap(showNewAccountConfirmEmail => this.setNewAcccountEmailValidators(showNewAccountConfirmEmail))
    );
    this.showNewAccountEmailError$ = this.newAccountEmailForm.statusChanges.pipe(
      map(status => status === ('INVALID')
        && this.newAccountEmailCtrl.value
        && this.newAccountEmailCtrl.valid
        && this.newAccountConfirmEmailCtrl.value
        && this.newAccountConfirmEmailCtrl.valid
      )
    );
  }

  private initNewAccountPasswordFormObservables() {
    this.showNewAccountConfirmPassword$ = this.newAccountPasswordCtrl.valueChanges.pipe(
      map(() => this.newAccountPasswordCtrl.value),
      tap(showNewAccountConfirmPassword => this.setProfilePasswordValidators(showNewAccountConfirmPassword))
    );
    this.showNewAccountPasswordError$ = this.newAccountPasswordForm.statusChanges.pipe(
      map(status => status === ('INVALID')
        && this.newAccountPasswordCtrl.value
        && this.newAccountPasswordCtrl.valid
        && this.newAccountConfirmPasswordCtrl.value
        && this.newAccountConfirmPasswordCtrl.valid
      )
    );
  }

  private setNewAcccountEmailValidators(showNewAccountConfirmEmail: boolean) {
    if (showNewAccountConfirmEmail) {
      this.newAccountConfirmEmailCtrl.addValidators([
        Validators.required,
        Validators.email,
        emailPatternValidator()
      ]);
      this.newAccountConfirmEmailCtrl.markAsTouched();
    } else {
      this.newAccountConfirmEmailCtrl.reset('');
      this.newAccountConfirmEmailCtrl.clearValidators();
    }
    this.newAccountConfirmEmailCtrl.updateValueAndValidity();
  }

  private setProfilePasswordValidators(showNewAccountConfirmPassword: boolean) {
    if (showNewAccountConfirmPassword) {
      this.newAccountConfirmPasswordCtrl.addValidators([
        Validators.required,
        passwordPatternValidator()
      ]);
      this.newAccountConfirmPasswordCtrl.markAsTouched();
    } else {
      this.newAccountConfirmPasswordCtrl.reset('');
      this.newAccountConfirmPasswordCtrl.clearValidators();
    }
    this.newAccountConfirmPasswordCtrl.updateValueAndValidity();
  }

  public getNewAccountFormControlErrorText(ctrl: AbstractControl) {
    if(ctrl.hasError('required')) {
      return 'Please confirm previous input.';
    } else if (ctrl.hasError('email') || ctrl.hasError('emailPatternValidator')){
      return 'Valid email address required.'
    } else if (ctrl.hasError('passwordPatternValidator')) {
      return '8 chars : a-Z 0-9 !@#$%^&*'
    } else {
      return 'An error has occured.';
    }
  }

  public onCreateAccount() {
    this.isLoading = true;
    this.newAccountService.createNewAccount(this.newAccountForm.value).pipe(
      tap(created => {
        this.isLoading = false;
        this.isAlreadyCreated = false;
        if (created) {
          this.newAccountForm.reset();
          this.newAccountDialog.close(true)
        } else {
          this.isAlreadyCreated = true;
        }
      })
    ).subscribe();
  }
}