import { Component } from '@angular/core';
import { FactorService } from '../../services/factor.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, tap } from 'rxjs';
import { Factor } from '../../models/factor.model';

@Component({
  selector: 'app-factor-list',
  templateUrl: './factor-list.component.html',
  styleUrls: ['./factor-list.component.scss']
})
export class FactorListComponent {

  constructor(
    private factorService: FactorService,
    private formBuilder: FormBuilder
  ) { }

  public factors$!: Observable<Factor[]>;

  public newFactorForm!: FormGroup;
  public newFactorTermCtrl!: FormControl;

  public isLoading = false;

  private ngOnInit() {
    this.initObservables();
    this.initNewFactorFormControls();
    this.initNewFactorForm();
  }

  private initObservables() {
    this.factors$ = this.factorService.factors$;
    this.factorService.getFactors();
  }

  private initNewFactorFormControls() {
    this.newFactorTermCtrl = this.formBuilder.control('', Validators.required);
  }

  private initNewFactorForm() {
    this.newFactorForm = this.formBuilder.group({
      term: this.newFactorTermCtrl
    })
  }

  public onAddNewFactor() {
    this.isLoading = true;
    this.factorService.addNewFactor(this.newFactorForm.value).pipe(
      tap(added => {
        this.isLoading = false;
        if (added) {
          this.newFactorForm.reset();
          this.factorService.getFactors();
        }
      })
    ).subscribe();
  }

  public onDeleteFactor(factorId: number) {
    this.isLoading = true;
    this.factorService.deleteFactorById(factorId).subscribe(() => {
      this.isLoading = false;
      this.newFactorForm.reset();
      this.factorService.getFactors();
    });
  }
}