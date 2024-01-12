import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Observable, tap } from 'rxjs';
import { Practitioner } from 'src/app/core/models/practitioner.model';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';
import { NewAccountComponent } from '../new-account/new-account.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  loginForm!: FormGroup;
  loginEmailCtrl!: FormControl;

  isLogged = this.authService.isLogged();
  isWrongCredentials = false;
  isLoading = false;
  isAccountCreated!: boolean;

  private currentPractitionerId = Number(sessionStorage.getItem('currentPractitionerId'));
  currentPractitioner$! : Observable<Practitioner>

  ngOnInit(): void {
    this.currentPractitioner$ = this.authService.getPractitionerById(this.currentPractitionerId);
    this.initLoginFormControls();
    this.initLoginForm();
  }

  private initLoginFormControls(): void {
    this.loginEmailCtrl = this.formBuilder.control('', [Validators.required, Validators.email, emailPatternValidator()]);
  }

  private initLoginForm(): void {
    this.loginForm = this.formBuilder.group({
      email: this.loginEmailCtrl,
      password: ['', Validators.required]
    });
  }

  getLoginFormControlErrorText(ctrl: AbstractControl): string {
    if(ctrl.hasError('required')) {
      return 'This input field is required.';
    } else {
      return 'This input filed require an valid email address.';
    }
  }

  openNewAccountDialog() {
    const newAccountDialog = this.dialog.open(NewAccountComponent, {
      width: '800px',
      maxWidth: 'calc(100vw - 32px)',
      maxHeight: 'calc(100vh - 32px)',
      data: {isAccountCreated: this.isAccountCreated}
    });

    newAccountDialog.afterClosed().subscribe(value => {
      if(value) {
        this.router.navigateByUrl('/home');
      }
    });
  }

  onLogin(): void {
    this.isLoading = true;
    this.isWrongCredentials = false;
    this.authService.login(this.loginForm.value).pipe(
      tap(logged => {
        this.isLoading = false;
        if (logged) {
          this.loginForm.reset();
          this.router.navigateByUrl('/home');
        } else {
          this.isWrongCredentials = true;
        }
      })
    ).subscribe();
  }

  onLogout(): void {
    if(this.authService.logout()) {
      this.isLogged=false;
    }
  }
}