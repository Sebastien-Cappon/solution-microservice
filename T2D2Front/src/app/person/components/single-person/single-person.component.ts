import { Component, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { Person } from '../../../shared/models/person.model';
import { PersonService } from '../../services/person.service';
import { NewNoteComponent } from 'src/app/note/components/new-note/new-note.component';
import { MatDialog } from '@angular/material/dialog';
import { RiskService } from '../../services/risk.service';
import { RiskFactorsValue } from '../../models/riskFactors.model';
import { Address } from '../../models/address.model';
import { ResidenceService } from '../../services/residence.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Note } from 'src/app/note/models/note.model';
import { NoteService } from 'src/app/note/services/note.service';
import { RiskDisplay } from '../../models/riskDisplay.model';

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
    private residenceService: ResidenceService,
    private personService: PersonService,
    private noteService: NoteService,
    private riskService: RiskService
  ) { }

  public riskFactorsForm!: FormGroup;

  public residences$!: Observable<Address[]>;
  public person$!: Observable<Person>;
  public notes$!: Observable<Note[]>;
  public risk$!: Observable<RiskDisplay>;
  public riskIsChecked$!: Observable<boolean>;

  public currentPerson!: Person;
  public currentPersonNotes!: Note[];

  public personId = Number(this.activatedRoute.snapshot.paramMap.get('personId'));

  private ngOnInit() {
    this.initObservables();
  }

  private initObservables() {
    this.riskIsChecked$ = this.riskService.riskIsChecked$;
    this.riskService.setRiskCheckingStatus(true);

    this.noteService.getNotes(this.personId);
    this.notes$ = this.noteService.notes$;

    this.residenceService.getResidencesByPersonId(this.personId);
    this.residences$ = this.residenceService.residences$;

    this.personService.getPersonById(this.personId);
    this.person$ = this.personService.person$;
  }

  public calculateRisks(personGender: boolean, personBirthdate: string, personNotes: Note[]) {
    this.riskFactorsForm = this.formBuilder.group({
      gender: personGender,
      birthdate: personBirthdate,
      notes: [personNotes.map(note => note.content)]
    });

    this.riskService.getRiskScore(this.riskFactorsForm.value);
    this.risk$ = this.riskService.risk$;

    this.riskService.setRiskCheckingStatus(false);
  }

  public onBack() {
    this.router.navigateByUrl('/patients');
  }
}