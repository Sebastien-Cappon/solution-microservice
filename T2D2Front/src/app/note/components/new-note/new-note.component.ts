import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NoteService } from '../../services/note.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-new-note',
  templateUrl: './new-note.component.html',
  styleUrls: ['./new-note.component.scss']
})
export class NewNoteComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: {
      personId: number
    },

    public noteDialog: MatDialogRef<NewNoteComponent>,
    private formBuilder: FormBuilder,
    private noteService: NoteService
  ) { }

  public newNoteForm!: FormGroup;
  public newNoteContentCtrl!: FormControl;

  public isLoading = false;
  public currentDate = new Date();

  private ngOnInit() {
    this.initNewNoteFormControls();
    this.initNewNoteForm();
  }

  private initNewNoteFormControls() {
    this.newNoteContentCtrl = this.formBuilder.control('', Validators.required);
  }

  private initNewNoteForm() {
    this.newNoteForm = this.formBuilder.group({
      personId: this.data.personId,
      date: this.currentDate,
      content: this.newNoteContentCtrl
    });
  }

  public onAddNote() {
    this.isLoading = true;
    this.noteService.createNewNote(this.newNoteForm.value).pipe(
      tap(written => {
        if (written) {
          this.newNoteForm.reset();
        }
      })
    ).subscribe(() => {
      this.noteDialog.close();
    });
  }
}