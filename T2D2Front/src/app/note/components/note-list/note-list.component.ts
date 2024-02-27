import { Component, Input } from '@angular/core';
import { NoteService } from '../../services/note.service';
import { Observable } from 'rxjs';
import { Note } from '../../models/note.model';
import { MatDialog } from '@angular/material/dialog';
import { EditNoteComponent } from '../edit-note/edit-note.component';
import { NewNoteComponent } from '../new-note/new-note.component';
import { RiskService } from 'src/app/person/services/risk.service';

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.scss']
})
export class NoteListComponent {

  @Input()
  public currentPersonId!: number;
  @Input()
  public currentPersonNotes!: Note[];

  constructor(
    private noteService: NoteService,
    private riskService: RiskService,
    private dialog: MatDialog
  ) { }

  panelOpenStateIndexes: Set<number> = new Set();
  trackById = (item: any) => item.id;
  panelOpened = (index: number) => this.panelOpenStateIndexes.add(index);
  panelClosed = (index: number) => this.panelOpenStateIndexes.delete(index);
  isPanelExpanded = (index: number) => this.panelOpenStateIndexes.has(index);

  public notes$!: Observable<Note[]>
  public isLoading = false;

  private ngOnInit() {

  }

  public openEditNoteDialog(currentNote: Note) {
    const editNoteDialog = this.dialog.open(EditNoteComponent, {
      width: '800px',
      maxWidth: 'calc(100vw - 32px)',
      maxHeight: 'calc(100vh - 32px)',
      data: {
        currentNote: currentNote
      }
    })

    editNoteDialog.afterClosed().subscribe(() => {
      this.riskService.setRiskCheckingStatus(true);
    })
  }

  public openNewNoteDialog() {
    const newNoteDialog = this.dialog.open(NewNoteComponent, {
      width: '800px',
      maxWidth: 'calc(100vw - 32px)',
      maxHeight: 'calc(100vh - 32px)',
      data: {
        personId: this.currentPersonId
      }
    })

    newNoteDialog.afterClosed().subscribe(() => {
      this.noteService.getNotes(this.currentPersonId);
      this.riskService.setRiskCheckingStatus(true);
    })
  }

  public onDeleteNote(noteId: string) {
    this.isLoading = true;
    this.noteService.deleteNoteById(noteId).subscribe(() => {
      this.isLoading = false;
      this.panelOpenStateIndexes = new Set();
      this.noteService.getNotes(this.currentPersonId);
      this.riskService.setRiskCheckingStatus(true);
    });
  }
}