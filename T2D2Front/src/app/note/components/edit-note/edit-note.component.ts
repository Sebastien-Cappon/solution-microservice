import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NoteService } from '../../services/note.service';
import { Observable, tap } from 'rxjs';
import { Note } from '../../models/note.model';

@Component({
  selector: 'app-edit-note',
  templateUrl: './edit-note.component.html',
  styleUrls: ['./edit-note.component.scss']
})
export class EditNoteComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: {
      currentNote: Note
    },

    public noteDialog: MatDialogRef<EditNoteComponent>,
    private formBuilder: FormBuilder,
    private noteService: NoteService
  ) { }

  public editNoteForm!: FormGroup;
  public editNoteContentCtrl!: FormControl;

  public note$!: Observable<Note>
  public isLoading = false;
  public isPristine = false;

  private ngOnInit() {
    this.initEditNoteFormControls();
    this.initEditNoteForm();
  }

  private initEditNoteFormControls() {
    this.editNoteContentCtrl = this.formBuilder.control('', Validators.required);

    if (this.editNoteContentCtrl.pristine) {
      this.isPristine = true;
    }
  }

  private initEditNoteForm() {
    this.editNoteForm = this.formBuilder.group({
      content: this.editNoteContentCtrl
    });
  }

  public onUpdateNote() {
    if (this.editNoteForm.value.content != this.data.currentNote.content) {
      this.isLoading = true;
      this.noteService.updateNoteById(this.data.currentNote.id, this.editNoteForm.value).pipe(
        tap(updated => {
          if (updated) {
            this.isLoading = false;
            this.editNoteForm.reset();
          }
        })
      ).subscribe(() => {
        this.noteService.getNotes(this.data.currentNote.personId);
        this.noteDialog.close();
      });
    } else {
      this.noteDialog.close();
    }
  }
}