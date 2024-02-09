import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Person } from '../../models/person.model';
import { PersonService } from '../../services/person.service';
import { NewNoteComponent } from 'src/app/note/components/new-note/new-note.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-single-person',
  templateUrl: './single-person.component.html',
  styleUrls: ['./single-person.component.scss']
})
export class SinglePersonComponent {

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private personService: PersonService,
    private dialog: MatDialog
  ) { }

  public person$!: Observable<Person>;
  public currentPerson!: Person;

  personId = Number(this.activatedRoute.snapshot.paramMap.get('personId'));

  private ngOnInit() {
    this.initObservables();
  }

  private initObservables() {
    this.personService.getPersonById(this.personId);
    this.person$ = this.personService.person$;
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

    newNoteDialog.afterClosed();
  }

  public onBack() {
    this.router.navigateByUrl('/patients');
  }
}